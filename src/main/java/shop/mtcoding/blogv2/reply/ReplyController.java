package shop.mtcoding.blogv2.reply;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogv2.user.User;



@Controller
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    @PostMapping("/reply/{id}/delete")
    public String replyDelete(@PathVariable Integer id,Integer boardId) {
        replyService.댓글삭제하기(id);


        return "redirect:/board/"+boardId;
    }

    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO saveDTO,Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // System.out.println("sessionuser : " + user.getUsername());
        replyService.댓글쓰기(saveDTO,sessionUser.getId());
        return "redirect:/board/"+saveDTO.getBoardId();
    }
}
