package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.*;
import dasturlash.uz.kun_uz.dto.profile.ProfileDTO;
import dasturlash.uz.kun_uz.entity.EmailHistory;
import dasturlash.uz.kun_uz.entity.Profile;
import dasturlash.uz.kun_uz.entity.Token;
import dasturlash.uz.kun_uz.enums.AppLanguage;
import dasturlash.uz.kun_uz.enums.ProfileStatus;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.EmailHistoryRepository;
import dasturlash.uz.kun_uz.repository.ProfileRepository;
import dasturlash.uz.kun_uz.repository.TokenRepository;
import dasturlash.uz.kun_uz.util.JWTUtil;
import dasturlash.uz.kun_uz.util.MD5Util;
import dasturlash.uz.kun_uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${server.domain}")
    private String serverDomain;

    @Autowired
    private ResourceBundleService resourceBundleService;

    public String registration(RegistrationDTO dto, AppLanguage lang) {
        // check email exists
        LocalDateTime createDateHistory = null;

        Profile byUsername = profileService.getByUsernameProfile(dto.getEmail());
        if (byUsername == null) {
            byUsername = profileService.createNewProfileNotExist(dto);
        }

        Optional<EmailHistory> optionalEmailHistory = emailHistoryRepository.findTopByEmailOrderByCreateDateDesc(dto.getEmail());
        if (optionalEmailHistory.isPresent()) {
             createDateHistory = optionalEmailHistory.get().getCreateDate();
        }

        if (createDateHistory == null && profileService.checkStatusInRegister(byUsername)) {
            registrationWithEmail(dto);
            return resourceBundleService.getMessage("sent.email", lang) ;
        }

        if (createDateHistory.isBefore(LocalDateTime.now().minusMinutes(2)) && profileService.checkStatusInRegister(byUsername)) {
            registrationWithEmail(dto);
            return resourceBundleService.getMessage("sent.email", lang) ;
        }
        if (!optionalEmailHistory.get().getCreateDate().isBefore(LocalDateTime.now().minusMinutes(2))) {
            throw new AppBadException(resourceBundleService.getMessage("time.status", lang));
        }


        if (byUsername != null && byUsername.getStatus() == ProfileStatus.ACTIVE) {
            throw new AppBadException(resourceBundleService.getMessage("wrong.email", lang));
        }
        registrationWithEmail(dto);
        return resourceBundleService.getMessage("sent.email", lang);
    }

    public ProfileDTO login(AuthDTO dto, AppLanguage lang) {
        Optional<Profile> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("email.password.wrong",lang));
        }
        Profile entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.md5(dto.getPassword()))) {
            throw new AppBadException(resourceBundleService.getMessage("email.password.wrong", lang));
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

    public String registrationConfirm(Integer code, AppLanguage lang) {

        Optional<EmailHistory> byCode = emailHistoryRepository.findByCode(code);
        if (byCode.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("code.status",lang));
        }
        if (byCode.get().getCreateDate().isBefore(LocalDateTime.now().minusMinutes(2))) {
            throw new AppBadException(resourceBundleService.getMessage("time.out.status", lang));
        }

        Optional<Profile> byEmail = profileRepository.findByEmail(byCode.get().getEmail());
        Profile entity = byEmail.get();

        if (!entity.getStatus().equals(ProfileStatus.IN_REGISTERED)) {
            return resourceBundleService.getMessage("profile.not.inRegistered", lang);
        }

        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return resourceBundleService.getMessage("profile.confirm", lang);
    }


    public Boolean checkDuration(LocalDateTime localDateTime, AppLanguage lang){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);
        System.out.println(duration.toMinutes());
        return duration.toMinutes() < 3;
    }


    public String smsConfirm(SmsConfirmDTO dto, AppLanguage lang) {
        Profile byPhone = profileRepository.findByPhone(dto.getPhone());
        if (!byPhone.getStatus().equals(ProfileStatus.IN_REGISTERED)) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.inRegistered", lang));
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



    public String restPassword(AuthDTO dto, AppLanguage lang) {

        Optional<Profile> byEmail = profileRepository.findByEmail(dto.getEmail());
        if (byEmail.isEmpty()){
            throw new AppBadException(resourceBundleService.getMessage("email.not.found",lang));
        }

        sentEmailForRestPassword(byEmail.get().getEmail());
       /* Profile entity = byEmail.get();
        entity.setPassword(MD5Util.md5(dto.getPassword()));
        ProfileDTO dto1 = profileService.toDTO(entity);
        profileRepository.save(entity);*/
        return resourceBundleService.getMessage("sent.email", lang);
    }

    public void registrationWithEmail(RegistrationDTO dto) {
        boolean checkProfileStatus = checkProfileStatus(dto.getEmail());

        if (!checkProfileStatus) {
            profileService.createNewProfileNotExist(dto);
        }

        //
        int code = RandomUtil.getRandomInt();
        StringBuilder sb = new StringBuilder();

        sb.append("<h1 style=\"text-align: center\"> Complete Registration</h1>");
        sb.append("<br>");
        sb.append("<p>Click the link below to complete registration</p>\n");
        sb.append("<p><a style=\"padding: 5px; background-color: indianred; color: white\"  href=\"" + serverDomain +"/auth/registration/confirm/")
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


    public String confirmCodeForRestPassword(Integer code, String email, String password, AppLanguage lang) {
        Optional<EmailHistory> optionalEmailHistory = emailHistoryRepository.findTopByCodeAndEmailOrderByCreateDateDesc(code,email);
        if (optionalEmailHistory.isEmpty()){
            throw new AppBadException(resourceBundleService.getMessage("code.status", lang));
        }
        if (optionalEmailHistory.get().getCreateDate().isAfter(LocalDateTime.now().minusMinutes(10))){
            throw new AppBadException(resourceBundleService.getMessage("time.out.status", lang));
        }
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
        if (optionalProfile.isEmpty()){
            throw new AppBadException(resourceBundleService.getMessage("email.not.found", lang));
        }
        Profile profile = optionalProfile.get();
        profile.setPassword(MD5Util.md5(password));
        return resourceBundleService.getMessage("rest.password.status", lang);
    }

    public boolean checkProfileStatus(String email){
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
        if (optionalProfile.isPresent()){
            return true;
        }
        return false;
    }

}
