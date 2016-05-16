package me.markeh.factionsframework.layer.layer_1_8;

import com.massivecraft.factions.cmd.FCommand;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

public class Command_1_8 extends FCommand implements CommandBase {

	// ---------------------------------------- //
	// FIELDS
	// ---------------------------------------- //
	
	private FactionsCommand command;
	
	// ---------------------------------------- //
	// CONSTRUCTOR
	// ---------------------------------------- //
	
	public Command_1_8(FactionsCommand command) {
		this.command = command;
		
		this.aliases.addAll(command.getAliases());
		
		this.requiredArgs.addAll(command.getOptionalArguments().keySet());
		this.optionalArgs.putAll(command.getOptionalArguments());
		
		this.permission = command.getPermission();
		
		this.errorOnToManyArgs = !command.overflowAllowed();
		
		this.setHelpShort(command.getDescription());
		
		if (this.command.getSubCommands().size() > 0) {
			for (FactionsCommand subCommand : command.getSubCommands()) {
				this.addSubCommand(new Command_1_8(subCommand));
			}
		}
	}
	
	// ---------------------------------------- //
	// METHODS
	// ---------------------------------------- //

	@Override
	public void perform() {
		// Create an information block and execute with that information
		FactionsCommandInformation info = new FactionsCommandInformation();
		info.setArgs(this.args);
		info.setFPlayer(FPlayers.getBySender(this.sender));
		info.setSender(this.sender);
		
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
