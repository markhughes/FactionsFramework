package me.markeh.factionsframework.layers.commands;

import java.lang.reflect.Method;
import java.util.Map.Entry;

import com.massivecraft.massivecore.MassiveException;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;

public class Command_2_8_6 extends com.massivecraft.factions.cmd.FactionsCommand implements CommandBase {

	// ---------------------------------------- //
	// FIELDS
	// ---------------------------------------- //

	private FactionsCommand command;
	
	// ---------------------------------------- //
	// CONSTRUCTOR
	// ---------------------------------------- //
	
	public Command_2_8_6(FactionsCommand command) {
		this.command = command;
		
		this.aliases.addAll(command.getAliases());
		this.setDesc(command.getDescription());
		
		try {
			
			/*
			 * Factions <= 2.8.2
			 * 
			 * Older versions use an older AR style classes  
			 */
			
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
			
			/*
			 * Factions > 2.8.2
			 * 
			 * 2.8 uses the new Type style classes 
			 */
			
			try { 
				
				for (String reqArg : command.getRequiredArguments()) {
					this.addParameter(com.massivecraft.massivecore.command.type.primitive.TypeString.get(), reqArg);
				}
				
				// Register the optional arguments
				for (Entry<String, String> arg : command.getOptionalArguments().entrySet()) {
					this.addParameter(com.massivecraft.massivecore.command.type.primitive.TypeString.get(), arg.getKey(), arg.getValue());
				}
				
			} catch (Exception e2) {
				FactionsFramework.get().logError(e);
			}
			
			this.overflowSensitive = ! command.overflowAllowed();			
		}
		
		if (command.getSubCommands().size() > 0) {
			for (FactionsCommand subCommand : command.getSubCommands()) {
				this.addChild(new Command_2_8_6(subCommand));
			}
		}
		
		this.chain.add(this);
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
	
}
