package com.davvi.message_app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id", nullable = false)
    private User recipient;

    @Enumerated(value = EnumType.STRING)
    private FriendRequestStatus status;

    public void acceptFriendRequest() {
        status = FriendRequestStatus.ACCEPTED;
    }
}
