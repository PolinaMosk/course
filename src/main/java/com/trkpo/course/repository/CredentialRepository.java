package com.trkpo.course.repository;

import com.trkpo.course.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credentials, Long> {
    public Credentials getByLogin(String login);
    public Optional<Credentials> findByLogin(String login);
    public Optional<Credentials> findByUserId(Long id);

    public void deleteByUserId(Long id);
}
