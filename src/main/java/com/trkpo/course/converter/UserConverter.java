package com.trkpo.course.converter;

import com.trkpo.course.dto.UserDTO;
import com.trkpo.course.entity.Credentials;
import com.trkpo.course.entity.Picture;
import com.trkpo.course.entity.User;
import com.trkpo.course.repository.CredentialRepository;
import com.trkpo.course.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter {
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PictureRepository pictureRepository;

    public UserDTO convertUserEntityToDTO(User userEntity) {
        UserDTO userDTO = new UserDTO();
        Optional<Credentials> credentials = credentialRepository.findByUserId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setLogin(credentials.get().getLogin());
        userDTO.setName(userEntity.getName());
        if (userEntity.getPicture() != null) userDTO.setPictureId(userEntity.getPicture().getId());
        return userDTO;
    }

    public User convertUserDTOToEntity(UserDTO userDTO, User userToEdit) {
        Optional<Credentials> principalCredentials = credentialRepository.findByUserId(userToEdit.getId());
        if (userDTO.getName() != null) userToEdit.setName(userDTO.getName());
        if (principalCredentials.isPresent()) {
            if (userDTO.getLogin() != null) {
                principalCredentials.get().setLogin(userDTO.getLogin());
            }
            if (userDTO.getPassword() != null) {
                principalCredentials.get().setPassword(userDTO.getPassword());
            }
            credentialRepository.save(principalCredentials.get());
        }
        if (userDTO.getPictureId() != null) {
            Optional<Picture> picture = pictureRepository.findById(userDTO.getPictureId());
            picture.ifPresent(userToEdit::setPicture);
        }
        return userToEdit;
    }
}
