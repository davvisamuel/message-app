package com.davvi.message_app.mapper;

import com.davvi.message_app.domain.User;
import com.davvi.message_app.dto.request.AuthRegisterPostRequest;
import com.davvi.message_app.dto.response.AuthLoginPostResponse;
import com.davvi.message_app.dto.response.AuthRegisterPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    User toUser(AuthRegisterPostRequest authRegisterPostRequest);

    AuthRegisterPostResponse toAuthRegisterPostResponse(User user);

    AuthLoginPostResponse toAuthLoginPostResponse(String token);
}
