package com.example.vuestagram.repository;

import com.example.vuestagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // User는 Entity 클래스, Long은 PK 데이터 타입
    // JpaRepository 이 안에 추상메소드가 정의되어있어서, 얘를 상속받아서 따로 정의 안해도 에러 안남
    // relationship 관련이거나 기본적인것 사용 가능
    Optional<User> findByAccount(String account); // 리턴 타입 메소드명(데이터타입 account) -> 추상메소드 정의
}
