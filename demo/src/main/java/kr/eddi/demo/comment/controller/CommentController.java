package kr.eddi.demo.comment.controller;

import kr.eddi.demo.comment.controller.form.CommentListRequestForm;
import kr.eddi.demo.comment.controller.form.CommentRegisterForm;
import kr.eddi.demo.comment.entity.Comment;
import kr.eddi.demo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {
    @Autowired
    final private CommentService commentService;

    @PostMapping(value = "/list/{boardId}", consumes = "application/json")
    public List<Comment> comments(@PathVariable("boardId") Long boardId) {

        return commentService.list(boardId);
    }

    @PostMapping("/register")
    public Boolean CommentRegister(@RequestBody CommentRegisterForm form){


       return commentService.register(form);
   }
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id){
        commentService.delete(id);
    }
}
