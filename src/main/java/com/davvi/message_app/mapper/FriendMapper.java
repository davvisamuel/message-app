package com.davvi.message_app.mapper;

import com.davvi.message_app.domain.FriendRequest;
import com.davvi.message_app.domain.User;
import com.davvi.message_app.dto.response.FriendGetResponse;
import com.davvi.message_app.dto.response.FriendPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FriendMapper {

    @Mapping(target = "status", expression = "java(FriendRequestStatus.PENDING)")
    @Mapping(target = "id", ignore = true)
    FriendRequest toFriendRequest(User sender, User recipient);

    FriendPostResponse toFriendPostResponse(FriendRequest friendRequest);

    @Mapping(target = "senderUsername", source = "sender.username")
    FriendGetResponse toFriendGetResponse(FriendRequest friendRequestPage);
}
