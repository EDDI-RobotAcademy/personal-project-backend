package kr.eddi.demo.domain.board.repository;

import kr.eddi.demo.domain.board.entity.StockBoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockBoardListRepository extends JpaRepository<StockBoardList, Long> {
    @Query("SELECT sbl FROM StockBoardList sbl JOIN FETCH sbl.boards WHERE sbl.stock.ticker = :ticker")
    Optional<StockBoardList> findByStockTicker(@Param("ticker") String ticker);
}
