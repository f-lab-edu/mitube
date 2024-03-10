package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "subscriptions")
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long ownerId;

    private Long subscriberId;

    @Builder
    public Subscription(Long ownerId, Long subscriberId) {
        this.ownerId = ownerId;
        this.subscriberId = subscriberId;
    }
}
