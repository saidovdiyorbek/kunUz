package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.*;
import dasturlash.uz.kun_uz.entity.EmailHistory;
import dasturlash.uz.kun_uz.entity.Profile;
import dasturlash.uz.kun_uz.entity.Token;
import dasturlash.uz.kun_uz.enums.ProfileStatus;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.EmailHistoryRepository;
import dasturlash.uz.kun_uz.repository.ProfileRepository;
import dasturlash.uz.kun_uz.repository.TokenRepository;
import dasturlash.uz.kun_uz.util.JWTUtil;
import dasturlash.uz.kun_uz.util.MD5Util;
import dasturlash.uz.kun_uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    SmsService smsService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private AttachService attachService;


    public String registration(RegistrationDTO dto) {
        // check email exists

        Profile byUsername = profileService.getByUsernameProfile(dto.getEmail());

        LocalDateTime createDateHistory = null;

        if (byUsername == null) {
            createNewProfileNotExist(dto);
        }

        Optional<EmailHistory> optionalEmailHistory = emailHistoryRepository.findTopByEmailOrderByCreateDateDesc(dto.getEmail());
        if (optionalEmailHistory.isPresent()) {
             createDateHistory = optionalEmailHistory.get().getCreateDate();
        }

        if (
                createDateHistory.isBefore(LocalDateTime.now().minusMinutes(2)) && byUsername != null && byUsername.getStatus() == ProfileStatus.IN_REGISTERED) {
            registrationWithEmail(dto);
            return "Email was sent";
        }
        if (optionalEmailHistory.isPresent() && !optionalEmailHistory.get().getCreateDate().isBefore(LocalDateTime.now().minusMinutes(2))) {
            throw new AppBadException("Time has not yet finished");
        }


        if (byUsername != null && byUsername.getStatus() == ProfileStatus.ACTIVE) {
            throw new AppBadException("Email already in use");
        }
        registrationWithEmail(dto);
        return "Email was sent";
    }

    public ProfileDTO login(AuthDTO dto) {
        Optional<Profile> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password wrong");
        }
        Profile entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.md5(dto.getPassword()))) {
            throw new AppBadException("Email or Password wrong");
        }
        /*if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("User Not Active");
        }*/
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setEmail(entity.getEmail());
        profileDTO.setRole(entity.getRole());
        profileDTO.setJwtToken(JWTUtil.encode(entity.getEmail(), entity.getRole().toString()));
        /*profileDTO.setPhoto(attachService.getDTO(entity.getPhoto_id()));*/

        return profileDTO;
    }

    public String registrationConfirm(Integer code) {

        Optional<EmailHistory> byCode = emailHistoryRepository.findByCode(code);
        if (byCode.isEmpty()) {
            throw new AppBadException("Wrong code");
        }
        if (byCode.get().getCreateDate().isBefore(LocalDateTime.now().minusMinutes(2))) {
            throw new AppBadException("Time out");
        }

        Optional<Profile> byEmail = profileRepository.findByEmail(byCode.get().getEmail());
        Profile entity = byEmail.get();

        if (!entity.getStatus().equals(ProfileStatus.IN_REGISTERED)) {
            return "Not Completed";
        }

        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return "Completed";
    }


    public Boolean checkDuration(LocalDateTime localDateTime){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);
        System.out.println(duration.toMinutes());
        return duration.toMinutes() < 3;
    }


    public String smsConfirm(SmsConfirmDTO dto) {
        Profile byPhone = profileRepository.findByPhone(dto.getPhone());
        if (!byPhone.getStatus().equals(ProfileStatus.IN_REGISTERED)) {
            throw new AppBadException("Not completed");
        }
        // 1. findByPhone()
        // 2. check IN_REGISTRATION

        // check()
        // 3. check code is correct
        // 4. sms expiredTime
        // 5. attempt count  (10,000 - 99,999)
        // change status and update
        return null;
    }



    public String restPassword(AuthDTO dto) {

        Optional<Profile> byEmail = profileRepository.findByEmail(dto.getEmail());
        if (byEmail.isEmpty()){
            throw new AppBadException("Email not found");
        }

        sentEmailForRestPassword(byEmail.get().getEmail());
       /* Profile entity = byEmail.get();
        entity.setPassword(MD5Util.md5(dto.getPassword()));
        ProfileDTO dto1 = profileService.toDTO(entity);
        profileRepository.save(entity);*/
        return "Email wast sent";
    }

    public void registrationWithEmail(RegistrationDTO dto) {
        boolean checkProfileStatus = checkProfileStatus(dto.getEmail());

        if (!checkProfileStatus) {
            createNewProfileNotExist(dto);
        }

        //
        int code = RandomUtil.getRandomInt();
        StringBuilder sb = new StringBuilder();

        sb.append("<h1 style=\"text-align: center\"> Complete Registration</h1>");
        sb.append("<br>");
        sb.append("<p>Click the link below to complete registration</p>\n");
        sb.append("<p><a style=\"padding: 5px; background-color: indianred; color: white\"  href=\"http://localhost:8080/auth/registration/confirm/")
                .append(code).append("\" target=\"_blank\">Click Th</a></p>\n");

        emailSenderService.sendMimeMessage(dto.getEmail(), "Complete Register", sb.toString());

        EmailHistory emailHistory = new EmailHistory();

        emailHistory.setEmail(dto.getEmail());
        emailHistory.setMessage("10 minutes to confirm");
        emailHistory.setCode(code);
        emailHistory.setCreateDate(LocalDateTime.now());
        emailHistoryService.createEmailHistory(emailHistory);
    }

    public void registrationWithSms(RegistrationDTO dto){
        Profile entity = new Profile();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.md5(dto.getPassword()));
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.IN_REGISTERED);
        entity.setVisible(Boolean.TRUE);
        entity.setCreateDate(LocalDateTime.now());
        profileRepository.save(entity);

        Token token = tokenRepository.getToken();
        if (token == null || !LocalDate.now().minusDays(29).isBefore(token.getExpiryDate())){
            smsService.getNewToken();
        }
        smsService.sendRegistrationSms(entity.getEmail());
    }

    public void sentEmailForRestPassword(String email) {
        Optional<Profile> optionProfile = profileRepository.findByEmail(email);

        int code = RandomUtil.getRandomInt();
        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center\"> Complete Registration</h1>");
        sb.append("<br>");
        sb.append("<p>Click the link below to change password</p>\n");
        sb.append("<p><a style=\"padding: 5px; background-color: indianred; color: white\"  href=\"http://localhost:8080/auth/restPassword/confirmCode/")
                .append(code).append(optionProfile.get().getEmail()).append("\" target=\"_blank\">Click Th</a></p>\n");



        emailSenderService.sendMimeMessage(email, "Complete Register", sb.toString());

        EmailHistory emailHistory = new EmailHistory();
        emailHistory.setEmail(email);
        emailHistory.setMessage("10 minutes to confirm, this code rest password");
        emailHistory.setCode(code);
        emailHistory.setCreateDate(LocalDateTime.now());
        emailHistoryService.createEmailHistory(emailHistory);
    }


    public String confirmCodeForRestPassword(Integer code, String email, String password) {
        Optional<EmailHistory> optionalEmailHistory = emailHistoryRepository.findTopByCodeAndEmailOrderByCreateDateDesc(code,email);
        if (optionalEmailHistory.isEmpty()){
            throw new AppBadException("Code is incorrect");
        }
        if (optionalEmailHistory.get().getCreateDate().isAfter(LocalDateTime.now().minusMinutes(10))){
            throw new AppBadException("This code has timed out");
        }
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
        if (optionalProfile.isEmpty()){
            throw new AppBadException("Email is incorrect");
        }
        Profile profile = optionalProfile.get();
        profile.setPassword(MD5Util.md5(password));
        return "Changed password";
    }

    public boolean checkProfileStatus(String email){
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
        if (optionalProfile.isPresent()){
            return true;
        }
        return false;
    }

    public Profile createNewProfileNotExist(RegistrationDTO dto){
        Profile entity = new Profile();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.md5(dto.getPassword()));
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.IN_REGISTERED);
        entity.setVisible(Boolean.TRUE);
        entity.setCreateDate(LocalDateTime.now());
        profileRepository.save(entity);
        return entity;
    }
}
