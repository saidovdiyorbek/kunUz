package dasturlash.uz.kun_uz.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dasturlash.uz.kun_uz.entity.SmsHistory;
import dasturlash.uz.kun_uz.entity.Token;
import dasturlash.uz.kun_uz.repository.SmsHistoryRepository;
import dasturlash.uz.kun_uz.util.RandomUtil;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void sendRegistrationSms(String phoneNumber) {
        int code = RandomUtil.getRandomInt();
        String message = "Bu Eskiz dan test";
        sendSms(phoneNumber, message);
    }

   public String getNewToken(){
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("email", "saidovdiyer777@gmail.com")
                    .add("password", "YiIU4kEve1K6wxs2E3xRA7yoEUH5qxFyLQn8FY2M")
                    .add("from", "4546")
                    .build();

            Request request = new Request.Builder()
                    .url("https://notify.eskiz.uz/api/auth/login")
                    .addHeader("Content-Type", "application/json")
                    .post(formBody).build();

            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);



            Response response = call.execute();

            String jsonToken = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonToken);

            String getToken =  jsonObject.getJSONObject("data").getString("token");

            Token token = new Token();
            token.setToken(getToken );
            token.setExpiryDate(LocalDate.now());
            tokenService.create(token);

            return getToken;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
   }
    private void sendSms(String phone, String message) {
        try {
            // TODO limit 3
            // TODO save


            RequestBody formBody = new FormBody.Builder()
                    .add("mobile_phone", phone)
                    .add("message", message)
                    .add("from", "4546")
                    .build();

            Request request = new Request.Builder()
                    .url("https://notify.eskiz.uz/api/message/sms/send")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken())
                    .post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);

            Response response = call.execute();
            //save sms history
            SmsHistory smsHistory = new SmsHistory();
            smsHistory.setPhone(phone);
            smsHistory.setMessage(message);
            smsHistory.setCreatedDate(LocalDateTime.now());
            smsHistoryRepository.save(smsHistory);

            System.out.println(response.body().string());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getToken() {
        Token token = tokenService.getToken();

        if (token.getExpiryDate().isBefore(LocalDate.now())) {
            String newToken = getNewToken();
            return newToken;
        }
        return token.getToken();
    }
    public boolean check(String phone, String code) {
        Optional<SmsHistory> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            return false;
        }
        // 3. check code is correct
        SmsHistory smsHistory = optional.get();
        if (!smsHistory.getMessage().equals(code)) {
            smsHistoryRepository.increaseAttemptCount(smsHistory.getId());
            return false;
        }
        // 4. sms expiredTime
        LocalDateTime exp = LocalDateTime.now().minusMinutes(1);
        if (exp.isAfter(smsHistory.getCreatedDate())) {
            return false;
        }
        if (smsHistory.getAttemptCount() >= 3) {
            return false;
        }
        return true;
    }
}
