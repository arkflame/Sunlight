package dev._2lstudios.sunlight.networking;

import java.io.IOException;

import dev._2lstudios.sunlight.networking.connection.Connection;
import dev._2lstudios.sunlight.networking.packets.Packet;
import dev._2lstudios.sunlight.networking.packets.ReceivedPacket;
import dev._2lstudios.sunlight.networking.pipe.Pipeline;
import dev._2lstudios.sunlight.networking.pipe.decoders.PacketIdentifier;
import dev._2lstudios.sunlight.networking.pipe.states.HandshakeHandler;
import dev._2lstudios.sunlight.networking.pipe.states.StatusHandler;
import io.activej.csp.ChannelConsumer;
import io.activej.csp.ChannelSupplier;
import io.activej.csp.binary.BinaryChannelSupplier;
import io.activej.csp.binary.ByteBufsDecoder;
import io.activej.eventloop.Eventloop;
import io.activej.net.SimpleServer;

public class AbstractServer {
    private Eventloop eventloop;
    private Pipeline pipeline;
    private SimpleServer server;

    public AbstractServer() {
        this.eventloop = Eventloop.create().withCurrentThread();   
        this.pipeline = new Pipeline();

        this.pipeline.addLast(new PacketIdentifier());
        this.pipeline.addLast(new HandshakeHandler());
        this.pipeline.addLast(new StatusHandler());
    }

    public void listen(int port) throws IOException {
        this.server = SimpleServer.create(socket -> {
            Connection connection = new Connection(socket);
            BinaryChannelSupplier.of(ChannelSupplier.ofSocket(socket))
                .decodeStream(ByteBufsDecoder.ofVarIntSizePrefixedBytes(10000))
                .peek(buf -> {
                    Packet packet = new ReceivedPacket(buf);
                    try {  
                        pipeline.handle(connection, packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .streamTo(ChannelConsumer.ofConsumer(buf -> System.out.println("Received packet")))
                .whenComplete(() -> System.out.println("Client disconnected"));
        }).withListenPort(port);

        this.server.listen();
        this.eventloop.run();
    }

    public void stop() {
        if (this.server != null && this.server.isRunning()) {
            this.server.close();
        }

        this.eventloop.breakEventloop();
    }
}
