package net.dengzixu.bilvetools.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import net.dengzixu.bilvedanmaku.enums.Message;
import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;
import net.dengzixu.bilvetools.constant.Constant;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/server")
@Component
public class WebsocketServer implements net.dengzixu.bilvedanmaku.handler.Handler {
    // LOGGER
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WebsocketServer.class);

    // WebSocket Session
    private Session session;

    private static final CopyOnWriteArraySet<WebsocketServer> webSocketServers = new CopyOnWriteArraySet<>();

    // Websocket onOpen
    @OnOpen
    public void onOpen(Session session) {
        webSocketServers.add(this);
        this.session = session;

        LOGGER.info("客户端[ID: {}] 连接成功", session.getId());

        Constant.bLiveDanmakuClient.addHandler(this);
    }

    // Websocket onClose
    @OnClose
    public void onClose() {
        webSocketServers.remove(this);
        Constant.bLiveDanmakuClient.removeHandler(this);
    }

    // Send Message
    synchronized private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            LOGGER.error("推送消息发生错误", e);
        }
    }

    @Override
    public void doHandler(SimpleMessageBody<?> simpleMessageBody) {
        String stringMessage = null;

        if (!Message._NULL.equals(simpleMessageBody.message())) {
            try {
                stringMessage = new ObjectMapper()
                        .registerModule(new Jdk8Module())
                        .writeValueAsString(simpleMessageBody);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            this.sendMessage(stringMessage);
        }
    }
}
