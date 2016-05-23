package me.markeh.factionsframework.layer.layer_2_7;


import java.util.Map.Entry;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

public class Command_2_7 extends com.massivecraft.factions.cmd.FactionsCommand implements CommandBase {
	
	// ---------------------------------------- //
	// FIELDS
	// ---------------------------------------- //
	
	private FactionsCommand command;
	
	// ---------------------------------------- //
	// CONSTRUCTOR
	// ---------------------------------------- //
	
	public Command_2_7(FactionsCommand command) {
		this.command = command;
		
		this.aliases.addAll(command.getAliases());
		this.setDesc(command.getDescription());
		
		for (String arg : command.getRequiredArguments()) {
			this.addRequiredArg(arg);
		}
		
		for (Entry<String, String> arg : command.getOptionalArguments().entrySet()) {
			this.addOptionalArg(arg.getKey(), arg.getValue());
		}
		
		this.errorOnToManyArgs = ! this.command.overflowAllowed();
		
		if (command.getSubCommands().size() > 0) {
			for (FactionsCommand subCommand : command.getSubCommands()) {
				this.addSubCommand(new Command_2_7(subCommand));
			}
		}
	}
	
	// ---------------------------------------- //
	// METHODS
	// ---------------------------------------- //
	
	@Override
	public void perform() {
		// Create an information block 
		FactionsCommandInformation info = new FactionsCommandInformation();
		info.setArgs(this.args);
		info.setFPlayer(FPlayers.getBySender(this.sender));
		info.setSender(this.sender);
		
		// Execute it with that information 
		try {
			command.executeWith(info);
		} catch (Exception e) {
			FactionsFramework.get().err(e);
		}
	}
	
	@Override
	public FactionsCommand getAsFactionsCommand() {
		return this.command;
	}
	
}
