package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.article.ArticleTypeDTO;
import dasturlash.uz.kun_uz.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {
    @Autowired
    ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> add(@RequestBody ArticleTypeDTO articleTypeDTO) {
        return ResponseEntity.ok(articleTypeService.add(articleTypeDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ArticleTypeDTO>> getAll() {
        return ResponseEntity.ok(articleTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleTypeDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(articleTypeService.getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ArticleTypeDTO> update(@PathVariable Integer id, @RequestBody ArticleTypeDTO articleTypeDTO) {
        return ResponseEntity.ok(articleTypeService.update(id, articleTypeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }
}
