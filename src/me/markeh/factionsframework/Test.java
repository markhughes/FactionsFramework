package me.markeh.factionsframework;

import org.bukkit.entity.Player;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;

public class Test {
	
	public void doTest(Player player) {
		FPlayer fplayer = FPlayers.getBySender(player);
		fplayer.asBukkitPlayer();
	}
}
