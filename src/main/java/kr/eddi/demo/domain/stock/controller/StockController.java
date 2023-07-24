package kr.eddi.demo.domain.stock.controller;

import kr.eddi.demo.domain.stock.controller.form.response.StockNameResponseForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockOCVAResponseForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockOpinionResponseForm;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ToString
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    final private StockService stockService;

    @GetMapping("/save-data")
    public void saveStockData ( ) {
        stockService.save();
    }
    @GetMapping("/name/{ticker}")
    public StockNameResponseForm responseStockName (@PathVariable("ticker") String ticker) {
       return stockService.getStockName(ticker);
    }
    @GetMapping("/opinion-mining")
    public void requestStockOpinion (){
        stockService.saveOpinion();
    }
    @GetMapping("/save-OCVA-data")
    public void saveOHCLVAData () {
        stockService.saveOCVAData();
    }
    @GetMapping("/list/{OCVA}/{ascending}/{pageNumber}")
    public List<StockOCVAResponseForm> stockListResponse (@PathVariable("OCVA") String OCVA,
                                                          @PathVariable("ascending") String ascending,
                                                          @PathVariable("pageNumber") int pageNumber
                                                          ) {
        return stockService.list(OCVA, ascending, pageNumber);
    }
    @GetMapping("/opinion-list/{sortItem}/{ascending}/{pageNumber}")
    public List<StockOpinionResponseForm> stockOpinionListResponse (@PathVariable("sortItem") String sortItem,
                                                                    @PathVariable("ascending") String ascending,
                                                                    @PathVariable("pageNumber") int pageNumber) {
        return stockService.opinionList(sortItem, ascending, pageNumber);
    }
}
