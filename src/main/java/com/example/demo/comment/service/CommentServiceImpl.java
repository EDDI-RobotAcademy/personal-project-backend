package com.example.demo.comment.service;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
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
import org.springframework.stereotype.Service;

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
}
