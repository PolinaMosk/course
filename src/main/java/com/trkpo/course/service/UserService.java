package com.trkpo.course.service;

import com.trkpo.course.entity.User;
import com.trkpo.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean addFavourites(Long id, User user) {
        Optional<User> favouriteUser = userRepository.findById(id);
        if (favouriteUser.isEmpty()) return false;
        user.getFavourites().add(favouriteUser.get());
        return true;
    }
}
