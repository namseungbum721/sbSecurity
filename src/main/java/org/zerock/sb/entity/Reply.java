package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "board") //연관관계를 걸면 ToString 에서 exclude해줘야 한다!
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY) //LAZY 불필요한 값은 안 가져와도 될 때 사용하면 된다. //단방향 참조!!!!!
    private Board board;//객체와 객체를 연결해준다 예전에는 bno를 걸었으나 이젠 객체를 연결시켜준다. //에러가 난 이유는 JPA이기 때문이다. 관계를 서술하지 않으면 에러발생!!

    @CreationTimestamp
    private LocalDateTime replyDate;

}
