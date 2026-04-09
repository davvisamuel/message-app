package com.davvi.message_app.controller;

import com.davvi.message_app.domain.User;
import com.davvi.message_app.dto.request.FriendPostRequest;
import com.davvi.message_app.dto.response.FriendGetResponse;
import com.davvi.message_app.dto.response.FriendPostResponse;
import com.davvi.message_app.mapper.FriendMapper;
import com.davvi.message_app.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/friend")
public class FriendController {

    private final FriendMapper friendMapper;
    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<FriendPostResponse> sendFriendRequest(@RequestBody FriendPostRequest friendPostRequest,
                                                                @AuthenticationPrincipal User sender) {

        var friendRequest = friendService.sendFriendRequest(sender, friendPostRequest.recipientUsername());

        var friendPostResponse = friendMapper.toFriendPostResponse(friendRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(friendPostResponse);
    }

    @GetMapping
    public ResponseEntity<Page<FriendGetResponse>> findAllFriendRequests(@AuthenticationPrincipal User recipient, Pageable pageable) {

        var friendRequestPage = friendService.findAllFriendRequests(recipient, pageable);

        var friendGetResponsePage = friendRequestPage.map(friendMapper::toFriendGetResponse);

        return ResponseEntity.ok(friendGetResponsePage);
    }
}
