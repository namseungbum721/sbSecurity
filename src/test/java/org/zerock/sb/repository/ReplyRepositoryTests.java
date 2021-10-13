package org.zerock.sb.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insert200() {
        IntStream.rangeClosed(1,200).forEach(i -> {

            Long bno = (long)(200 - (i % 5)); //200,199,198,196

            int replyCount = (i % 5); //0, 1, 2, 3, 4

            IntStream.rangeClosed(0, replyCount).forEach(j -> {


                Board board = Board.builder().bno(bno).build(); //보드객체를 만들어서 주입해준다. 아이디만 있으면 식별이 가능하기에 bno 선언

                Reply reply = Reply.builder()
                        .replyText("Reply....")
                        .replyer("replyer..")
                        .board(board)
                        .build();

                replyRepository.save(reply);
            });//inner loop

        });//outer loop
    }

    @Transactional
    @Test
    public void testRead() {
        Long rn = 1L;

        Reply reply = replyRepository.findById(rn).get();

        log.info(reply);

        log.info(reply.getBoard());
    }

    @Test
    public void testByBno() {
        Long bno = 200L;

        List<Reply> replyList = replyRepository.findReplyByBoard_BnoOrderByRno(bno);

        replyList.forEach(reply -> log.info(reply));
    }

    @Test
    public void testListOfBoard() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.getListByBno(197L, pageable);

        log.info(result.getTotalElements());

        result.get().forEach(reply -> log.info(reply));
    }


}
