package com.zerobase.fintech.user.repository;

import com.zerobase.fintech.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // 쿼리가 수행될 때 아이디를 권한과 함께 Lazy 조회가 아닌 Eager 조회로 가져옴
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    Optional<User> findByUsername(String username);

}
