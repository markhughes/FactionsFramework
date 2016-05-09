package me.markeh.factionsframework.command.requirements;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.markeh.factionsframework.command.FactionsCommand;

public class ReqIsPlayer extends Requirement {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public static ReqIsPlayer get(FactionsCommand command) {
		return new ReqIsPlayer(command);
	}
	
	private ReqIsPlayer(FactionsCommand command) {
		this.command = command;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private FactionsCommand command;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public Boolean isMet() {
		return (command.getSender() instanceof Player);
	}

	@Override
	public String getErrorMessage() {
		return ChatColor.RED + "You must be a player to run this command.";
	}

}
