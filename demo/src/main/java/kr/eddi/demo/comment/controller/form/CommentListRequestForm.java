package kr.eddi.demo.comment.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class CommentListRequestForm {

   final private Long boardId;
}
