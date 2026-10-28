package lander.tawan.auth.controller;

import jakarta.validation.Valid;
import lander.tawan.auth.dto.LoginRequest;
import lander.tawan.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi{
    private final AuthService authService;

    @Override
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest req){
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> logout(){
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> refresh(){
        return ResponseEntity.noContent().build();
    }
}
