package me.markeh.factionsframework.layer.layer_2_8_16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.massivecore.command.MassiveCommandHelp;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.entities.Handler;

public class CommandManager_2_8_16 extends FactionsCommandManager {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private Map<FactionsCommand, Command_2_8_16> cmdMap = new HashMap<FactionsCommand, Command_2_8_16>();

	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //

	@Override
	public void add(FactionsCommand command) {
		Command_2_8_16 nativeCommand = new Command_2_8_16(command);

		this.cmdMap.put(command, nativeCommand);
		
		CmdFactions.get().addChild(nativeCommand);
	}

	@Override
	public void remove(FactionsCommand command) {
		CmdFactions.get().removeChild(this.cmdMap.get(command));
	}

	@Override
	public void removeAll() {
		for (FactionsCommand command : new HashSet<FactionsCommand>(this.cmdMap.keySet())) {
			this.remove(command);
		}
	}

	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		MassiveCommandHelp.get().execute(sender, command.getArgs(), this.cmdMap.get(command).getChain());
	}

	@Override
	public Handler asHandler() {
		return this;
	}

}
