package net.dengzixu.bilvetools.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import net.dengzixu.bilvetools.constant.Constant;
import net.dengzixu.blivedanmaku.Packet;
import net.dengzixu.blivedanmaku.PacketResolver;
import net.dengzixu.blivedanmaku.Resolver;
import net.dengzixu.blivedanmaku.enums.MessageEnum;
import net.dengzixu.blivedanmaku.enums.Operation;
import net.dengzixu.blivedanmaku.message.Message;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/message")
public class WebsocketServer implements net.dengzixu.blivedanmaku.handler.Handler {
    // Logger
    private static final org.slf4j.Logger Logger = org.slf4j.LoggerFactory.getLogger(WebsocketServer.class);

    // WebSocket Session
    private Session session;

    private static final CopyOnWriteArraySet<WebsocketServer> webSocketServers = new CopyOnWriteArraySet<>();

    // Websocket onOpen
    @OnOpen
    public void onOpen(Session session) {
        Logger.info("[Websocket Server (Message)] ID: {} 开始链接", session.getId());

        webSocketServers.add(this);
        this.session = session;

        Constant.bLiveDanmakuClient.addHandler(this);
    }

    // Websocket onClose
    @OnClose
    public void onClose(Session session) {
        Logger.info("[Websocket Server (Message)] ID: {} 断开链接", session.getId());

        webSocketServers.remove(this);

        Constant.bLiveDanmakuClient.removeHandler(this);
    }

    // Send Message
    synchronized private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            Logger.error("[Websocket Server (Message)] ID: {} 推送消息发生错误", this.session.getId(), e);
        }
    }

    @Override
    public void doHandler(byte[] bytes) {
        PacketResolver packetResolver = new PacketResolver(bytes);

        List<Packet> packets = packetResolver.resolve();

        packets.forEach(packet -> {

            if (packet.operation() == Operation.MESSAGE) {
                Message<?> message = new Resolver(new String(packet.body(), StandardCharsets.UTF_8)).resolve();

                String stringMessage = null;
                try {
                    stringMessage = new ObjectMapper()
                            .registerModule(new Jdk8Module())
                            .writeValueAsString(message);
                } catch (JsonProcessingException e) {
                    Logger.error("消息解析失败", e);
                }

                if (!message.messageEnum().equals(MessageEnum.UNKNOWN) &&
                        !message.messageEnum().equals(MessageEnum.IGNORE)) {
                    this.sendMessage(stringMessage);
                }
            }
        });
    }
}
