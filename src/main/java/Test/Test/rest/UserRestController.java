package Test.Test.rest;

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

    @GetMapping
    public ResponseEntity<Object> getAllUser(){
        var response = usersService.getUsers();
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable("id") Long id){
        var response = usersService.getOneUser(id);
        return ResponseEntity.status(200).body(response);
    }
}
