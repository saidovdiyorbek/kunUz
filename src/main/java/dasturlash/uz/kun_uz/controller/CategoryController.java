package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.CategoryDTO;
import dasturlash.uz.kun_uz.entity.Category;
import dasturlash.uz.kun_uz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.add(categoryDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@RequestParam Integer id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.update(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
