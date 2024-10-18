package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    Profile findByEmail(String email);
    Profile findByPhone(String phone);
}
