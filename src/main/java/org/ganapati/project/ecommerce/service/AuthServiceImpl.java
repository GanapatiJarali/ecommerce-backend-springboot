package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.common.Result;
import org.ganapati.project.ecommerce.config.JwtUtil;
import org.ganapati.project.ecommerce.dto.LoginRequest;
import org.ganapati.project.ecommerce.dto.LoginResponse;
import org.ganapati.project.ecommerce.entity.Roles;
import org.ganapati.project.ecommerce.entity.User;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.repository.RolesRepository;
import org.ganapati.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.management.relation.Role;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final RolesRepository rolesRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ValidationException(1005, "user not found..!", "user not found..!"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ValidationException(1008, "Invalid credentials....!", "Invalid credentials....!");
        }
        if (!user.isStatus()) {
            throw new ValidationException(1009, "User not active...!", "User not active...!");
        }
        List<Roles> roleList = rolesRepository.findByUser(user);
        if (roleList.isEmpty()) {
            throw new ValidationException(1010, "User roles not found..!", "User roles not found..!");
        }
        String token = jwtUtil.generateToken(user,roleList);
        Result response = new Result();
        response.setSuccessCode(0);
        response.setSuccessDescription("Success");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(new LoginResponse(token, user.getName(), user.getEmail()));
        baseResponse.setResult(response);
        return baseResponse;
    }
}
