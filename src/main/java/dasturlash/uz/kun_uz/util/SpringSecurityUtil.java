package dasturlash.uz.kun_uz.util;

import dasturlash.uz.kun_uz.config.CustomUserDetails;
import dasturlash.uz.kun_uz.enums.ProfileRole;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.exp.AppForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {

    public static void checkRoleExists(String profileRole, ProfileRole... requiredRoles) {
        for (ProfileRole requiredRole : requiredRoles) {
            if (requiredRole.name().equals(profileRole)) {
                return;
            }
        }
        throw new AppForbiddenException("Forbidden");
    }

    public static Integer getCurrentUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
                if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getId();
            } else if (principal instanceof String && "anonymousUser".equals(((String) principal))) {
                throw new RuntimeException("Anonymous user");
            }
        }
        throw new AppBadException("http://localhost:8080/auth/login");
    }

}
