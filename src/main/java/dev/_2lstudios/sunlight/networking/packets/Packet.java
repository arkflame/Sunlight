package dev._2lstudios.sunlight.networking.packets;

import io.activej.bytebuf.ByteBuf;
import io.activej.bytebuf.ByteBufPool;

public abstract class Packet {
    private ByteBuf bytebuf;
    private int packetId;

    public Packet(ByteBuf buf) {
        this.bytebuf = buf;
    }

    public Packet(int packetId) {
        this.packetId = packetId;
    }

    public Packet() {}

    // Wrapper factory
    public abstract void onBuild();
    
    public void allocate(int packetLength) {
        int idLength = String.valueOf(this.getPacketID()).length();
        int length = packetLength + idLength;

        this.bytebuf = ByteBufPool.allocate(length + packetLength);
        this.writeVarInt(length + 1);
        this.writeVarInt(this.getPacketID());
    }

    // Wrapper getters/setters.
    public ByteBuf getBytebuf() {
        return this.bytebuf;
    }

    public int getPacketID() {
        return this.packetId;
    }

    public void setPacketID(int packetId) {
        this.packetId = packetId;
    }

    // Buffer utils.
    public long readLong() {
        return this.bytebuf.readLong();
    }

    public short readShort() {
        return this.bytebuf.readShort();
    }

    public int readVarInt() {
        return this.bytebuf.readVarInt();
    }

    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = this.bytebuf.readByte();
        }
        return bytes;
    }

    public String readString() {
        int length = this.readVarInt();
        byte[] bytes = this.readBytes(length);
        return new String(bytes);
    }

    public void writeLong(long value) {
        this.bytebuf.writeLong(value);
    }

    public Packet writeVarInt(int varint) {
        this.bytebuf.writeVarInt(varint);
        return this;
    }

    public Packet writeString(String string) {
        byte[] bytes = string.getBytes();
        this.writeVarInt(bytes.length);
        this.bytebuf.write(bytes);
        return this;
    }
}
