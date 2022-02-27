package org.ricoware.wurmunlimited.mods.chirpinfo;

import org.gotti.wurmunlimited.modloader.interfaces.PlayerLoginListener;
import org.gotti.wurmunlimited.modloader.interfaces.PlayerMessageListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.Server;
import com.wurmonline.server.Servers;

public class chirpinfo implements WurmServerMod, PlayerMessageListener, PlayerLoginListener {

	@Override
	public boolean onPlayerMessage(Communicator communicator, String message) {
		if(message != null) {
			if (message.startsWith("/chirp") || message.startsWith("/nextchirp") || message.equals("/nextcrop")) {
				communicator.sendNormalServerMessage(getNextChirpTime());
				return true;
			}
		}
		return false;
	}

	@Override
	public void onPlayerLogin(Player player) {
		player.getCommunicator().sendSafeServerMessage(getNextChirpTime());
		player.getCommunicator().sendSafeServerMessage("  Type /nextchirp or /nextcrop for update.");
	}
	
	public static String getNextChirpTime() {
		long fieldGrowthTime = Servers.localServer.getFieldGrowthTime();		
		long currentTime = System.currentTimeMillis();
		long startTime = Server.getStartTime();
		long chirpTime = startTime + fieldGrowthTime;
		
		while(chirpTime < currentTime) {
			chirpTime += fieldGrowthTime;
		}

		long nextChirp = chirpTime - currentTime;
		
		return "Crops will need tending in about " + Server.getTimeFor(nextChirp) + ".";			
	}
}
