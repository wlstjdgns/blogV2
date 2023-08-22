package shop.mtcoding.blogv2.reply;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.user.User;



@Controller
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    @DeleteMapping("/api/reply/{id}/delete")
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id) {
        //1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null){
            throw new MyApiException("인증되지 않았습니다.");
        }

        //2. 핵심로직
        replyService.댓글삭제(id, sessionUser.getId());

        //3. 응답
        return new ApiUtil<String>(true, "댓글삭제 완료");
    }

    // @PostMapping("/reply/save")
    // public String save(ReplyRequest.SaveDTO saveDTO,Integer id) {
    //     User sessionUser = (User) session.getAttribute("sessionUser");
    //     // System.out.println("sessionuser : " + user.getUsername());
    //     replyService.댓글쓰기(saveDTO,sessionUser.getId());
    //     return "redirect:/board/"+saveDTO.getBoardId();
    // }

    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save(@RequestBody  ReplyRequest.SaveDTO saveDTO){
        // System.out.println("board: "+saveDTO.getBoardId());
        // System.out.println("comment: "+saveDTO.getComment());
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            // return new ApiUtil<String>(false, "인증이 되지 않았습니다.");
            // 서비스쪽에서 오류발생하면 어째 그냥 쓰로우로 다 넘겨버리는게 나아.
            throw new MyApiException("인증 되지 않았습니다.");
        }
        replyService.댓글쓰기(saveDTO,sessionUser.getId());
        return new ApiUtil<String>(true, "댓글이 작성되었습니다.");
    }
}
