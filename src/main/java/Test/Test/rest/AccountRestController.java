package Test.Test.rest;

import Test.Test.component.JwtManager;
import Test.Test.dao.DetailUsersRepository;
import Test.Test.dao.JobsRepository;
import Test.Test.dao.UsersRepository;
import Test.Test.dto.Account.RegisterDTO;
import Test.Test.dto.CreatedUser.CreatedDataDTO;
import Test.Test.dto.CreatedUser.InfoCreatedUserDTO;
import Test.Test.dto.DeleteUser.DeleteDataDTO;
import Test.Test.dto.DeleteUser.InfoDeleteUserDTO;
import Test.Test.dto.Token.RequestTokenDTO;
import Test.Test.dto.Token.ResponseFailLoginDTO;
import Test.Test.dto.Token.ResponseTokenDTO;
import Test.Test.dto.UpdateUser.InfoUpdateUserDTO;
import Test.Test.dto.UpdateUser.UpdateDataDTO;
import Test.Test.dto.Users.UpsertJobDTO;
import Test.Test.dto.Users.UpsertUserDetailDTO;
import Test.Test.service.AccountService;
import Test.Test.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("api/account")
public class AccountRestController {
    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DetailUsersRepository detailUsersRepository;

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String username = "";

    @PostMapping("/register")
    public ResponseEntity<Object> post(@Valid @RequestBody RegisterDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var user = accountService.register(dto);
            CreatedDataDTO createdDto = new CreatedDataDTO();
            createdDto.setPk(user.getId());
            createdDto.setCreated_by(user.getCreatedBy());
            createdDto.setEntity(user.getClass().getSimpleName());
            createdDto.setCreated_at(user.getCreatedAt());
            InfoCreatedUserDTO response = new InfoCreatedUserDTO(true, "Succesfully Created Data", createdDto);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> post(@RequestBody RequestTokenDTO dto){
        var isAuthenticated = accountService.checkUsernamePassword(dto.getUsername(), dto.getPassword());
        if(isAuthenticated){
            var token = jwtManager.generateToken(
                    dto.getUsername(),
                    dto.getSubject(),
                    dto.getAudience(),
                    dto.getSecretKey()
            );
            var responseDto = new ResponseTokenDTO(true, "Succesfully Login", token, token);
            username = dto.getUsername();
            return ResponseEntity.status(200).body(responseDto);
        } else {
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> put(@Valid @RequestBody RegisterDTO dto, BindingResult bindingResult){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        if(!bindingResult.hasErrors()){
            var user = usersRepository.findById(dto.getId()).get();
            if(user.getDeleteAt() != null){
                return ResponseEntity.status(200).body("Data Sudah Terhapus");
            }
            user.setUsername(dto.getUsername());
            var hashPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(hashPassword);
            user.setUpdateBy(usersRepository.getIdUser(username));
            user.setUpdateAt(LocalDateTime.now());
            usersRepository.save(user);
            UpdateDataDTO updateDataDTO = new UpdateDataDTO();
            updateDataDTO.setPk(user.getId());
            updateDataDTO.setUpdated_by(usersRepository.getIdUser(username));
            updateDataDTO.setEntity(user.getClass().getSimpleName());
            updateDataDTO.setUpdated_at(LocalDateTime.now());
            InfoUpdateUserDTO response = new InfoUpdateUserDTO(true, "Succesfully Updated Data", updateDataDTO);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        var user = usersRepository.findById(id).get();
        user.setDeleteBy(usersRepository.getIdUser(username));
        user.setDeleteAt(LocalDateTime.now());
        DeleteDataDTO deleteDataDTO = new DeleteDataDTO();
        deleteDataDTO.setPk(user.getId());
        Long idUser = usersRepository.getIdUser(username);
        deleteDataDTO.setDeleted_by(idUser);
        deleteDataDTO.setEntity(user.getClass().getSimpleName());
        deleteDataDTO.setDeleted_at(LocalDateTime.now());
        InfoDeleteUserDTO response = new InfoDeleteUserDTO(true, "Succesfully Delete Data", deleteDataDTO);
        usersRepository.save(user);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping()
    public ResponseEntity<Object> get(){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        var id = usersRepository.getIdUser(username);
        var response = accountService.getAccountDetail(id);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/insertDetailUser")
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertUserDetailDTO dto, BindingResult bindingResult){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        if(!bindingResult.hasErrors()){
            var userDetail = usersService.saveDetailUser(dto);
            if(dto.getUserId() == usersRepository.getIdUser(username)){
                return ResponseEntity.status(200).body("User Id sudah tersedia dalam database");
            }
            userDetail.setUserId(dto.getUserId());
            userDetail.setCreatedBy(usersRepository.getIdUser(username));
            userDetail.setCreatedAt(LocalDateTime.now());
            detailUsersRepository.save(userDetail);
            CreatedDataDTO createdDto = new CreatedDataDTO();
            createdDto.setPk(userDetail.getId());
            createdDto.setCreated_by(userDetail.getCreatedBy());
            createdDto.setEntity(userDetail.getClass().getSimpleName());
            createdDto.setCreated_at(userDetail.getCreatedAt());
            InfoCreatedUserDTO response = new InfoCreatedUserDTO(true, "Succesfully Created Data", createdDto);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body(bindingResult.getAllErrors());
    }

    @PutMapping("/updateDetailUser")
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertUserDetailDTO dto, BindingResult bindingResult){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        if(!bindingResult.hasErrors()){
            var userDetail = detailUsersRepository.findById(dto.getId()).get();
            userDetail.setId(dto.getId());
            userDetail.setUserId(dto.getUserId());
            userDetail.setFirstName(dto.getFirstName());
            userDetail.setLastName(dto.getLastName());
            userDetail.setUpdateBy(usersRepository.getIdUser(username));
            userDetail.setUpdateAt(LocalDateTime.now());
            detailUsersRepository.save(userDetail);
            UpdateDataDTO updateDataDTO = new UpdateDataDTO();
            updateDataDTO.setPk(userDetail.getId());
            updateDataDTO.setUpdated_by(usersRepository.getIdUser(username));
            updateDataDTO.setEntity(userDetail.getClass().getSimpleName());
            updateDataDTO.setUpdated_at(LocalDateTime.now());
            InfoUpdateUserDTO response = new InfoUpdateUserDTO(true, "Succesfully Updated Data", updateDataDTO);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body(bindingResult.getAllErrors());
    }

    @DeleteMapping("/deleteDetailUser/{id}")
    public ResponseEntity<Object> deleteDetailUser(@PathVariable("id") Long id){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        var userDetail = detailUsersRepository.findById(id).get();
        userDetail.setDeleteBy(usersRepository.getIdUser(username));
        userDetail.setDeleteAt(LocalDateTime.now());
        DeleteDataDTO deleteDataDTO = new DeleteDataDTO();
        deleteDataDTO.setPk(userDetail.getId());
        deleteDataDTO.setDeleted_by(usersRepository.getIdUser(username));
        deleteDataDTO.setEntity(userDetail.getClass().getSimpleName());
        deleteDataDTO.setDeleted_at(LocalDateTime.now());
        InfoDeleteUserDTO response = new InfoDeleteUserDTO(true, "Succesfully Delete Data", deleteDataDTO);
        detailUsersRepository.save(userDetail);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/insertJobUser")
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertJobDTO dto, BindingResult bindingResult){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        if(!bindingResult.hasErrors()){
            var jobUser = usersService.saveJobUser(dto);
            jobUser.setUserId(dto.getUserId());
            jobUser.setCreatedBy(usersRepository.getIdUser(username));
            jobUser.setCreatedAt(LocalDateTime.now());
            jobsRepository.save(jobUser);
            CreatedDataDTO createdDto = new CreatedDataDTO();
            createdDto.setPk(jobUser.getId());
            createdDto.setCreated_by(jobUser.getCreatedBy());
            createdDto.setEntity(jobUser.getClass().getSimpleName());
            createdDto.setCreated_at(jobUser.getCreatedAt());
            InfoCreatedUserDTO response = new InfoCreatedUserDTO(true, "Succesfully Created Data", createdDto);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body(bindingResult.getAllErrors());
    }

    @PutMapping("/updateJobUser")
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertJobDTO dto, BindingResult bindingResult){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        if(!bindingResult.hasErrors()){
            var jobUser = jobsRepository.findById(dto.getId()).get();
            jobUser.setId(dto.getId());
            jobUser.setUserId(dto.getUserId());
            jobUser.setName(dto.getName());
            jobUser.setStartAt(dto.getStartAt());
            jobUser.setEndAt(dto.getEndAt());
            jobUser.setUpdateBy(usersRepository.getIdUser(username));
            jobUser.setUpdateAt(LocalDateTime.now());
            jobsRepository.save(jobUser);
            UpdateDataDTO updateDataDTO = new UpdateDataDTO();
            updateDataDTO.setPk(jobUser.getId());
            updateDataDTO.setUpdated_by(usersRepository.getIdUser(username));
            updateDataDTO.setEntity(jobUser.getClass().getSimpleName());
            updateDataDTO.setUpdated_at(LocalDateTime.now());
            InfoUpdateUserDTO response = new InfoUpdateUserDTO(true, "Succesfully Updated Data", updateDataDTO);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body(bindingResult.getAllErrors());
    }

    @DeleteMapping("/deleteJobUser/{id}")
    public ResponseEntity<Object> deleteJobUser(@PathVariable("id") Long id){
        if(username.equals("")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        var jobUser = jobsRepository.findById(id).get();
        jobUser.setDeleteBy(usersRepository.getIdUser(username));
        jobUser.setDeleteAt(LocalDateTime.now());
        DeleteDataDTO deleteDataDTO = new DeleteDataDTO();
        deleteDataDTO.setPk(jobUser.getId());
        deleteDataDTO.setDeleted_by(usersRepository.getIdUser(username));
        deleteDataDTO.setEntity(jobUser.getClass().getSimpleName());
        deleteDataDTO.setDeleted_at(LocalDateTime.now());
        InfoDeleteUserDTO response = new InfoDeleteUserDTO(true, "Succesfully Delete Data", deleteDataDTO);
        jobsRepository.save(jobUser);
        return ResponseEntity.status(200).body(response);
    }
}
