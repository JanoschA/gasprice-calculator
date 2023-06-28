package com.dejaad.gpc.service;

import com.dejaad.gpc.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserById(String id) throws ResourceNotFoundException;
}
