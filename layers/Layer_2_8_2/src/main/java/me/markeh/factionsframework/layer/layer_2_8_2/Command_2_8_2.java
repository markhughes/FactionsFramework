package me.markeh.factionsframework.layer.layer_2_8_2;

import java.lang.reflect.Method;
import java.util.Map.Entry;

import com.massivecraft.massivecore.MassiveException;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

public class Command_2_8_2 extends com.massivecraft.factions.cmd.FactionsCommand implements CommandBase {
	
	// ---------------------------------------- //
	// FIELDS
	// ---------------------------------------- //
	
	private FactionsCommand command;
	
	// ---------------------------------------- //
	// CONSTRUCTOR
	// ---------------------------------------- //
	
	public Command_2_8_2(FactionsCommand command) {
		FactionsFramework.get().log("Construct yay");
		this.command = command;
		
		this.aliases.addAll(command.getAliases());
		this.setDesc(command.getDescription());
		
		try {
			Class<?> ARString = Class.forName("com.massivecraft.massivecore.cmd.arg.ARString");
			Method addArg = this.getClass().getMethod("addArg", ARString, String.class);
			
			// Register the required arguments 
			for (String reqArg : command.getRequiredArguments()) {
				addArg.invoke(this, ARString.getMethod("get").invoke(this), reqArg);
			}
			
			// Register all the optional arguments
			for (Entry<String, String> arg : command.getOptionalArguments().entrySet()) {
				addArg.invoke(this, ARString.getMethod("get").invoke(this), arg.getKey(), arg.getKey());
			}
			
			this.getClass().getMethod("setGivingErrorOnTooManyArgs", Boolean.class).invoke(this, ! this.command.overflowAllowed());
		} catch (Exception e) {
			FactionsFramework.get().logError(e);
		}
		
		if (command.getSubCommands().size() > 0) {
			for (FactionsCommand subCommand : command.getSubCommands()) {
				this.addChild(new Command_2_8_2(subCommand));
			}
		}
	}
	
	// ---------------------------------------- //
	// METHODS
	// ---------------------------------------- //
	
	@Override
	public void perform() throws MassiveException {
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
	
	@Override
	public FactionsCommand getAsFactionsCommand() {
		return this.command;
	}
	
}
