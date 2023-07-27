package kr.eddi.demo.comment.service;

import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.repository.AccountRepository;
import kr.eddi.demo.board.entity.Board;
import kr.eddi.demo.board.repository.BoardRepository;
import kr.eddi.demo.board.service.BoardService;
import kr.eddi.demo.comment.controller.form.CommentListRequestForm;
import kr.eddi.demo.comment.controller.form.CommentRegisterForm;
import kr.eddi.demo.comment.entity.Comment;
import kr.eddi.demo.comment.entity.ReportedComment;
import kr.eddi.demo.comment.repository.CommentRepository;
import kr.eddi.demo.comment.repository.ReportedCommentRepository;
import kr.eddi.demo.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ReportedCommentRepository reportedCommentRepository;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Boolean register(CommentRegisterForm form) {

        Long id = redisService.getValueByKey(form.getAuthor());
        Optional<Board> maybeBoard = boardRepository.findById(form.getBoardId());
        if (maybeBoard.isEmpty()){
            return false;
        }
        Optional<Account> maybeAccount= accountRepository.findById(id);
        if (maybeAccount.isEmpty()){
            return false;
        }
        Comment newComment = new Comment(form.getContent(), maybeAccount.get().getNickname(),maybeBoard.get());
        commentRepository.save(newComment);
        return true;
    }

    @Override
    public List<Comment> list(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);


        return comments;
    }

    @Override
    public void delete(Long id) {
        List<ReportedComment> reportedComments = reportedCommentRepository.findByCommentId(id);

        // Delete all reported comments for the comment
        for (ReportedComment reportedComment : reportedComments) {
            reportedCommentRepository.deleteById(reportedComment.getId());
        }
        commentRepository.deleteById(id);
    }

    @Override
    public void reportComment(Long commentId) {
        Optional<Comment> maybeComment = commentRepository.findById(commentId);
        if (maybeComment.isEmpty()) {
            throw new IllegalArgumentException("Comment not found with id: " + commentId);
        }

        Comment comment = maybeComment.get();
        ReportedComment reportedComment = new ReportedComment();
        reportedComment.setComment(comment);
        reportedCommentRepository.save(reportedComment);

        log.info("Comment with id {}", commentId);
    }

    @Override
    public List<Comment> getAllReportedComments() {
        List<ReportedComment> ReportedList =reportedCommentRepository.findAll();
        // 신고된 댓글 리스트 이다
        List<Comment> ReportedCommentList =new ArrayList<>();
        for (ReportedComment comments:ReportedList) {
            Comment comment=comments.getComment();
            ReportedCommentList.add(comment);
        }
        return ReportedCommentList;
    }

    @Override
    public void deleteReported(Long id) {
       Optional<ReportedComment> maybeReport = reportedCommentRepository.findReportByCommentId(id);
        maybeReport.ifPresent(reportedComment -> reportedCommentRepository.deleteById(reportedComment.getId()));
    }
}
