package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.SmsHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistory, String> {
    Optional<SmsHistory> findTopByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistory set attemptCount = attemptCount + 1 where id = ?1")
    void increaseAttemptCount(String id);
}
