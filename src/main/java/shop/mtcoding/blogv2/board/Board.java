package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    //ManyToOne Eager 전략(디폴트) 바로땡겨와라 (join을 하든 select를 하든)
    //OneToMany Lazy  전략(디폴트) 무조건 땡기지말구 (Getter호출할때)필요할 때만 떙겨라
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY) 
    //나는 포링키가 아니에여 , 보드는 변수명이다. 연관관계의 주인이 누구인지.
    private List<Reply>replies = new ArrayList<>(); //양방향 매핑(모든걸 다 Lazy로 안바꾸면 무한매핑된다..)

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

}