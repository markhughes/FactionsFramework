package me.markeh.factionsframework.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.command.requirements.Requirement;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;

public abstract class FactionsCommand extends Messenger {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private FPlayer fplayer;
	private CommandSender sender;
	private List<String> args = new ArrayList<String>();
	private List<String> aliases = new ArrayList<String>();
	private List<String> requiredArguments = new ArrayList<String>();
	private HashMap<String, String> optionalArguments = new HashMap<String, String>();
	private Boolean allowOverflow = false;
	private String description = "";
	private String permission = null;
	private List<Requirement> requirements = new ArrayList<Requirement>();
	
	// -------------------------------------------------- //
	// METHODS 
	// -------------------------------------------------- //
	
	/**
	 * Returns the FPlayer object for the sender executing the command. 
	 *
	 * @return      the FPlayer object for the sender
	 * @see         FPlayer
	 */
	public final FPlayer getFPlayer() {
		return this.fplayer;
	}
	
	/**
	 * Returns the CommandSender object for the sender executing the command. 
	 *
	 * @return      the CommandSender object for the sender
	 * @see         CommandSender
	 */
	public final CommandSender getSender() {
		return this.sender;
	}
	
	/**
	 * Fetches the aliases for this command. 
	 *
	 * @return      a list of aliases
	 */
	public final List<String> getAliases() {
		return this.aliases;
	}
	
	public final void addAlias(String alias) {
		this.aliases.add(alias);
	}
	
	/**
	 * Fetches the required arguments for this command. 
	 *
	 * @return      a list of arguments
	 */
	public final List<String> getRequiredArguments() {
		return this.requiredArguments;
	}
	
	/**
	 * Fetches the optional arguments for this command. 
	 *
	 * @return      a hashmap of arguments
	 */
	public final HashMap<String, String> getOptionalArguments() {
		return this.optionalArguments;
	}
	
	/**
	 * Adds an optional argument, along with a default value. The name
	 * will show in the help. 
	 */
	public final void addOptionalArgument(String name, String defaultValue) {
		this.optionalArguments.put(name, defaultValue);
	}
	
	/**
	 * Adds a required argument. The name will show in the help. 
	 */
	public final void addRequiredArgument(String name) {
		this.requiredArguments.add(name);
	}
	
	public final void allowOverflow(Boolean allow) {
		this.allowOverflow = allow;
	}
	
	public final Boolean overflowAllowed() {
		return this.allowOverflow;
	}
	
	public final void setDescription(String desc) {
		this.description = desc;
	}
	
	public final String getDescription() {
		return this.description;
	}
	
	public final void setPermission(String perm) {
		this.permission = perm;
	}
	
	public final String getPermission() {
		return this.permission;
	}
	
	public final void addRequirement(Requirement req) {
		this.requirements.add(req);
	}
	
	public final List<Requirement> getRequirements() {
		return this.requirements;
	}
	
	public final void executeWith(FactionsCommandInformation info) throws Exception {
		this.fplayer = info.getFPlayer();
		this.sender = info.getSender();
		this.args = info.getArgs();
		
		for (Requirement req : this.getRequirements()) {
			if (req.isMet()) continue;
			if (req.getErrorMessage() != null) msg(req.getErrorMessage());
			return;
		}
		
		this.run();
	}
	
	public final String getArg(int index) {
		if (this.args.size() < index+1) return null;
		
		return this.args.get(index);
	}
	
	public final Faction getArgAs(int index, Class<? extends Faction> type, Faction defaultValue) {
		Faction value = Factions.getById(this.getArg(index));
		
		if (value == null) return defaultValue;
		
		return value;
	}
	
	public final FPlayer getArgAs(int index, Class<? extends FPlayer> type, FPlayer defaultValue) {
		FPlayer value = FPlayers.getById(this.getArg(index));
		
		if (value == null) return defaultValue;
		
		return value;
	}
	
	public final void msg(String msg) {
		this.fplayer.msg(msg);
	}
		
	// -------------------------------------------------- //
	// ABSTRACT METHODS 
	// -------------------------------------------------- //
	
	public abstract void run() throws Exception;
	
}
