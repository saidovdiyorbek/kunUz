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
    
    public ArticleTypeDTO getById(Integer id) {
        ArticleType articleType = get(id);
        return toDTO(articleType);
    }

    public ArticleTypeDTO update(Integer id, ArticleTypeDTO articleTypeDTO) {
        ArticleType articleType = get(id);

        articleType.setName_uz(articleTypeDTO.getName_uz());
        articleType.setName_ru(articleTypeDTO.getName_ru());
        articleType.setName_en(articleTypeDTO.getName_en());
        articleType.setOrderNumber(articleTypeDTO.getOrderNumber());

        articleTypeRepository.save(articleType);
        return toDTO(articleType);
    }


    public Boolean delete(Integer id) {
        ArticleType articleType = get(id);
        articleTypeRepository.deleteById(id);
        return true;
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
    
    public ArticleType get(Integer id){
       ArticleType articleType = articleTypeRepository.findById(id).orElseThrow(()-> new AppBadException("Article type with id " + id + " not found"));
       return articleType;
    }
}
