package kr.eddi.demo.board.repository;

import kr.eddi.demo.board.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
