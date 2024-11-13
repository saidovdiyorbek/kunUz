package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface TokenRepository extends CrudRepository<Token, Integer> {


    @Query("from Token s")
    Token getToken();
}
