package kr.eddi.demo.comment.repository;

import kr.eddi.demo.comment.entity.Comment;
import kr.eddi.demo.comment.entity.ReportedComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportedCommentRepository extends JpaRepository<ReportedComment, Long> {

   

    Optional<ReportedComment> findReportByCommentId(Long id);

    List<ReportedComment> findByCommentId(Long id);
}
