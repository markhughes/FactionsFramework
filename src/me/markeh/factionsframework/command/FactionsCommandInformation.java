package me.markeh.factionsframework.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.entities.FPlayer;

public class FactionsCommandInformation {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private FPlayer fplayer;
	private CommandSender sender;
	private List<String> args;
	
	// -------------------------------------------------- //
	// METHODS 
	// -------------------------------------------------- //
	
	// Getters
	public FPlayer getFPlayer() {
		return this.fplayer;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	
	public List<String> getArgs() {
		return this.args;
	}
	
	// Setters
	public void setFPlayer(FPlayer fplayer) {
		this.fplayer = fplayer;
	}
	
	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	
	public void setArgs(List<String> args) {
		this.args = args;
	}
	
}
