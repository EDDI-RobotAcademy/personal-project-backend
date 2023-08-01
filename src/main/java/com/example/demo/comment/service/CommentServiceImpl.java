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
import com.example.demo.redis.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final private MemberRepository memberRepository;
    final private MemberBoardRepository boardRepository;
    final private CommentRepository commentRepository;
    final private MemberBoardService memberBoardService;
    final private RedisService redisService;

    @Override
    @Transactional
    public BoardResForm register(RequestRegisterCommentForm requestCommentForm, Long boardId) {
        Long memberId = redisService.getValueByKey(requestCommentForm.getUserToken());
        Optional<Member> isMember = memberRepository.findById(memberId);
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member savedMember = isMember.get();
        Optional<MemberBoard> isBoard = boardRepository.findById(boardId);
        if (isBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        MemberBoard savedBoard = isBoard.get();
        Comment newComment = requestCommentForm.toComment(savedBoard, savedMember);
        commentRepository.save(newComment);

        return memberBoardService.read(boardId);
    }

    @Override
    @Transactional
    public CommentResForm modify(RequestRegisterCommentForm requestCommentForm, Long commentId) {
        Long memberId = redisService.getValueByKey(requestCommentForm.getUserToken());
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        if (maybeMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member savedMember = maybeMember.get();
        Optional<Comment> maybeComment = commentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        Comment finedComment = maybeComment.get();
        if (savedMember.getId().equals(finedComment.getMember().getId())) {
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
        return null;
    }

    @Override
    @Transactional
    public Boolean delete(Long commentId, HttpHeaders headers) {
        Optional<Comment> maybeComment = commentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            return false;
        }
        Comment finedComment = maybeComment.get();
        Long memberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        if (finedComment.getMember().getId().equals(memberId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public List<CommentResForm> commentlistWithMember(HttpHeaders headers, Integer page) {
        log.info(Objects.requireNonNull(headers.get("authorization")).get(0));
        Long memberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        log.info(String.valueOf(memberId));
        Optional<Member> isMember = memberRepository.findById(memberId);
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member findMember = isMember.get();
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("commentId").descending());
        List<Comment> commentList = commentRepository.findAllByMemberId(findMember.getId(), pageable);
//        List<Long> idList = maybeBoard.get().getFilePathList().stream().map(FilePaths::getFileId).toList();
//        List<Long> idList = commentList.stream().map(Comment::getMemberBoard).map(MemberBoard::getBoardId).toList();
        List<CommentResForm> CommentResFormList = commentList.stream().map((cl) -> CommentResForm
                .builder()
                .commentId(cl.getCommentId())
                .text(cl.getText())
                .createdDate(cl.getCreatedDate())
                .member(cl.getMember())
                .memberBoard(new MemberBoard(cl.getMemberBoard().getBoardId(), cl.getMemberBoard().getTitle()))
                .build()).toList();
        return CommentResFormList;
    }

    @Override
    @Transactional
    public Integer getMyCommentTotalPage(HttpHeaders headers) {
        Long memberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        Optional<Member> isMember = memberRepository.findById(memberId);
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member findMember = isMember.get();
        Integer totalComment = (int) commentRepository.findById(findMember.getId()).stream().count();
        log.info(String.valueOf(totalComment));
        Integer size = 10;
        if (totalComment % size == 0) {
            return totalComment / size;
        } else {
            return totalComment / size + 1;
        }
    }

}
