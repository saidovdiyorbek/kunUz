package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.EmailHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistory, Integer> {
    boolean deleteByEmail(String email);

    @Query("select eh.createDate from EmailHistory eh where eh.email = ?1")
    LocalDateTime  getEmailHistoryCreatDateByEmail(String email);

//    @Query("from EmailHistory where code = ?1 and email = ?2 order by createDate desc nulls first ")
    Optional<EmailHistory> findTopByCodeAndEmailOrderByCreateDateDesc(Integer code,String email);

    Optional<EmailHistory> findByCode(Integer code);

    Optional<EmailHistory> findByEmail(String email);

    Optional<EmailHistory> findTopByEmailOrderByCreateDateDesc(String email);
}