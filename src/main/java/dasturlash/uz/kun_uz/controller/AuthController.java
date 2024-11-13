package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.*;
import dasturlash.uz.kun_uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody @Valid RegistrationDTO dto){
        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody  @Valid AuthDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/restPassword")
    public ResponseEntity<String> rest(@RequestBody  @Valid AuthDTO dto){
        return ResponseEntity.ok(authService.restPassword(dto));
    }

    @GetMapping("/registration/confirm/{id}")
    public ResponseEntity<String> registration(@PathVariable Integer id){
        return ResponseEntity.ok(authService.registrationConfirm(id));
    }

    @PostMapping("/registration/sms/confirm")
    public ResponseEntity<String> smsConfirm(@RequestBody SmsConfirmDTO dto){
        return ResponseEntity.ok(authService.smsConfirm(dto));
    }





    @GetMapping("/restPassword/confirmCode/{code}/email")
    public ResponseEntity<String> restPasswordConfirmCode(@PathVariable Integer code,
                                                          @RequestParam String email,
                                                          @RequestBody String password){
        return ResponseEntity.ok(authService.confirmCodeForRestPassword(code,email,password));
    }


}
