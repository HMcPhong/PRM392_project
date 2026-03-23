package com.PRM392.prm392.repository;

import com.PRM392.prm392.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserID(int user_id);
}
