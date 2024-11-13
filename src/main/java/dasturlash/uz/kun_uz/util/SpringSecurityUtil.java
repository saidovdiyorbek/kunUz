package dasturlash.uz.kun_uz.util;

import dasturlash.uz.kun_uz.config.CustomUserDetails;
import dasturlash.uz.kun_uz.enums.ProfileRole;
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
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getId();
    }

}
