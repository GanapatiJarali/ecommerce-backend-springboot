package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.AdminUserRequest;
import org.ganapati.project.ecommerce.dto.UserRequest;
import org.ganapati.project.ecommerce.dto.UserResponse;

public interface UserService {
    BaseResponse<UserResponse> registerUser(UserRequest request);

    BaseResponse<UserResponse> createAdminUser(AdminUserRequest request);

    BaseResponse<UserResponse> getUserEmail(String email);

    BaseResponse<UserResponse> updateUser(UserRequest request);

    BaseResponse softDeleteUser(String email);
}
