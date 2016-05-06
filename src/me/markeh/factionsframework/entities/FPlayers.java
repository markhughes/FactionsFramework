package me.markeh.factionsframework.entities;

import java.util.UUID;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.enums.FactionsVersion;
import me.markeh.factionsframework.layer.layer_1_6.FPlayers_1_6;
import me.markeh.factionsframework.layer.layer_2_6.FPlayers_2_6;
import me.markeh.factionsframework.layer.layer_2_8_6.FPlayers_2_8_6;
import me.markeh.factionsframework.layer.layer_2_8_7.FPlayers_2_8_7;

public abstract class FPlayers implements Handler {

	// -------------------------------------------------- //
	// STATIC GETTERS  
	// -------------------------------------------------- //
	
	public static FPlayer getById(String id) {
		return ((FPlayers) getHandler()).get(UUID.fromString(id));
	}
	
	public static FPlayer getBySender(CommandSender sender) {
		return ((FPlayers) getHandler()).get(sender);
	}
	
	private static FPlayers fplayersInstance;
	public static Handler getHandler() {
		if (fplayersInstance == null) {
			switch (FactionsVersion.get()) {
				case Factions_1_6 :
					fplayersInstance = new FPlayers_1_6();
					break;
				case Factions_2_6 :
					fplayersInstance = new FPlayers_2_6();
					break;
				case Factions_2_8_2 :
				case Factions_2_8_6 :
					fplayersInstance = new FPlayers_2_8_6();
					break;
				case Factions_2_8_7 :
					fplayersInstance = new FPlayers_2_8_7();
					break;
				default :
					fplayersInstance = null;
					break;
			}
		}
		
		return fplayersInstance;
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS  
	// -------------------------------------------------- //
	
	public abstract FPlayer get(CommandSender sender);
	public abstract FPlayer get(UUID uuid);
	public abstract FPlayer get(String name);

}
