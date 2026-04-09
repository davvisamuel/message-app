package com.davvi.message_app.repository;

import com.davvi.message_app.domain.FriendRequest;
import com.davvi.message_app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("SELECT fr FROM FriendRequest fr WHERE (fr.sender = ?1 and fr.recipient = ?2) OR (fr.sender = ?2 and fr.recipient = ?1)")
    Optional<FriendRequest> findBySenderAndRecipient(User sender, User recipient);

    Page<FriendRequest> findAllByRecipient(User recipient, Pageable pageable);

}
