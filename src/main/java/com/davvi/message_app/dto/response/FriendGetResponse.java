package com.davvi.message_app.dto.response;

import com.davvi.message_app.domain.FriendRequestStatus;

public record FriendGetResponse(String senderUsername, FriendRequestStatus status) {
}
