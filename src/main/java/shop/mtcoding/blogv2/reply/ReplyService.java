package shop.mtcoding.blogv2.reply;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;


@Service
public class ReplyService {

@Autowired
private ReplyRepository replyRepository;


 @Transactional
    public void 댓글삭제하기(Integer id) {
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(id+"번은 없어요");
        }
    }

     @Transactional
    public void 댓글쓰기(ReplyRequest.SaveDTO saveDTO, Integer sessionUserId) {
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(Board.builder().id(saveDTO.getBoardId()).build())
                .user(User.builder().id(sessionUserId).build())
                .build();
        replyRepository.save(reply);
    }

}
