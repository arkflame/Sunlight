package dev._2lstudios.sunlight.networking.pipe.states;

import dev._2lstudios.sunlight.networking.connection.Connection;
import dev._2lstudios.sunlight.networking.connection.ConnectionState;
import dev._2lstudios.sunlight.networking.packets.Packet;
import dev._2lstudios.sunlight.networking.pipe.Pipe;

public class HandshakeHandler implements Pipe {
    @Override
    public boolean handle(Connection connection, Packet packet) throws Exception {
        if (connection.isClosed()) {
            // Don't handle in any handler.
            return false;
        } else if (connection.getState() != ConnectionState.HANDSHAKE) {
            // Don't handle in this handler.
            return true;
        }

        int protocol = packet.readVarInt();
        String hostname = packet.readString();
        short port = packet.readShort();
        int nextState = packet.readVarInt();

        connection.setHandshakeData(protocol, hostname, port);

        if (nextState == 1) {
            connection.setState(ConnectionState.STATUS);
        } else if (nextState == 2) {
            connection.setState(ConnectionState.LOGIN);
        }

        return false;
    }
}
