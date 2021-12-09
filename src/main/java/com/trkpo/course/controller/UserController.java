package com.trkpo.course.controller;

import com.trkpo.course.converter.UserConverter;
import com.trkpo.course.dto.UserDTO;
import com.trkpo.course.entity.CustomUserDetails;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.CredentialRepository;
import com.trkpo.course.repository.PostRepository;
import com.trkpo.course.repository.UserRepository;
import com.trkpo.course.service.PostService;
import com.trkpo.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserConverter userConverter;

    @PutMapping("/v1/user")
    public ResponseEntity<?> edit(@RequestBody UserDTO userDTO) {
        User user = userConverter.convertUserDTOToEntity(userDTO, getUserFromContext());
        return ResponseEntity.ok(userConverter.convertUserEntityToDTO(userRepository.save(user)));
    }

    @GetMapping("/v1/user")
    public ResponseEntity<?> getAuthorizedUserInfo() {
        return ResponseEntity.ok(userConverter.convertUserEntityToDTO(getUserFromContext()));
    }

    @GetMapping("/v1/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userConverter.convertUserEntityToDTO(user.get()));
    }

    @GetMapping("/v1/user/favourites")
    public ResponseEntity<?> getUserFavourites() {
        User user = userRepository.getById(getUserFromContext().getId());
        List<User> favourite = user.getFavourites();
        return ResponseEntity.ok().body(favourite.stream().map(it -> userConverter.convertUserEntityToDTO(it)).collect(Collectors.toList()));
    }

    @PostMapping("/v1/user/favourites/{id}")
    public ResponseEntity<?> addUserFavourites(@PathVariable Long id) {
        if (userService.addFavourites(id, getUserFromContext())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/v1/user/news")
    public ResponseEntity<?> getNews() {
        User user = getUserFromContext();
        return ResponseEntity.ok().body(postService.getNews(user));
    }

    @GetMapping("/v1/user/posts")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok().body(postService.getPosts(getUserFromContext()));
    }

    @Transactional
    @DeleteMapping("/v1/user")
    public ResponseEntity<?> deleteUser() {
        credentialRepository.deleteByUserId(getUserFromContext().getId());
        postRepository.deleteAllByUserId(getUserFromContext().getId());
        userRepository.deleteById(getUserFromContext().getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/search/users")
    public ResponseEntity<?> searchUser() {
        return ResponseEntity.ok().body(userRepository.findAll().stream().map(it -> userConverter.convertUserEntityToDTO(it)).collect(Collectors.toList()));
    }


    private User getUserFromContext() {
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getById(Long.valueOf(id));
    }
}
