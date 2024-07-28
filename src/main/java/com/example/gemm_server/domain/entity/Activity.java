package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@DynamicInsert
@SQLDelete(sql = "UPDATE activity SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "activity")
public class Activity extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="title", length = 30, nullable = false)
    private String title;

    @Column(name="age", nullable = false)
    private Short age;

    @Column(name="material_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    @Column(name="content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Activity(String title, Short age, MaterialType materialType, String content) {
        this.title = title;
        this.age = age;
        this.materialType = materialType;
        this.content = content;
    }
}
