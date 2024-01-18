package Test.Test.service;

import Test.Test.dao.DetailUsersRepository;
import Test.Test.dao.JobsRepository;
import Test.Test.dao.UsersRepository;
import Test.Test.dto.Account.AccountDetailDTO;
import Test.Test.dto.Account.RegisterDTO;
import Test.Test.dto.Account.UserDataDTO;
import Test.Test.entity.Users;
import Test.Test.utility.ApplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService implements UserDetailsService{
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DetailUsersRepository detailUsersRepository;

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users register(RegisterDTO dto){
        var entity = new Users();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        var hashPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(hashPassword);
        entity.setCreatedBy(dto.getId());
        entity.setCreatedAt(LocalDateTime.now());
        usersRepository.save(entity);
        return entity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = usersRepository.findById(usersRepository.getIdUser(username)).get();
        if(account == null){
            throw new UsernameNotFoundException("User tidak ditemukan.");
        } else {
            var userDetail = new ApplicationUserDetails(account);
            return userDetail;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        var isAuthenticated = false;
        Long id = usersRepository.getIdUser(username);
        if(id != null){
            var entity = usersRepository.findById(id).get();
            if(entity != null){
                var hashPassword = entity.getPassword();
                isAuthenticated = passwordEncoder.matches(password, hashPassword);
            }
            return isAuthenticated;
        } else {
            return isAuthenticated;
        }
    }

    public AccountDetailDTO getAccountDetail(Long id){
        AccountDetailDTO dto = new AccountDetailDTO();
        Users user = usersRepository.findById(id).get();
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setId(id);
        userDataDTO.setUsername(user.getUsername());
        userDataDTO.setCreated_by(user.getCreatedBy());
        userDataDTO.setCreated_at(user.getCreatedAt());
        userDataDTO.setDetail(detailUsersRepository.getUserDetail(id));
        userDataDTO.setJobs(jobsRepository.getJob(id));
        dto.setStatus(true);
        dto.setData(userDataDTO);
        return dto;
    }
}
