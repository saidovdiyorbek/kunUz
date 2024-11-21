package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.*;
import dasturlash.uz.kun_uz.dto.profile.ProfileDTO;
import dasturlash.uz.kun_uz.enums.AppLanguage;
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
    public ResponseEntity<String> registration(@RequestBody @Valid RegistrationDTO dto,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang){
        return ResponseEntity.ok(authService.registration(dto, lang));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody  @Valid AuthDTO dto,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang){
        return ResponseEntity.ok(authService.login(dto, lang));
    }

    @PostMapping("/restPassword")
    public ResponseEntity<String> rest(@RequestBody  @Valid AuthDTO dto,
                                       @RequestHeader(value = "Accept-Language",defaultValue = "UZ")AppLanguage lang){
        return ResponseEntity.ok(authService.restPassword(dto, lang));
    }

    @GetMapping("/registration/confirm/{id}")
    public ResponseEntity<String> registration(@PathVariable Integer id,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang){
        return ResponseEntity.ok(authService.registrationConfirm(id, lang));
    }

    @PostMapping("/registration/sms/confirm")
    public ResponseEntity<String> smsConfirm(@RequestBody SmsConfirmDTO dto,
                                             @RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang){
        return ResponseEntity.ok(authService.smsConfirm(dto, lang));
    }





    @GetMapping("/restPassword/confirmCode/{code}/email")
    public ResponseEntity<String> restPasswordConfirmCode(@PathVariable Integer code,
                                                          @RequestParam String email,
                                                          @RequestBody String password,
                                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ")AppLanguage lang){
        return ResponseEntity.ok(authService.confirmCodeForRestPassword(code,email,password, lang));
    }


}
