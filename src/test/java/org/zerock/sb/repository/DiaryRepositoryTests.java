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
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb.dto.DiaryDTO;
import org.zerock.sb.entity.Diary;
import org.zerock.sb.entity.DiaryPicture;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    ModelMapper modelMapper; //

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1,100).forEach(i-> {

            Set<String> tags = IntStream.rangeClosed(1, 3).mapToObj(j -> i + "_tag_" + j).collect(Collectors.toSet());
            Set<DiaryPicture> pictures = IntStream.rangeClosed(1, 3).mapToObj(j -> {
                DiaryPicture picture = DiaryPicture.builder()
                        .uuid(UUID.randomUUID().toString())
                        .savePath("2021/10//18")
                        .fileName("img" + j + ".jpg")
                        .idx(j)
                        .build();
                return picture;
            }).collect(Collectors.toSet());

            Diary diary = Diary.builder()
                    .title("sample.." + i)
                    .content("sample.." + i)
                    .writer("user00")
                    .tags(tags)
                    .pictures(pictures)
                    .build();
            diaryRepository.save(diary);



        });

    }


    @Test
    public void testSelectOne() {
        Long dno = 1L;

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        log.info(diary); //지연로딩이 걸렸기 때문에 No Session 이 걸린다. diary에 tags가 ToString이 걸려 에러발생!!

        log.info(diary.getTags());

        log.info(diary.getPictures());

        //해결책 1번 - ToString에 exclude를 tags를 걸어준다. 1번을 적용해서 문제는 해결되나 다이어리를 볼때 태그도 봐야할때 문제가 생긴다.
        //해결책 2번 - 다음으로 트랜잭션을 걸어 사용
        //해결책 3번 - 게시물 가져올 때 태그는 가져와야 하기 때문에 Eager로딩을 걸어서 사용! 조인까지 해서 셀렉문을 처리한다.

    }

    @Transactional
    @Test
    public void testPaging1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("dno").descending());

        Page<Diary> result = diaryRepository.findAll(pageable);

        result.get().forEach(diary -> {
            log.info(diary);
            log.info(diary.getTags());
            log.info(diary.getPictures());
            log.info("----------------------");
        });


    }

    @Test
    public void testSelectOne2() {
        Long dno = 1L;

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        DiaryDTO dto = modelMapper.map(diary, DiaryDTO.class); //entity 를 DiaryDTO로 변환 테스트

        log.info(dto);



    }


}
