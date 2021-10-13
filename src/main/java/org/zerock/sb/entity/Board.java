package org.zerock.sb.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Entity //엔티티를 사용하면 id값을 주어야한다. 안주면 에러
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Board {
    @Id //수정이 안된다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    private String writer;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime modDate;

    public void change(String title, String content) { //수정하려고 한다.
        this.title = title;
        this.content = content;
    }
}
