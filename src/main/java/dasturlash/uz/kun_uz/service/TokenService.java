package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.entity.Token;
import dasturlash.uz.kun_uz.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public String create(Token token) {
        token.setExpiryDate(token.getExpiryDate().plusDays(30));
        token.setToken(token.getToken());
        tokenRepository.save(token);
        return token.toString();
    }

    public Token getToken(){
        return tokenRepository.getToken();
    }
}
