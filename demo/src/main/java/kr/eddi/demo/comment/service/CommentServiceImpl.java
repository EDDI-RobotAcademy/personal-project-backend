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
    public List<ReportedComment> getAllReportedComments() {
        return reportedCommentRepository.findAll();
    }
}
