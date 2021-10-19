package org.zerock.sb.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sb.dto.BoardDTO;
import org.zerock.sb.entity.Board;

import java.util.Arrays;


@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testSearch1() {
        char[] typeArr = null;
        String keyword = null;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.search1(typeArr, keyword, pageable);

        result.get().forEach(board -> {
            log.info(board);
            log.info("-------------------------------------");

            BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

            log.info(boardDTO); //Board를 BoardDTO로 자동으로 변환시켜준다.
        });
    }

    @Test
    public void testEx1() {

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.ex1(pageable);



        result.get().forEach(element -> {

            Object[] arr = (Object[])element; //다운캐스팅해준다. Object[] 타입이기에 //JPA에서 모든 카운트는 Long이다.

            log.info(Arrays.toString(arr));

        });

    }






}
