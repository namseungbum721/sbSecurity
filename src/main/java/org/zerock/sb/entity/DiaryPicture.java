package org.zerock.sb.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable //이게 걸려야먄 ElementCollection 으로 사용할 수 있다. PK가 필요하지 않다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//파일처리 순서 1번 -> Diary로 간다
public class DiaryPicture implements Comparable<DiaryPicture> { //entity 가 아니다

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx; //첫번째 사진만 가지고 오고 싶어한다던가


    @Override
    public int compareTo(DiaryPicture o) {
        return this.idx - o.idx; //음수,양수,0 이렇게 세가지만 나온다.
    }
}
