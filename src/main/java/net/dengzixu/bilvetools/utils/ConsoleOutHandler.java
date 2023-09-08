package net.dengzixu.bilvetools.utils;

import net.dengzixu.bilvetools.constant.Constant;
import net.dengzixu.blivedanmaku.Packet;
import net.dengzixu.blivedanmaku.PacketResolver;
import net.dengzixu.blivedanmaku.Resolver;
import net.dengzixu.blivedanmaku.enums.MessageEnum;
import net.dengzixu.blivedanmaku.enums.Operation;
import net.dengzixu.blivedanmaku.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConsoleOutHandler  implements net.dengzixu.blivedanmaku.handler.Handler {
    // Logger
    private static final org.slf4j.Logger Logger = org.slf4j.LoggerFactory.getLogger(ConsoleOutHandler.class);

    @Override
    public void doHandler(byte[] bytes) {
        PacketResolver packetResolver = new PacketResolver(bytes);

        List<Packet> packets = packetResolver.resolve();

        packets.forEach(packet -> {

            if (packet.operation() == Operation.MESSAGE) {
                Message<?> message = new Resolver(new String(packet.body(), StandardCharsets.UTF_8)).resolve();

                if (message.messageEnum().equals(MessageEnum.UNKNOWN)) {
                    Logger.warn("[直播间: {}] 收到未知消息 {}", Constant.ROOM_ID, message.rawMessage());
                } else if (!message.messageEnum().equals(MessageEnum.IGNORE)) {
                    Logger.info("[直播间: {}] {}", Constant.ROOM_ID, message.convertToString());
                }
            }
        });
    }
}
