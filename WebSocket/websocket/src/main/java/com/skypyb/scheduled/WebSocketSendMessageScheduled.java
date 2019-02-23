package com.skypyb.scheduled;

import com.skypyb.websocket.WebSocketServerOne;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class WebSocketSendMessageScheduled {

    @Scheduled(fixedRate = 3 * 1000)
    public void publish() {
        WebSocketServerOne.fanoutMessage(LocalDateTime.now().toString());
    }

}
