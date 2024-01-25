package Test.Test.rest;

import Test.Test.dao.UsersRepository;
import Test.Test.dto.Token.ResponseFailLoginDTO;
import Test.Test.service.AccountService;
import Test.Test.service.UsersService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<Object> getAllUser(){
        var username = accountService.getUsernameAccount();
        if(username.equals("Anda belum login")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        var response = usersService.getUsers();
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable("id") Long id){
        var username = accountService.getUsernameAccount();
        if(username.equals("Anda belum login")){
            var failResponse = new ResponseFailLoginDTO(false, "Unauthorized");
            return ResponseEntity.status(401).body(failResponse);
        }
        var response = usersService.getOneUser(id);
        return ResponseEntity.status(200).body(response);
    }
}
