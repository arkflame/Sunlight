package dev._2lstudios.sunlight.networking.packets;

import io.activej.bytebuf.ByteBuf;

public class ReceivedPacket extends Packet {
    public ReceivedPacket(ByteBuf buf) {
        super(buf);
    }

    @Override
    public void onBuild() { }
}
