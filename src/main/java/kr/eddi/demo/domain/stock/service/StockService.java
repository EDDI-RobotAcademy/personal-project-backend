package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.domain.stock.controller.form.response.StockNameResponseForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockOCVAResponseForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockOpinionResponseForm;
import kr.eddi.demo.domain.stock.entity.Stock;

import java.util.List;

public interface StockService {
    void save();

    List<Stock> getStockList();

    StockNameResponseForm getStockName(String ticker);

    void getOpinionTest();

    void getOCVAData();

    List<StockOCVAResponseForm> list(String OCVA, String ascending, int pageNumber);

    List<StockOpinionResponseForm> opinionList(String sortItem, String ascending, int pageNumber);
}
