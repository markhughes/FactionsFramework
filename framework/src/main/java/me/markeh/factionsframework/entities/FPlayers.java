package me.markeh.factionsframework.entities;

import java.util.UUID;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.enums.FactionsVersion;

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
	
	private static Handler fplayersInstance;
	public static Handler getHandler() {
		if (fplayersInstance == null) {
			try {
				switch (FactionsVersion.get()) {
					case Factions_1_6 :
						fplayersInstance = (FPlayers) Class.forName("me.markeh.factionsframework.layer.layer_1_6.FPlayers_1_6").newInstance();
						break;
					case Factions_2_6 :
						fplayersInstance = (FPlayers) Class.forName("me.markeh.factionsframework.layer.layer_2_6.FPlayers_2_6").newInstance();
						break;
					case Factions_2_8_2 :
					case Factions_2_8_6 :
						fplayersInstance = (FPlayers) Class.forName("me.markeh.factionsframework.layer.layer_2_8_6.FPlayers_2_8_6").newInstance();
						break;
					case Factions_2_8_7 :
						fplayersInstance = (FPlayers) Class.forName("me.markeh.factionsframework.layer.layer_2_8_7.FPlayers_2_8_7").newInstance();
						break;
					default :
						fplayersInstance = null;
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return fplayersInstance.asHandler();
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS  
	// -------------------------------------------------- //
	
	public abstract FPlayer get(CommandSender sender);
	public abstract FPlayer get(UUID uuid);
	public abstract FPlayer get(String name);
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	public Handler asHandler() {
		return (Handler) this;
	}

}
