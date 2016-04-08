package me.markeh.factionsframework.entities;

import java.util.UUID;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.FactionsFramework;

public abstract class FPlayers {

	// -------------------------------------------------- //
	// STATIC GETTERS  
	// -------------------------------------------------- //
	
	public static FPlayer getById(String id) {
		return FactionsFramework.get().getFPlayers().get(UUID.fromString(id));
	}
	
	public static FPlayer getBySender(CommandSender sender) {
		return FactionsFramework.get().getFPlayers().get(sender);
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS  
	// -------------------------------------------------- //
	
	public abstract FPlayer get(CommandSender sender);
	public abstract FPlayer get(UUID uuid);
	public abstract FPlayer get(String name);

}
