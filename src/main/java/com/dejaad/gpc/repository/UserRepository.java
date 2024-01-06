package com.dejaad.gpc.repository;

import com.dejaad.gpc.model.oauth.AuthProvider;
import com.dejaad.gpc.model.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing User data.
 * This repository provides methods for common database operations involving the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Finds a User by provider ID and where the provider is not the given provider.
     *
     * @param providerId The provider ID of the User to find.
     * @param provider The provider that the User should not have.
     * @return An Optional containing the User if found, or empty if not.
     */
    @Query("select user from User user where user.providerId = ?1 and user.provider <> ?2")
    Optional<User> findByProviderIdAndProviderNot(String providerId, AuthProvider provider);
}
