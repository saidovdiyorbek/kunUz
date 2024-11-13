package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.Profile;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer>, JpaRepository<Profile, Integer> {
    @Query("from Profile where email = ?1")
    Optional<Profile> findByEmail(String email);

    Profile findByPhone(String phone);
    Page<Profile> findAll(Pageable pageable);

    Optional<Profile> findByIdAndVisibleTrue(Integer id);

    Optional<Profile> findByEmailAndVisibleTrue(@NotBlank String email);
}
