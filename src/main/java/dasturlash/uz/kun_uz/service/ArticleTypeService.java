package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.ArticleTypeDTO;
import dasturlash.uz.kun_uz.dto.RegionDTO;
import dasturlash.uz.kun_uz.entity.ArticleType;
import dasturlash.uz.kun_uz.entity.Region;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO add(ArticleTypeDTO articleTypeDTO) {
        ArticleType articleType = new ArticleType();
        articleType.setCreatedDate(LocalDateTime.now());

        articleType.setName_en(articleTypeDTO.getName_en());
        articleType.setName_ru(articleTypeDTO.getName_ru());
        articleType.setName_uz(articleTypeDTO.getName_uz());
        articleType.setOrderNumber(articleTypeDTO.getOrderNumber());
        articleType.setVisible(articleTypeDTO.getVisible());
        articleType.setCreatedDate(LocalDateTime.now());
        articleTypeRepository.save(articleType);

        articleTypeDTO.setId(articleType.getId());
        return articleTypeDTO;
    }

    public List<ArticleTypeDTO> getAll() {
        List<ArticleTypeDTO> articleTypeDTOS = new ArrayList<>();
        for (ArticleType articleType : articleTypeRepository.findAll()) {
            ArticleTypeDTO dto = toDTO(articleType);
            articleTypeDTOS.add(dto);
        }
        return articleTypeDTOS;
    }



    public ArticleTypeDTO toDTO(ArticleType articleType) {
        ArticleTypeDTO articleTypeDTO = new ArticleTypeDTO();

        articleTypeDTO.setId(articleType.getId());
        articleTypeDTO.setName_uz(articleType.getName_uz());
        articleTypeDTO.setName_ru(articleType.getName_ru());
        articleTypeDTO.setName_en(articleType.getName_en());
        articleTypeDTO.setVisible(articleType.getVisible());
        articleTypeDTO.setCreatedDate(articleType.getCreatedDate());
        return articleTypeDTO;
    }

    public ArticleTypeDTO getById(Integer id) {
        Optional<ArticleType> byId = articleTypeRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Article type with id " + id + " not found");
        }
        return toDTO(byId.get());
    }

    public ArticleTypeDTO update(Integer id, ArticleTypeDTO articleTypeDTO) {
        Optional<ArticleType> byId = articleTypeRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Article type with id " + id + " not found");
        }
        ArticleType articleType = byId.get();
        articleType.setName_uz(articleTypeDTO.getName_uz());
        articleType.setName_ru(articleTypeDTO.getName_ru());
        articleType.setName_en(articleTypeDTO.getName_en());
        articleType.setOrderNumber(articleTypeDTO.getOrderNumber());
        articleType.setVisible(articleTypeDTO.getVisible());
        articleTypeRepository.save(articleType);

        articleTypeDTO.setId(articleType.getId());
        articleTypeDTO.setCreatedDate(articleType.getCreatedDate());
        return articleTypeDTO;
    }

    public Boolean delete(Integer id) {
        Optional<ArticleType> byId = articleTypeRepository.findById(id);
        if (!byId.isPresent()){
            throw new AppBadException("Article type with id " + id + " not found");
        }
        articleTypeRepository.deleteById(id);
        return true;
    }
}
