package dev._2lstudios.sunlight.networking.packets.server.status;

import com.google.gson.JsonObject;

import dev._2lstudios.sunlight.networking.packets.Packet;

public class S0StatusResponse extends Packet {
    private String versionName; // version.name
    private int versionProtocol; // version.protocol

    private int maxPlayers; // players.max
    private int onlinePlayers; // players.online

    private String motd; // description.text

    private String favicon; // favicon
    private boolean previewsChat; // previewsChat

    public S0StatusResponse setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public S0StatusResponse setVersionProtocol(int protocol) {
        this.versionProtocol = protocol;
        return this;
    }

    public S0StatusResponse setMaxPlayers(int players) {
        this.maxPlayers = players;
        return this;
    }

    public S0StatusResponse setOnlinePlayers(int players) {
        this.onlinePlayers = players;
        return this;
    }

    public S0StatusResponse setMotd(String motd) {
        this.motd = motd;
        return this;
    }

    public S0StatusResponse setFavicon(String favicon) {
        this.favicon = favicon;
        return this;
    }

    public S0StatusResponse setPreviewsChat(boolean previewsChat) {
        this.previewsChat = previewsChat;
        return this;
    }

    // Packet methods
    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public void onBuild() {
        JsonObject packet = new JsonObject();

        JsonObject versionObject = new JsonObject();
        versionObject.addProperty("name", this.versionName);
        versionObject.addProperty("protocol", this.versionProtocol);
        packet.add("version", versionObject);

        JsonObject playersObject = new JsonObject();
        playersObject.addProperty("max", this.maxPlayers);
        playersObject.addProperty("online", this.onlinePlayers);
        packet.add("players", playersObject);
        
        JsonObject descriptionObject = new JsonObject();
        descriptionObject.addProperty("text", this.motd);
        packet.add("description", descriptionObject);

        if (this.favicon != null) {
            packet.addProperty("favicon", this.favicon);
        }
        
        packet.addProperty("previewsChat", this.previewsChat);

        String raw = packet.toString();
        this.allocate(raw.length());
        this.writeString(packet.toString());
    }
    
}
