package com.trkpo.course.converter;

import com.trkpo.course.dto.UserDTO;
import com.trkpo.course.entity.Credentials;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter {
    @Autowired
    private CredentialRepository credentialRepository;

    public UserDTO convertUserEntityToDTO(User userEntity) {
        UserDTO userDTO = new UserDTO();
        Optional<Credentials> credentials = credentialRepository.findByUserId(userEntity.getId());
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(credentials.get().getEmail());
        userDTO.setLogin(credentials.get().getLogin());
        userDTO.setName(userEntity.getName());
        if (userEntity.getPicture() != null) userDTO.setPictureId(userEntity.getPicture().getId());
        return userDTO;
    }
}
