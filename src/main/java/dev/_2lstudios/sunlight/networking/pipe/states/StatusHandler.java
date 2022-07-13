package dev._2lstudios.sunlight.networking.pipe.states;

import dev._2lstudios.sunlight.networking.connection.Connection;
import dev._2lstudios.sunlight.networking.connection.ConnectionState;
import dev._2lstudios.sunlight.networking.packets.Packet;
import dev._2lstudios.sunlight.networking.packets.server.status.S0StatusResponse;
import dev._2lstudios.sunlight.networking.packets.server.status.S1PingResponse;
import dev._2lstudios.sunlight.networking.pipe.Pipe;

public class StatusHandler implements Pipe {
    @Override
    public boolean handle(Connection connection, Packet packet) throws Exception {
        if (connection.isClosed()) {
            // Don't handle in any handler.
            return false;
        } else if (connection.getState() != ConnectionState.STATUS) {
            // Don't handle in this handler.
            return true;
        }

        if (packet.getPacketID() == 0) {
            S0StatusResponse response = new S0StatusResponse()
                .setOnlinePlayers(0)
                .setMaxPlayers(12)
                .setMotd("uwu")
                .setVersionName("testing")
                .setVersionProtocol(connection.getProtocol());
            connection.sendPacket(response);
        }

        if (packet.getPacketID() == 1) {
            long payload = packet.readLong();
            System.out.println("payload " + payload);
            connection.sendPacket(new S1PingResponse(payload));
            connection.close();
        }

        return false;
    }
}
