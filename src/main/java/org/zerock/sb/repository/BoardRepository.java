package org.zerock.sb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb.entity.Board;
import org.zerock.sb.repository.search.BoardSearch;

import java.util.List;


//여러개의 매개변수 사용가능
public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {


    //board 테이블과 reply 테이블 두개를 조인시켜주는데 없는 reply가 있기에 left 조인을 사용한다. //Board를 기준으로 압축해야 한다. group by 사용!
    @Query("select b.bno,b.title,b.writer,b.regDate, count(r) from Board b left join Reply r on r.board = b group by b") //Board b 의 Board 는 Entity이다
    Page<Object[]> ex1(Pageable pageable);
}
