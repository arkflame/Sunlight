package dev._2lstudios.sunlight.networking.packets.server.status;

import dev._2lstudios.sunlight.networking.packets.Packet;

public class S1PingResponse extends Packet {
    private long payload;
    
    public S1PingResponse(long payload) {
        this.payload = payload;
    }

    @Override
    public int getPacketID() {
        return 1;
    }

    @Override
    public void onBuild() {
        this.allocate(8);
        this.writeLong(this.payload);
    }
}
