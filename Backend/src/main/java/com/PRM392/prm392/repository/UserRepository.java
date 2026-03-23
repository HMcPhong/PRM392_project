package com.PRM392.prm392.repository;

import com.PRM392.prm392.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
