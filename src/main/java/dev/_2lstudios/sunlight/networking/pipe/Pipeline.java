package dev._2lstudios.sunlight.networking.pipe;

import java.util.ArrayList;
import java.util.List;

import dev._2lstudios.sunlight.networking.connection.Connection;
import dev._2lstudios.sunlight.networking.packets.Packet;

public class Pipeline {
    private List<Pipe> handlers;
    
    public Pipeline() {
        this.handlers = new ArrayList<>();
    }

    public Pipeline addFirst(Pipe handler) {
        this.handlers.add(0, handler);
        return this;
    }

    public Pipeline addLast(Pipe handler) {
        this.handlers.add(handler);
        return this;
    }

    public Pipe getHandlerByClass(Class<?> clazz) {
        for (Pipe handler : this.handlers) {
            if (handler.getClass() == clazz) {
                return handler;
            }
        }
        return null;
    }

    public int getHandlerIndexByClass(Class<?> clazz) {
        int index = 0;
        for (Pipe handler : this.handlers) {
            if (handler.getClass() == clazz) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }

    public Pipe getHandlerByIndex(int index) {
        return this.handlers.get(index);
    }

    public Pipeline replace(Class<?> clazz, Pipe handler) {
        int index = this.getHandlerIndexByClass(clazz);
        if (index != -1) {
            this.handlers.set(index, handler);
        }
        return this;
    }

    public Pipeline remove(Pipe handler) {
        this.handlers.remove(handler);
        return this;
    }

    public int size() {
        return this.handlers.size();
    }
    
    public void handle(Connection connection, Packet packet) throws Exception {
        for (Pipe handler : this.handlers) {
            if (!handler.handle(connection, packet)) {
                return;
            }
        }

        throw new Exception("All decoders have been called but the packet has not been read correctly.");
    }
}
