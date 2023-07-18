package kr.eddi.demo.domain.chat.service;

import jakarta.websocket.Session;
import kr.eddi.demo.domain.chat.entity.Message;
import kr.eddi.demo.domain.chat.repository.MessageRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

        final static private Map<String, Set<Session>> ROOMS = Collections.synchronizedMap(new HashMap<>());
        final private StockRepository stockRepository;
        final private MessageRepository messageRepository;

        @Override
        public void onOpen(Session session, String ticker) throws IOException {
            Set<Session> clients = ROOMS.computeIfAbsent(ticker, key -> Collections.synchronizedSet(new HashSet<>()));
            clients.add(session);

            for (Session client : clients) {
                client.getBasicRemote().sendText(session.getId() + "님이 입장했습니다");
            }
        }

        @Override
        public void onClose(Session session, String ticker) throws IOException {
            Set<Session> clients = ROOMS.get(ticker);
            if (clients != null) {
                clients.remove(session);
                for (Session client : clients) {
                    client.getBasicRemote().sendText(session.getId() + "님이 나갔습니다");
                }
            }
        }

        @Override
        public void onMessage(String content, Session session, String ticker) throws IOException {
            Set<Session> clients = ROOMS.get(ticker);
            if (clients != null) {
                Message message = new Message();
                message.setContent(content);
                Stock stock = stockRepository.findByTicker(ticker).get();
                message.setStock(stock);
                messageRepository.save(message);
                for (Session client : clients) {
                    client.getBasicRemote().sendText(session.getId() + ": " + content);
                }
            }
        }
}
