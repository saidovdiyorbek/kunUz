package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Integer>, JpaRepository<Profile, Integer> {
    Profile findByEmail(String email);
    Profile findByPhone(String phone);
    Page<Profile> findAll(Pageable pageable);
}
