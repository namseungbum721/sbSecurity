package org.zerock.sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.sb.dto.DiaryDTO;
import org.zerock.sb.entity.Diary;
import org.zerock.sb.repository.DiaryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiaryServiceImpl implements DiaryService {

    private final ModelMapper modelMapper;

    private final DiaryRepository diaryRepository;

    @Override //모델멥퍼이용해서 다이어리로 바꿔준다. 엔티티
    public Long register(DiaryDTO dto) {

        Diary diary = modelMapper.map(dto, Diary.class);

        log.info(diary);
        log.info(diary.getTags());
        log.info(diary.getPictures());

        diaryRepository.save(diary);

        return diary.getDno(); //다이어리를 등록하는 기능 완성
    }

    @Override
    public DiaryDTO read(Long dno) {

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        DiaryDTO dto = modelMapper.map(diary, DiaryDTO.class);

        return dto;
    }
}
