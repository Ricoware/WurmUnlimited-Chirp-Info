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
		if(message != null &&  (message.startsWith("/nextchirp")) || message.startsWith("/chirp") ) {
			communicator.sendNormalServerMessage("Next farming chirp in " + getNextChirpTime() + ".");
			return true;
		}
		return false;
	}

	@Override
	public void onPlayerLogin(Player player) {
		player.getCommunicator().sendSafeServerMessage("Next farming chirp in " + getNextChirpTime() + ".");
		player.getCommunicator().sendSafeServerMessage("  Type /nextchirp or /chirp for updates.");
	}
	
	public static String getNextChirpTime() {
		String time = "about ";

		long fieldGrowthTime = Servers.localServer.getFieldGrowthTime();		
		long currentTime = System.currentTimeMillis();
		long startTime = Server.getStartTime();
		long chirpTime = startTime + fieldGrowthTime;
		
		while(chirpTime < currentTime) {
			chirpTime += fieldGrowthTime;
		}

		long nextChirp = chirpTime - currentTime;

		if(nextChirp > 0) {
			time += Server.getTimeFor(nextChirp);
		}
		else {
			time += "ERROR 0";
		}
		
		return time;
	}
}
