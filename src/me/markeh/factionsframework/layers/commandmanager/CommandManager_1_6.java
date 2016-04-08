package me.markeh.factionsframework.layers.commandmanager;

import java.util.HashMap;
import java.util.Map;

import com.massivecraft.factions.P;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.layers.commands.Command_1_6;

public class CommandManager_1_6 extends FactionsCommandManager {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Map<FactionsCommand, Command_1_6> cmdMap = new HashMap<FactionsCommand, Command_1_6>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		// If its added already don't add it again  
		if (this.cmdMap.containsKey(command)) return;
		
		// Create the command and add it to our map
		Command_1_6 originalCommand = new Command_1_6(command);
		this.cmdMap.put(command, originalCommand);
		
		// Add it to Factions 
		P.p.cmdBase.addSubCommand(originalCommand);
	}

	@Override
	public void remove(FactionsCommand command) {
		// Only attempt to remove if it has been added 
		if ( ! this.cmdMap.containsKey(command)) return;
		
		// Remove it from Factions
		P.p.cmdBase.subCommands.remove(this.cmdMap.get(command));
		
		// Remove it from out map
		this.cmdMap.remove(command);
	}

	@Override
	public void removeAll() {
		for (FactionsCommand command : this.cmdMap.keySet()) {
			this.remove(command);
		}
	}

}
