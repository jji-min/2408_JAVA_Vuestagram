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

// DTO 객체
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EnableJpaAuditing // createdAT, updateAT 자동으로 관리
@EntityListeners(AuditingEntityListener.class) // Entity를 계속 감시
@Table(name = "users") // 연결할 테이블명 지정
@SQLDelete(sql = "UPDATE users SET updated_at = NOW(), deleted_at = NOW() WHERE user_id = ?") // softDeletes 지정, 기본적으로 물리삭제임 -> update처리 해줌, deletedAt 기본적으로 감지하지 않음
@Where(clause = "deleted_at IS NULL") // softDeletes 된것 제외하고 가져오도록 설정, select할 때 clause에 있는 조건도 추가하라는 뜻
public class User {
    @Id // PK, PK기 때문에 기본적으로 notNull
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto Increment처럼 관리
    @Column(name = "user_id") // 연결할(Mapping) 컬럼명, 간단한 제약조건 설정(notNull, 크기, unique 설정 등)
    private Long userId; // long -> bigInt, int -> int

    @Column(name = "account", unique = true, nullable = false, length = 20)
    private String account; // 데이터타입은 String -> varchar

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "profile", length = 100) // nullable의 default는 true, 생략 가능
    private String profile;

    @Column(name = "gender", nullable = false, length = 1) // columnDefinition = "" -> 쿼리구문을 넣을 수 있음, 잘 안씀
    private String gender;

    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
