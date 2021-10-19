package org.zerock.sb.entity;


import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity //엔티티를 사용하면 id값을 주어야한다. 안주면 에러
@Table(name = "tbl_diary")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"tags", "pictures"}) //이제 ToString 처리 무조건 해준다!!
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

    @ElementCollection(fetch = FetchType.LAZY)//중요 값객체를 선언할때 사용하는 어노테이션
    @CollectionTable(name = "tbl_diary_tag")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    @Builder.Default
    private Set<String> tags = new HashSet<>(); //종속적으로 다이어리에 속해 있고 다이어리를 표현하는 정도이다. 다이어리가 삭제되면 태그도 자동삭제처리됨.

    //파일처리순서 2번
    @ElementCollection(fetch = FetchType.LAZY)//중요 값객체를 선언할때 사용하는 어노테이션 //첨부파일을 처리할 땐 OneToMany를 사용할 수도 있다.
    @CollectionTable(name = "tbl_diary_picture")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<DiaryPicture> pictures;


}
