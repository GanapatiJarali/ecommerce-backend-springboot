package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.entity.User;

public interface CacheService {
    void clearAll();

    User findUserByEmail(String email);

    User updateUser(User user);

}
