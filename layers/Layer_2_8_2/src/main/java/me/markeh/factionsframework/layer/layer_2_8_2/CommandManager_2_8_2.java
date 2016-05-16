package me.markeh.factionsframework.layer.layer_2_8_2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.cmd.HelpCommand;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;

public class CommandManager_2_8_2 extends FactionsCommandManager {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Map<FactionsCommand, Command_2_8_2> cmdMap = new HashMap<FactionsCommand, Command_2_8_2>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		Command_2_8_2 nativeCommand = new Command_2_8_2(command);
		
		this.cmdMap.put(command, nativeCommand);
		
		Factions.get().getOuterCmdFactions().addSubCommand(nativeCommand);
	}

	@Override
	public void remove(FactionsCommand command) {
		Factions.get().getOuterCmdFactions().removeSubCommand(this.cmdMap.get(command));
		this.cmdMap.remove(command);
	}
	
	@Override
	public void removeAll() {
		for (FactionsCommand command : new HashSet<FactionsCommand>(this.cmdMap.keySet())) {
			this.remove(command);
		}		
	}
	
	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		try {
			HelpCommand.get().execute(sender, command.getArgs(), this.cmdMap.get(command).getCommandChain());
		} catch (Exception e) {
			FactionsFramework.get().err(e);
		}
	}
	
}
