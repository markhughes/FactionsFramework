package me.markeh.factionsframework.layer.layer_1_8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.P;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.entities.Handler;

public class CommandManager_1_8 extends FactionsCommandManager {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Map<FactionsCommand, Command_1_8> cmdMap = new HashMap<FactionsCommand, Command_1_8>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		Command_1_8 nativeCommand = new Command_1_8(command);
		
		this.cmdMap.put(command, nativeCommand);
		
		P.p.cmdBase.addSubCommand(nativeCommand);
	}

	@Override
	public void remove(FactionsCommand command) {
		P.p.cmdBase.subCommands.remove(this.cmdMap.get(command));	
	}
	
	@Override
	public void removeAll() {
		for (FactionsCommand command : new HashSet<FactionsCommand>(this.cmdMap.keySet())) {
			this.remove(command);
		}		
	}
	
	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		P.p.cmdAutoHelp.execute(sender, command.getArgs());
	}

	@Override
	public Handler asHandler() {
		return this;
	}
	
}
