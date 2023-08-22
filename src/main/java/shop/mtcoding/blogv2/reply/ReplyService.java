package shop.mtcoding.blogv2.reply;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRepository;
import shop.mtcoding.blogv2.user.User;


@Service
public class ReplyService {

@Autowired
private ReplyRepository replyRepository;

@Autowired
private BoardRepository boardRepository;


 @Transactional
    public void 댓글삭제(Integer id,Integer sessionUserId) {
        //권한체크
        Optional<Reply> replyOp = replyRepository.findById(id);

        if(replyOp.isEmpty()){
            throw new MyApiException("삭제할 댓글이 없습니다.");
        }

            Reply reply = replyOp.get();
            if(reply.getUser().getId() != sessionUserId){
                throw new MyApiException(" 해당 댓글을 삭제할 권한이 없습니다.");
            }
        
            replyRepository.deleteById(id);

    }

     @Transactional
    public void 댓글쓰기(ReplyRequest.SaveDTO saveDTO, Integer sessionId) {
        // insert into reply_tb(comment, board_id, user_id) values(?,?,?)

        // 비즈니스 로직 1. board id가 존재하는지 유무
        //(1-1. DB에서 먼저 Optional로 조회를 하고 없으면 없다고 리턴 )
        //(1-2. 일단 들어오는대로 다 집어 넣는방법-유령데이터가 생긴다.)
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionId).build();//비영속 아직 디비안감

        Reply reply = Reply.builder() //아직 비영속 디비접근안함
        .comment(saveDTO.getComment())
        .board(board)
        .user(user)
        .build();

        replyRepository.save(reply);//entity : Reply 객체
        //save로 영속성 컨텍스트에 들어가니까 DB에 집어넣는거고 영속화 동기화되는거..
    }

}
