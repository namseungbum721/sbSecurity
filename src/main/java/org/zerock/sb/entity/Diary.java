package org.zerock.sb.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity //엔티티를 사용하면 id값을 주어야한다. 안주면 에러
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dno;


    private String title;

    private String content;

    private String writer;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime modDate;

    @ElementCollection//중요 값객체를 선언할때 사용하는 어노테이션
    private List<String> tags; //종속적으로 다이어리에 속해 있고 다이어리를 표현하는 정도이다. 다이어리가 삭제되면 태그도 자동삭제처리됨.


}
