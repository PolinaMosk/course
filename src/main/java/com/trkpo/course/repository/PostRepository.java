package com.trkpo.course.repository;

import com.trkpo.course.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> getAllByUserId(Long id);

    public void deleteAllByUserId(Long id);
}
