package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.PacketBuffer;

public class MessageSerializer2 extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int i = msg.readableBytes();
        int j = PacketBuffer.getVarIntSize(i);

        if (j > 3) {
            throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
        } else {
            PacketBuffer packetbuffer = new PacketBuffer(out);
            packetbuffer.ensureWritable(j + i);
            packetbuffer.writeVarIntToBuffer(i);
            packetbuffer.writeBytes(msg, msg.readerIndex(), i);
        }
    }
}
