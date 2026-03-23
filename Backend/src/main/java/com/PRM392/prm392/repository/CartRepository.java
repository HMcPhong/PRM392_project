package com.PRM392.prm392.repository;

import com.PRM392.prm392.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Carts, Integer> {
}
