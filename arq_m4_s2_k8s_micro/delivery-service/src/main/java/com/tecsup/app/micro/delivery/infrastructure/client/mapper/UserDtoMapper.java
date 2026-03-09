package com.tecsup.app.micro.delivery.infrastructure.client.mapper;

import com.tecsup.app.micro.delivery.domain.model.User;
import com.tecsup.app.micro.delivery.infrastructure.client.dto.UserDto;
import com.tecsup.app.micro.delivery.presentation.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User toDomain(UserDto dto);

    UserResponse toResponse(User user);

}
