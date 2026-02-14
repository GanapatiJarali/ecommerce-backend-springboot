package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.UserResponse;
import org.ganapati.project.ecommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse entityToUser(User user);
}
