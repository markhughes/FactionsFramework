package me.markeh.factionsframework.layer.layer_2_8_7;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;

public class FPlayers_2_8_7 extends FPlayers {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private HashMap<String, FPlayer> fplayersMap = new HashMap<String, FPlayer>();
	
	// -------------------------------------------------- //
	// METHODS 
	// -------------------------------------------------- //
	
	@Override
	public FPlayer get(CommandSender sender) {
		UUID uuid = null;
		
		if (sender instanceof Player) {
			uuid = ((Player)sender).getUniqueId();
		} else {
			uuid = null;
		}
		
		return this.get(uuid);
	}
	
	@Override
	public FPlayer get(UUID uuid) {
		String id;
		if (uuid == null) {
			id = "@console";
		} else {
			id = uuid.toString();
		}
		
		if ( ! this.fplayersMap.containsKey(id)) {
			FPlayer_2_8_7 fplayer = new FPlayer_2_8_7(id);
			this.fplayersMap.put(id, fplayer);
		}
		
		return this.fplayersMap.get(id);
	}
	
	@Override
	public FPlayer get(String name) {
		return this.get(Bukkit.getPlayer(name).getUniqueId());
	}
	
}
