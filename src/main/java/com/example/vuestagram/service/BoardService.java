package com.example.vuestagram.service;

import com.example.vuestagram.model.Board;
import com.example.vuestagram.model.QBoard;
import com.example.vuestagram.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    public Board test() {
        // Optional<Board> board = boardRepository.findById(40L);

        // QueryDSL
        QBoard qBoard = QBoard.board; // QueryDSL이 자동으로 생성해주는 Board 엔티티 기반의 클래스

        JPAQuery<Board> query = queryFactory.selectFrom(qBoard) // select(컬럼명), QCLass 넣으면 정의되어 있는 컬럼들이 나옴, select절과 from절에서 부르는게 같으면 selectFrom으로
                                    .where(
                                            qBoard.boardId.eq(40L) // 같은거 equal
                                    );
        return query.fetchFirst(); // 이전까지는 fetch가 안된 상태
        // fetchAll은 잘 안씀 -> JPAQuery<Board>로 리턴
        // fetch -> List<Board>로 리턴
    }
}
