package com.dejaad.gpc.repository;

import com.dejaad.gpc.domain.oauth.AuthProvider;
import com.dejaad.gpc.domain.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByProviderIdAndProvider(String providerId, AuthProvider provider);
}
