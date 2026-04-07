package org.ganapati.project.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.common.Result;
import org.ganapati.project.ecommerce.config.JwtRequestContext;
import org.ganapati.project.ecommerce.dto.AdminUserRequest;
import org.ganapati.project.ecommerce.dto.UserRequest;
import org.ganapati.project.ecommerce.dto.UserResponse;
import org.ganapati.project.ecommerce.enums.RoleType;
import org.ganapati.project.ecommerce.entity.Roles;
import org.ganapati.project.ecommerce.entity.User;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.mapper.UserMapper;
import org.ganapati.project.ecommerce.repository.RolesRepository;
import org.ganapati.project.ecommerce.repository.UserRepository;
import org.ganapati.project.ecommerce.util.CommonService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RolesRepository rolesRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtRequestContext jwtRequestContext;


    private final CommonService commonService;
    private final CacheService cacheService;

    public UserServiceImpl(UserRepository userRepository, RolesRepository rolesRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtRequestContext jwtRequestContext, CommonService commonService, CacheService cacheService) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestContext = jwtRequestContext;
        this.commonService = commonService;
        this.cacheService = cacheService;
    }

    @Override
    public BaseResponse<UserResponse> registerUser(UserRequest request) {
        // 2. Create User
        commonService.userAlreadyExist(request.getEmail());
        User user = new User();
        user.setMobileNo(request.getMobileNo());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); //
        user.setGender(request.getGender());
        user.setStatus(true);
        user.setDateOfBirth(request.getDateOfBirth());

        User savedUser = userRepository.save(user);

        // 3. Assign Role
        Roles role = new Roles();
        role.setRole(RoleType.USER);
        role.setUser(savedUser);
        role.setStatus(true);
        rolesRepository.save(role);

        // 4. Map Response
        UserResponse userResponse = userMapper.entityToUser(savedUser);

        Result result = new Result();
        result.setSuccessCode(0);
        result.setSuccessDescription("Success");

        BaseResponse<UserResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(userResponse);
        baseResponse.setResult(result);

        return baseResponse;
    }

    @Override
    public BaseResponse<UserResponse> createAdminUser(AdminUserRequest request) {
        log.info("AdminUserRequest : {} ", request);
        commonService.userAlreadyExist(request.getEmail());
        User user = new User();
        user.setMobileNo(request.getMobileNo());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); //
        user.setGender(request.getGender());
        user.setStatus(true);
        user.setDateOfBirth(request.getDateOfBirth());
        User savedUser = userRepository.save(user);
        // 3. Assign Role
        Roles role = new Roles();
        role.setRole(RoleType.valueOf(request.getRole()));
        role.setUser(savedUser);
        role.setStatus(true);
        rolesRepository.save(role);
        // 4. Map Response
        UserResponse userResponse = userMapper.entityToUser(savedUser);
        Result result = new Result();
        result.setSuccessCode(0);
        result.setSuccessDescription("Success");
        BaseResponse<UserResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(userResponse);
        baseResponse.setResult(result);
        return baseResponse;
    }

    public BaseResponse<UserResponse> getUserEmail(String email) {
        User user = cacheService.findUserByEmail(email);
        UserResponse userResponse = userMapper.entityToUser(user);
        return BaseResponse.success(userResponse);
    }

    @Override
    public BaseResponse<UserResponse> updateUser(UserRequest request) {
        String email = jwtRequestContext.getEmail();
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ValidationException(1005, "user not found..!", "user not found..!"));
        if (!email.equalsIgnoreCase(user.getEmail())) {
            throw new ValidationException(1018, "user email mismatch..!", "user email mismatch..!");
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getMobileNo() != null) {
            user.setMobileNo(request.getMobileNo());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        User savedUser = commonService.updateUser(user);
        UserResponse userResponse = userMapper.entityToUser(savedUser);
        Result result = new Result();
        result.setSuccessCode(0);
        result.setSuccessDescription("Success");

        BaseResponse<UserResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(userResponse);
        baseResponse.setResult(result);

        return baseResponse;
    }

    @Override
    public BaseResponse softDeleteUser(String email) {
        log.info("delete User emailId : {} ", email);
        //fetching by email
        String emailJwt = jwtRequestContext.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ValidationException(1005, "user not found..!", "user not found..!"));
        if (!email.equalsIgnoreCase(emailJwt)) {
            throw new ValidationException(1018, "user email mismatch..!", "user email mismatch..!");
        }
        //editing or making deactive user
        user.setStatus(false);
        //save existing user object
        commonService.updateUser(user);
        //setting response
        Result result = new Result();
        result.setSuccessCode(0);
        result.setSuccessDescription("Success");
        BaseResponse<UserResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(null);
        baseResponse.setResult(result);
        return baseResponse;
    }
}
