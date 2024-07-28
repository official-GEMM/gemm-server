package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@SQLDelete(sql = "UPDATE activity SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "generation")
public class Generation extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member ownerId;

    @OneToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activityId;

    @Builder
    public Generation(Member ownerId, Activity activityId) {
        this.ownerId = ownerId;
        this.activityId = activityId;
    }
}
