package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.LoginRequest;
import org.ganapati.project.ecommerce.dto.LoginResponse;

public interface AuthService {
     BaseResponse<LoginResponse> login(LoginRequest request);
}
