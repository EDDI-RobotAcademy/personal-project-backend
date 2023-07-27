package kr.eddi.demo.comment.repository;

import kr.eddi.demo.comment.entity.ReportedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedCommentRepository extends JpaRepository<ReportedComment, Long> {

}
