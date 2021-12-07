package com.trkpo.course.converter;

import com.trkpo.course.dto.PostDTO;
import com.trkpo.course.entity.Picture;
import com.trkpo.course.entity.Post;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostConverter {
    @Autowired
    private PictureRepository pictureRepository;

    public Post convertPostDTOtoEntity(PostDTO post, User user) {
        Post newPost = new Post();
        if (post.getPictureId() != null) {
            Optional<Picture> pic = pictureRepository.findById(post.getPictureId());
            pic.ifPresent(newPost::setPicture);
        }
        newPost.setUser(user);
        newPost.setDateTime(post.getDateTime());
        newPost.setPrivate(post.isPrivate());
        newPost.setText(post.getText());
        newPost.setSpanJson(post.getSpanJson());
        return newPost;
    }

    public PostDTO convertPostEntityToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setDateTime(post.getDateTime());
        postDTO.setPrivate(post.isPrivate());
        postDTO.setSpanJson(post.getSpanJson());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setText(post.getText());
        if (post.getPicture() != null) {
            postDTO.setPictureId(post.getPicture().getId());
        }
        return postDTO;
    }
}
