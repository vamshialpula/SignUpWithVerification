package com.app.registration.respository;

import com.app.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUserId(Long userId);

    List<User> findAllByStatus(String userActive);
}
