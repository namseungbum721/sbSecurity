package org.zerock.sb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.sb.entity.Diary;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO {

    private Long dno;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<String> tags;//리스트로 선언한 이유 - 파라미터 수집을 할 때 인덱스 번호를 이용하려면 리스트를 사용하는 게 좋다.
    private List<DiaryPictureDTO> pictures; //모델 Mapper 를 이용해 변환

}
