package org.ganapati.project.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.CommonConstants;
import org.ganapati.project.ecommerce.entity.User;
import org.ganapati.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    private final CacheManager cacheManager;
    private final UserRepository userRepository;

    @Autowired
    public CacheServiceImpl(CacheManager cacheManager, UserRepository userRepository) {
        this.cacheManager = cacheManager;
        this.userRepository = userRepository;
    }

    @Override
    public void clearAll() {
        cacheManager.getCache(CommonConstants.CATEGORY_KEY_VALUE).clear();
    }

    @Override
    @Cacheable(value = "user", key = "#email", unless = "#result==null")
    public User findUserByEmail(String email) {
        log.info("UserByEmail fetching from DB: {} ", email);
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    @CachePut(value = "user", key = "#user.email", unless = "#result==null")
    public User updateUser(User user) {
        log.info("user update:{} ", user);
        return userRepository.save(user);
    }
}
