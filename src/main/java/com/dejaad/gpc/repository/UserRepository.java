package com.dejaad.gpc.repository;

import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByProviderIdAndProvider(String providerId, AuthProvider provider);
}
