package com.trkpo.course.controller;

import com.trkpo.course.converter.PostConverter;
import com.trkpo.course.dto.PostDTO;
import com.trkpo.course.entity.Post;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.PictureRepository;
import com.trkpo.course.repository.PostRepository;
import com.trkpo.course.repository.UserRepository;
import com.trkpo.course.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private PostConverter postConverter;

    @PostMapping("/v1/post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO post) {
        Post newPost = postConverter.convertPostDTOtoEntity(post, getUserFromContext());
        return ResponseEntity.ok(postConverter.convertPostEntityToDTO(postRepository.save(newPost)));
    }

    @PutMapping("/v1/post/{id}")
    public ResponseEntity<?> editPost(@PathVariable Long id, @RequestBody PostDTO post) {
        Optional<Post> postToEdit = postRepository.findById(id);
        if (postToEdit.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        postService.editPost(postToEdit.get(), post);
        return ResponseEntity.ok(postConverter.convertPostEntityToDTO(postRepository.save(postToEdit.get())));
    }

    @DeleteMapping("/v1/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (pictureRepository.findById(id).isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        pictureRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private User getUserFromContext(){
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getById(Long.valueOf(userId));
    }
}
