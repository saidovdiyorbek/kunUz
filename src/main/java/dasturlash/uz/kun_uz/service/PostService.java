package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.AttachDTO;
import dasturlash.uz.kun_uz.dto.PostDTO;
import dasturlash.uz.kun_uz.entity.Post;
import dasturlash.uz.kun_uz.entity.PostAttach;
import dasturlash.uz.kun_uz.repository.PostAttachRepository;
import dasturlash.uz.kun_uz.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostAttachService postAttachService;

    public PostDTO create(PostDTO postDTO) {
        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        postRepository.save(post);

        postAttachService.create(post.getId(), postDTO.getAttachList());

        postDTO.setId(post.getId());
        return postDTO;
    }

    public PostDTO getById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Post not found");
        });

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        // attach list
        List<AttachDTO> attachList = postAttachService.getAttachList(id);
        postDTO.setAttachList(attachList);
        return postDTO;
    }

    public PostDTO update(String id, PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(postDTO.getId());
        if (optionalPost.isEmpty()){
            throw new RuntimeException("Post not found");
        }
        Post post = optionalPost.get();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        postRepository.save(post);
        postAttachService.update(post.getId());
        return postDTO;
    }

}
