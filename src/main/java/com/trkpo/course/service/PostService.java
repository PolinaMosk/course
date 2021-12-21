package com.trkpo.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trkpo.course.converter.PostConverter;
import com.trkpo.course.dto.PostDTO;
import com.trkpo.course.entity.Picture;
import com.trkpo.course.entity.Post;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.PictureRepository;
import com.trkpo.course.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostConverter postConverter;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void editPost(Post postToEdit, PostDTO post) throws JsonProcessingException {
        if (post.getPictureId() != null) {
            Picture pic = pictureRepository.getById(post.getPictureId());
            postToEdit.setPicture(pic);
        } else {
            if (postToEdit.getPicture() != null) {
                pictureRepository.deleteById(postToEdit.getPicture().getId());
            }
            postToEdit.setPicture(null);
        }
        postToEdit.setSpan(objectMapper.writeValueAsString(post.getSpan()));
        postToEdit.setText(post.getText());
    }

    public List<PostDTO> getPosts(User user) throws JsonProcessingException {
        List<Post> posts = postRepository.getAllByUserId(user.getId());
        List<PostDTO> result = new ArrayList<>();
        for (Post p: posts) {
            result.add(postConverter.convertPostEntityToDTO(p));
        }
        return result;
    }

    public List<PostDTO> getNews(User user) throws JsonProcessingException {
        List<User> favourites = user.getFavourites();
        List<PostDTO> news = new ArrayList<>();
        for (User u: favourites) {
            List<Post> posts = postRepository.getAllByUserId(u.getId());
            for (Post p: posts) {
                PostDTO postDTO = postConverter.convertPostEntityToDTO(p);
                if (!postDTO.isPrivate()) {
                    news.add(postDTO);
                }
            }
        }
        return news.stream().sorted(Comparator.comparing(PostDTO::getDateTime).reversed()).collect(Collectors.toList());
    }
}
