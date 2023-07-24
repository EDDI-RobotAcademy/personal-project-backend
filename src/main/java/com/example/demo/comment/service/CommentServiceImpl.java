package com.example.demo.comment.service;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.CommentResForm;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import com.example.demo.board.service.MemberBoardService;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.form.RequestRegisterCommentForm;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    final private MemberRepository memberRepository;
    final private MemberBoardRepository boardRepository;
    final private CommentRepository commentRepository;
    final private MemberBoardService memberBoardService;

    @Override
    @Transactional
    public BoardResForm register(RequestRegisterCommentForm requestCommentForm, Long boardId) {
        Optional<Member> isMember = memberRepository.findByUserToken(requestCommentForm.getUserToken());
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member savedMember= isMember.get();
        Optional<MemberBoard> isBoard = boardRepository.findById(boardId);
        if (isBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        MemberBoard savedBoard = isBoard.get();
        Comment newComment=requestCommentForm.toComment(savedBoard, savedMember);
        commentRepository.save(newComment);

        return memberBoardService.read(boardId);
    }

    @Override
    @Transactional
    public CommentResForm modify(RequestRegisterCommentForm requestCommentForm, Long commentId) {
        Optional<Member> maybeMember = memberRepository.findByUserToken(requestCommentForm.getUserToken());
        if (maybeMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member savedMember= maybeMember.get();
        Optional<Comment> maybeComment = commentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        Comment finedComment = maybeComment.get();
        finedComment.setText(requestCommentForm.getText());
        CommentResForm comment = CommentResForm
                .builder()
                .text(finedComment.getText())
                .createdDate(finedComment.getCreatedDate())
                .modifiedDate(finedComment.getModifiedDate())
                .member(finedComment.getMember())
                .build();
        return comment;
    }

    @Override
    @Transactional
    public Boolean delete(Long commentId, HttpHeaders headers) {
        Optional<Comment> maybeComment = commentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            return false;
        }
        Comment finedComment = maybeComment.get();
        if (finedComment.getMember().getUserToken().equals(Objects.requireNonNull(headers.get("authorization")).get(0))) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }
}
