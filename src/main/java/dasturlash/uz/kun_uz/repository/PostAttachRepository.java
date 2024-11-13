package dasturlash.uz.kun_uz.repository;

import dasturlash.uz.kun_uz.entity.PostAttach;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface   PostAttachRepository extends CrudRepository<PostAttach, Integer> {
    @Query("select attachId from PostAttach where postId =?1")
    List<String> findAllByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("delete from PostAttach where postId =?1")
    void deleteByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("delete from PostAttach where postId =?1 and attachId = ?2")
    void deleteByPostIdAndAttachId(Integer postId, String attachId);

}
