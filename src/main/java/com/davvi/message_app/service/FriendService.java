package com.davvi.message_app.service;

import com.davvi.message_app.domain.FriendRequest;
import com.davvi.message_app.domain.FriendRequestStatus;
import com.davvi.message_app.domain.User;
import com.davvi.message_app.exception.FriendRequestAlreadyExistsException;
import com.davvi.message_app.exception.FriendRequestNotFoundException;
import com.davvi.message_app.exception.UserNotFoundException;
import com.davvi.message_app.mapper.FriendMapper;
import com.davvi.message_app.repository.FriendRequestRepository;
import com.davvi.message_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendMapper friendMapper;
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public FriendRequest sendFriendRequest(User sender, String recipientUsername) {

        if (sender.getUsername().equals(recipientUsername)) {
            throw new IllegalArgumentException("You cannot send a friend request to yourself");
        }

        var recipient = userRepository.findByUsername(recipientUsername).orElseThrow(() -> new UserNotFoundException("User not found"));

        friendRequestRepository.findFriendRequestBetweenUsers(sender, recipient)
                .ifPresent((fr) -> {
                    if (fr.getStatus() == FriendRequestStatus.ACCEPTED)
                        throw new FriendRequestAlreadyExistsException("This User already your friend");

                    if (fr.getSender().equals(sender))
                        throw new FriendRequestAlreadyExistsException("Friend request already sent to this user");

                    if (fr.getSender().equals(recipient))
                        fr.acceptFriendRequest();
                });


        var friendRequest = friendMapper.toFriendRequest(sender, recipient);

        return friendRequestRepository.save(friendRequest);
    }

    @Transactional(readOnly = true)
    public Page<FriendRequest> findAllFriendRequests(User recipient, Pageable pageable) {

        return friendRequestRepository.findAllByRecipient(recipient, pageable);
    }

    @Transactional
    public void deleteFriendRequest(User currentUser, String username) {

        var targetUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        var friendRequest = friendRequestRepository.findFriendRequestBetweenUsers(currentUser, targetUser)
                .orElseThrow(() -> new FriendRequestNotFoundException("Friend request not found"));

        friendRequestRepository.delete(friendRequest);
    }
}
