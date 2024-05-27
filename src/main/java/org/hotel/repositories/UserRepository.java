package org.hotel.repositories;

import org.hotel.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findFirstByUsername(String username);
    Optional<UserModel> findFirstByEmail(String email);
    Optional<UserModel> findFirstById(Integer id);

    List<UserModel> findByUsernameContainingOrNameContainingOrSurnameContainingOrEmailContaining(
            String username, String name, String surname, String email);
}

