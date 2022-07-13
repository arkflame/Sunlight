package dev._2lstudios.sunlight.networking.connection;

import dev._2lstudios.sunlight.networking.packets.Packet;

import io.activej.net.socket.tcp.AsyncTcpSocket;

public class Connection {
    private AsyncTcpSocket socket;
    private ConnectionState state;

    // handshake data
    private int protocol;
    private String hostname;
    private short port;

    public Connection(AsyncTcpSocket socket) {
        this.socket = socket;
        this.state = ConnectionState.HANDSHAKE;
    }

    // Connection and packet methods
    public void close() {
        this.socket.close();
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

    public void sendPacket(Packet packet) {
        packet.onBuild();
        this.socket.write(packet.getBytebuf());
    }

    // Getters and Setters
    public AsyncTcpSocket getSocket() {
        return this.socket;
    }

    public String getHostname() {
        return this.hostname;
    }

    public short getPort() {
        return this.port;
    }

    public int getProtocol() {
        return this.protocol;
    }
    
    public ConnectionState getState() {
        return this.state;
    }

    public void setHandshakeData(int protocol, String hostname, short port) {
        this.protocol = protocol;
        this.hostname = hostname;
        this.port = port;
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }
}
