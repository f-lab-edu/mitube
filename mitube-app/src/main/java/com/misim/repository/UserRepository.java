package com.misim.repository;

import com.misim.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
    User findByNickname(String nickname);

    User findByEmail(String email);
}
