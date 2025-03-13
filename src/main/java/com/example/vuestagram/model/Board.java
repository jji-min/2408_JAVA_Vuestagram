package com.example.vuestagram.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EnableJpaAuditing // createdAT, updateAT 자동으로 관리
@EntityListeners(AuditingEntityListener.class) // Entity를 계속 감시
@Table(name = "boards") // 연결할 테이블명 지정
@SQLDelete(sql = "UPDATE boards SET updated_at = NOW(), deleted_at = NOW() WHERE board_id = ?")
@Where(clause = "deleted_at IS NULL")
public class Board {
    @Id // PK, PK기 때문에 기본적으로 notNull
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto Increment처럼 관리
    @Column(name = "board_id") // 연결할(Mapping) 컬럼명, 간단한 제약조건 설정(notNull, 크기, unique 설정 등)
    private Long boardId; // long -> bigInt, int -> int

    @ManyToOne // Board가 Many, User가 One, 기본 EAGER
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "img", nullable = false, length = 100)
    private String img;

    @Column(name = "likes", nullable = false, length = 11)
    private int likes;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
