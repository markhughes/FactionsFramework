package me.markeh.factionsframework.layers.commands;

import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.util.TL;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;

public class Command_2_6 extends FCommand implements CommandBase {

	// -------------------------------------------------- //
	// CONSTRUCT 
	// -------------------------------------------------- //
	
	public Command_2_6(FactionsCommand command) {
		this.command = command;
		
		this.aliases.addAll(command.getAliases());
		this.requiredArgs.addAll(command.getRequiredArguments());
		this.optionalArgs.putAll(command.getOptionalArguments());
		this.setHelpShort(this.command.getDescription());
		
		this.errorOnToManyArgs = ! command.overflowAllowed();
		
		this.permission = command.getPermission();
	}

	// -------------------------------------------------- //
	// FIELDS 
	// -------------------------------------------------- //
	
	private FactionsCommand command;

	// -------------------------------------------------- //
	// METHODS 
	// -------------------------------------------------- //
	
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
			FactionsFramework.get().logError(e);
		}		
	}
	
	// FactionsUUID specific, is required for compiling with 1.6 - but useless
	public TL getUsageTranslation() {
		return null;
	}

}
