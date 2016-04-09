package me.markeh.factionsframework.layers.commandmanager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.massivecore.command.HelpCommand;
import com.massivecraft.massivecore.command.MassiveCommand;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.layers.commands.Command_2_8_7;

public class CommandManager_2_8_7 extends FactionsCommandManager {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private Map<FactionsCommand, MassiveCommand> cmdMap = new HashMap<FactionsCommand, MassiveCommand>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		if (cmdMap.containsKey(command)) return;
		
		Command_2_8_7 nativeCommand = new Command_2_8_7(command);
		
		this.cmdMap.put(command, nativeCommand);
		
		CmdFactions.get().addChild(nativeCommand);
	}

	@Override
	public void remove(FactionsCommand command) {
		if ( ! cmdMap.containsKey(command)) return;
		
		CmdFactions.get().removeChild(cmdMap.get(command));
		
		cmdMap.remove(command);
	}

	@Override
	public void removeAll() {
		for (FactionsCommand command : this.cmdMap.keySet()) {
			this.remove(command);
		}		
	}

	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		HelpCommand.get().execute(sender, command.getArgs(), this.cmdMap.get(command).getChain());		
	}

}
