package dev._2lstudios.sunlight;

import dev._2lstudios.sunlight.networking.AbstractServer;

public final class SunlightLauncher {
	public static void main(String[] args) throws Exception {
		AbstractServer server = new AbstractServer();
		server.listen(25565);
	}
}