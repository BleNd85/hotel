package org.hotel.repositories;

import org.hotel.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findFirstByUsername(String login);
    Optional<UserModel> findFirstByEmail(String email);
}

