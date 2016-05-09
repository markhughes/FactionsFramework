package me.markeh.factionsframework.command.requirements;

import org.bukkit.ChatColor;

import me.markeh.factionsframework.command.FactionsCommand;

public class ReqInFaction extends Requirement {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public static ReqInFaction get(FactionsCommand command) {
		return new ReqInFaction(command);
	}
	
	private ReqInFaction(FactionsCommand command) {
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
		return ( ! this.command.getFPlayer().getFaction().isNone());
	}

	@Override
	public String getErrorMessage() {
		return ChatColor.RED + "You must be in a faction to use this command.";
	}

}
