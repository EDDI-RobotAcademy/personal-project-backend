package kr.eddi.demo.marker.repository;

import kr.eddi.demo.marker.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
