package kr.eddi.demo.initializer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kr.eddi.demo.domain.stock.service.StockService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockInitializer {

    @Bean
    public CommandLineRunner onStartUp(StockService stockService) {
        return args -> {
            ExecutorService executorService = Executors.newFixedThreadPool(3);

            executorService.submit(stockService::save);
            executorService.submit(stockService::saveOpinion);
            executorService.submit(stockService::saveOCVAData);

            executorService.shutdown();
        };
    }
}
