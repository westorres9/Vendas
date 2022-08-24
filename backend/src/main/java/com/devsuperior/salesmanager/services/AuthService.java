package com.devsuperior.salesmanager.services;

import com.devsuperior.salesmanager.entities.User;
import com.devsuperior.salesmanager.repositories.UserRepository;
import com.devsuperior.salesmanager.services.exceptions.ForbiddenException;
import com.devsuperior.salesmanager.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User authenticated() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UnauthorizedException("Unauthorized");
        }
    }


    public void validateSelf(Long userId) {
        User user = authenticated();
        if(!user.getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }
    }

    public void validateAdmin(Long userId) {
        User user = authenticated();
        if (!user.hasRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Unauthorized");
        }
    }

    public void validateSelfOrAdmin(Long userId) {
        User user = authenticated();
        if (!user.getId().equals(userId) &&(!user.hasRole("ROLE_ADMIN"))) {
            throw new ForbiddenException("Unauthorized");
        }
    }

    public void validateManagerOrAdmin(Long userId) {
        User user = authenticated();
        if (!user.hasRole("ROLE_MANAGER") || (!user.hasRole("ROLE_ADMIN"))) {
            throw new ForbiddenException("Unauthorized");
        }
    }
}
