package dev._2lstudios.sunlight.networking.pipe;

import dev._2lstudios.sunlight.networking.connection.Connection;
import dev._2lstudios.sunlight.networking.packets.Packet;

public interface Pipe {
    public boolean handle(Connection connection, Packet packet) throws Exception;
}
