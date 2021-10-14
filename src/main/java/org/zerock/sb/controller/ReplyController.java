package org.zerock.sb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.dto.ReplyDTO;
import org.zerock.sb.service.ReplyService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/list/{bno}") //고정으로 bno를 사용하기 위해 이렇게 셋팅 pathable로 bno를 걸어준다.
    public PageResponseDTO<ReplyDTO> getListOfBoard(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO) {

        return replyService.getListOfBoard(bno, pageRequestDTO);

    }

    @PostMapping("")
    public PageResponseDTO<ReplyDTO> register(@RequestBody ReplyDTO replyDTO){//마지막 페이지를 보여주기위해 pageresponseDTO리턴
        //JSON DATA를 받기위해 RequestBody 사용 필요

        replyService.register(replyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();//마지막페이지로 가기위해, page에 -1을넣으면
        //service단에서 if문으로 마지막 페이지 로딩 로직 해줌

        return replyService.getListOfBoard(replyDTO.getBno(), pageRequestDTO);

    }

}
