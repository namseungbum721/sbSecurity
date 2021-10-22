package org.zerock.sb.repository.search;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.QBoard;
import org.zerock.sb.entity.QReply;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }



    @Override
    public Page<Board> search1(char[] typeArr, String keyword, Pageable pageable) {
        log.info("------------search1");


        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

        //검색조건이 있다면
        if(typeArr != null && typeArr.length > 0){

            BooleanBuilder condition = new BooleanBuilder();

            for(char type: typeArr){
                if(type == 'T'){
                    condition.or(board.title.contains(keyword));
                }else if(type =='C'){
                    condition.or(board.content.contains(keyword));
                }else if(type == 'W'){
                    condition.or(board.writer.contains(keyword));
                }
            }
            jpqlQuery.where(condition);
        }

        jpqlQuery.where(board.bno.gt(0L)); //bno > 0

        JPQLQuery<Board> pagingQuery =
                this.getQuerydsl().applyPagination(pageable, jpqlQuery);

        List<Board> boardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();

        return new PageImpl<>(boardList, pageable, totalCount);

    }

    @Override
    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable) {
        log.info("searchWithReplyCount");

//        select board.bno, board.title, board.writer, board.regDate, count(reply)
//        from Board board
//        left join Reply reply with reply.board = board
//        where board.bno = ?1
//        group by board

        //1.EntityManager를 이용해서 Query
        //2.getQuerydsl( )을 이용하는 방식

        //Query를 만들때는 Q도메인 -- 값을 뽑을때는 엔티티 타입 /값
        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply;

        JPQLQuery<Board> query = from(qBoard);
        query.leftJoin(qReply).on(qReply.board.eq(qBoard));
        query.groupBy(qBoard);

        //검색조건이 있다면
        if(typeArr != null && typeArr.length > 0){

            BooleanBuilder condition = new BooleanBuilder();

            for(char type: typeArr){
                if(type == 'T'){
                    condition.or(qBoard.title.contains(keyword));
                }else if(type =='C'){
                    condition.or(qBoard.content.contains(keyword));
                }else if(type == 'W'){
                    condition.or(qBoard.writer.contains(keyword));
                }
            }
            query.where(condition);
        }


        JPQLQuery<Tuple> selectQuery = query.select(qBoard.bno, qBoard.title, qBoard.writer,qBoard.regDate, qReply.count());

        this.getQuerydsl().applyPagination(pageable, selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();
        long totalCount = selectQuery.fetchCount();

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).
                                                    collect(Collectors.toList());

        return new PageImpl<>(arr, pageable, totalCount);
    }
}
