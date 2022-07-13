package dev._2lstudios.sunlight.networking.pipe.decoders;

import dev._2lstudios.sunlight.networking.connection.Connection;
import dev._2lstudios.sunlight.networking.packets.Packet;
import dev._2lstudios.sunlight.networking.pipe.Pipe;

public class PacketIdentifier implements Pipe {
    @Override
    public boolean handle(Connection connection, Packet packet) throws Exception {
        if (connection.isClosed()) {
            // Don't handle in any handler.
            return false;
        }

        int packetId = packet.readVarInt();
        packet.setPacketID(packetId);
        return true;
    }
}
