package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.enums.AppLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {
    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    public String getMessage(String code, AppLanguage lang) {
        return resourceBundleMessageSource.getMessage(code, null, new Locale(lang.name()));
    }

}
