package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.CategoryDTO;
import dasturlash.uz.kun_uz.entity.Category;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDTO add(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setName_uz(categoryDTO.getName_uz());
        category.setName_en(categoryDTO.getName_en());
        category.setName_ru(categoryDTO.getName_ru());
        category.setOrderNumber(categoryDTO.getOrderNumber());
        category.setCreatedDate(LocalDateTime.now());
        category.setVisible(true);
        categoryRepository.save(category);

        categoryDTO.setId(category.getId());
        return categoryDTO;
    }

    public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName_uz(category.getName_uz());
        categoryDTO.setName_en(category.getName_en());
        categoryDTO.setName_ru(category.getName_ru());
        categoryDTO.setOrderNumber(category.getOrderNumber());
        return categoryDTO;
    }

    public List<CategoryDTO> getAll() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            CategoryDTO dto = toDTO(category);
            categoryDTOList.add(dto);
        }
        return categoryDTOList;
    }

    public CategoryDTO getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            throw new AppBadException("Category not found");
        }
        return toDTO(optionalCategory.get());
    }

    public CategoryDTO update(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            throw new AppBadException("Category not found");
        }
        Category category = optionalCategory.get();
        category.setName_uz(categoryDTO.getName_uz());
        category.setName_en(categoryDTO.getName_en());
        category.setName_ru(categoryDTO.getName_ru());
        category.setOrderNumber(categoryDTO.getOrderNumber());
        category.setVisible(true);
        category.setCreatedDate(LocalDateTime.now());
        categoryDTO.setId(category.getId());
        categoryRepository.save(category);
        return toDTO(category);
    }

    public Boolean delete(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            throw new AppBadException("Category not found");
        }
        categoryRepository.deleteById(id);
        return true;
    }
}
