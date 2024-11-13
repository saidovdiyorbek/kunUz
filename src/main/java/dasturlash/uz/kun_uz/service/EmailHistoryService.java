package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.EmailHistoryDTO;
import dasturlash.uz.kun_uz.entity.EmailHistory;
import dasturlash.uz.kun_uz.mapper.EmailHistoryInfoMapper;
import dasturlash.uz.kun_uz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailHistoryService {
    @Autowired
    EmailHistoryRepository repository;

    public EmailHistory createEmailHistory(EmailHistory emailHistory) {
        return repository.save(emailHistory);
    }

    public Boolean deleteByEmail(String email) {
        return repository.deleteByEmail(email);
    }

    public LocalDateTime getEmailHistoryCreatDateByEmail(String email) {
        return repository.getEmailHistoryCreatDateByEmail(email);
    }
}
