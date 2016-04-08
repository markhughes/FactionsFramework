package me.markeh.factionsframework.command.requirements;

import org.bukkit.ChatColor;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.enums.Rel;

public class ReqRankAtLeast extends Requirement {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public static ReqRankAtLeast get(FactionsCommand command, Rel rel) {
		return new ReqRankAtLeast(command, rel);
	}
	
	private ReqRankAtLeast(FactionsCommand command, Rel rel) {
		this.command = command;
		this.rel = rel;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private FactionsCommand command;
	private Rel rel;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public Boolean isMet() {
		return (this.command.getFPlayer().getRelationTo(this.command.getFPlayer().getFaction()).isAtLeast(this.rel));
	}

	@Override
	public String getErrorMessage() {
		return ChatColor.RED + "You must be of at least " + this.rel.getColor() + this.rel.getDescPlayerOne() + ChatColor.RED + " to use this command.";
	}

}
