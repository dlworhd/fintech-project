package com.zerobase.fintech.user.repository;

import com.zerobase.fintech.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);

    // Email을 기준으로 정보를 가져오는데 권한 정보도 같이 가져온다.
    @EntityGraph(attributePaths = "authorities") // 쿼리가 수행될 때 Lazy 조회가 아닌 Eager 조회로 authorities 정보를 같이 가져옴
    Optional<User> findOneWithAuthoritiesByUsername(String username);

}
