package kr.eddi.demo.comment.controller.form;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentRegisterForm {

  final private String content;

  final private String author;

  final private Long boardId;
}
