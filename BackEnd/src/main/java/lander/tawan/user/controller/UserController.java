package lander.tawan.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lander.tawan.enums.paramStatus.ParamStatus;
import lander.tawan.user.dto.UserDataResponse;
import lander.tawan.user.dto.UserPatchRequest;
import lander.tawan.user.dto.UserPutRequest;
import lander.tawan.user.dto.UserRegisterRequest;
import lander.tawan.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController implements UserApi{

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Override
    public List<UserDataResponse> listUsers(@RequestParam(name = "status", defaultValue = "ACTIVE") ParamStatus status){
        return userService.listUsers(status);
    }

    @Override
    public UserDataResponse getUser(@PathVariable UUID id){
        return userService.getUser(id);
    }

    @Override
    public ResponseEntity<UserDataResponse> registerUser(@Valid @RequestBody UserRegisterRequest req){
        UserDataResponse user = userService.registerUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    public ResponseEntity<Void> patchUser(@PathVariable UUID id, @Valid @RequestBody UserPatchRequest req){
        userService.patchUser(id, req);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> putUser(@PathVariable UUID id, @Valid @RequestBody UserPutRequest req){
        userService.putUser(id, req);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> restoreUser(@PathVariable UUID id){
        userService.restoreUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> hardDeleteUser(@PathVariable UUID id){
        userService.hardDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
