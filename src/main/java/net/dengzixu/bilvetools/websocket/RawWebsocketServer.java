package net.dengzixu.bilvetools.websocket;

import net.dengzixu.bilvetools.constant.Constant;
import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/raw")
public class RawWebsocketServer implements net.dengzixu.blivedanmaku.handler.Handler {
    // Logger
    private static final org.slf4j.Logger Logger = org.slf4j.LoggerFactory.getLogger(WebsocketServer.class);

    // WebSocket Session
    private Session session;

    private static final CopyOnWriteArraySet<RawWebsocketServer> webSocketServers = new CopyOnWriteArraySet<>();

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
        Logger.info("[Websocket Server (Raw)] ID: {} 断开链接", session.getId());

        webSocketServers.remove(this);

        Constant.bLiveDanmakuClient.removeHandler(this);
    }

    // Send Message
    synchronized private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            Logger.error("[Websocket Server (Raw)] ID: {} 推送消息发生错误", this.session.getId(), e);
        }
    }

    synchronized private void sendMessage(byte[] message) {
        try {
            this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
        } catch (IOException e) {
            Logger.error("[WS Server (Raw)] ID: {} 推送消息发生错误", this.session.getId(), e);
        }
    }

    @Override
    public void doHandler(byte[] bytes) {
        this.sendMessage(bytes);
    }
}