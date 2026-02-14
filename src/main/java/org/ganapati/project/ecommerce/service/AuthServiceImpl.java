package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.common.Result;
import org.ganapati.project.ecommerce.config.JwtUtil;
import org.ganapati.project.ecommerce.dto.LoginRequest;
import org.ganapati.project.ecommerce.dto.LoginResponse;
import org.ganapati.project.ecommerce.entity.User;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ValidationException(1005, "user not found..!", "user not found..!"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ValidationException(1008, "Invalid credentials....!", "Invalid credentials....!");
        }
        if (!user.isStatus()) {
            throw new ValidationException(1009, "User not active...!", "User not active...!");
        }
        String token = jwtUtil.generateToken(user);
        Result response = new Result();
        response.setSuccessCode(0);
        response.setSuccessDescription("Success");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(new LoginResponse(token, user.getName(), user.getEmail()));
        baseResponse.setResult(response);
        return baseResponse;
    }
}
