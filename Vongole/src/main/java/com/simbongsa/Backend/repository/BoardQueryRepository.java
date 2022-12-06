package com.simbongsa.Backend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simbongsa.Backend.entity.Board;
import com.simbongsa.Backend.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.simbongsa.Backend.entity.QBoard.board;
import static com.simbongsa.Backend.entity.QHashtag.*;

@Repository
public class BoardQueryRepository {

    private final EntityManager em;             // em는 스프링이랑 엮어서 쓸 때, 동시성 문제랑 전혀 관계 없이 트랜젝션 단위로 분리되어 따로따로 동작하게 됨.. 진짜 영속성 컨텍스트 entitymanager가 아님. 프록싱(가짜)를 주입해서 트랜잭션 단위로 다 바인딩 되도록 라우팅만 해줌.

    public void save(Board board) {
        em.persist(board);
    }
    private final JPAQueryFactory queryFactory;




    public BoardQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Board> findAll() {
        return queryFactory
                .selectFrom(board)
                .fetch();
    }



    public List<Board> findAllBySearch(Pageable pageable, Tag tag, String startDate, String endDate, String area) {

        return queryFactory
                .selectFrom(board)
                .where(board.id.in(
                        JPAExpressions
                                   .select(hashtag.boardId)
                                 .from(hashtag)
                                  .where(tagIn(tag)))
                , areaEq(area), dateBetween(startDate, endDate))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }



    private BooleanExpression tagIn(Tag tag) {

        if (tag == Tag.ALL) {
            return null;
        }

        return hashtag.tag.in(tag);
    }



    private BooleanExpression areaEq(String area) {

        return area.equals("ALL") ? null : board.area.substring(0,2).eq(area);
    }

    private BooleanExpression dateBetween(String startDate, String endDate) {
        BooleanExpression searchDate;
        LocalDate startDateOfLd = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDateOfLd = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("startDate = " + startDateOfLd);
        searchDate = board.endDate.between(startDateOfLd,endDateOfLd);
        return searchDate;
    }


}
