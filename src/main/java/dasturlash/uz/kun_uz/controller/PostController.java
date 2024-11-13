package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.PostDTO;
import dasturlash.uz.kun_uz.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("")
    public ResponseEntity<PostDTO> create(PostDTO postDTO){
        return ResponseEntity.ok(postService.create(postDTO));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable String id, @RequestBody PostDTO postDTO){
        return null;
    }
}
