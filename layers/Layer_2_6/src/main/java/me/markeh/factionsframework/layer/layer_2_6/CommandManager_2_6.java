package me.markeh.factionsframework.layer.layer_2_6;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.cmd.HelpCommand;
import com.massivecraft.massivecore.cmd.MassiveCommand;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;

public class CommandManager_2_6 extends FactionsCommandManager {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private Map<FactionsCommand, Command_2_6> cmdMap = new HashMap<FactionsCommand, Command_2_6>();

	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //

	@Override
	public void add(FactionsCommand command) {
		// If its added already don't add it again
		if (this.cmdMap.containsKey(command)) return;

		// Create the command and add it to our map
		Command_2_6 originalCommand = new Command_2_6(command);
		this.cmdMap.put(command, originalCommand);

		try {
			getCmdFactions().getClass().getMethod("addSubCommand", Class.forName("com.massivecraft.massivecore.cmd.MassiveCommand")).invoke(getCmdFactions(), originalCommand);
		} catch (Exception e) {
			FactionsFramework.get().err(e);
		}
	}

	@Override
	public void remove(FactionsCommand command) {
		// Only attempt to remove if it has been added
		if ( ! this.cmdMap.containsKey(command)) return;

		// Remove it from Factions
		// For whatever reason they didn't have a removeSubCommand method back here..
		//getCmdFactions().getSubCommands().remove(this.cmdMap.get(command)); 

		// Remove it from out map
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
			Method executeCommand = HelpCommand.get().getClass().getMethod("execute", CommandSender.class, List.class, MassiveCommand.class);

			executeCommand.invoke(HelpCommand.get().getClass(), sender, command.getArgs(), this.cmdMap.get(command).getCommandChain());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------- //
	// UTIL
	// -------------------------------------------------- //

	private com.massivecraft.factions.cmd.CmdFactions factionsInstance = null;
	public com.massivecraft.factions.cmd.CmdFactions getCmdFactions() {
		// We store a cache version of the instance to slow down on reflection use
		if (this.factionsInstance == null) {
			try {
				Factions factions = (Factions) com.massivecraft.factions.Factions.class.getMethod("get").invoke(null);

				Field field = com.massivecraft.factions.Factions.class.getDeclaredField("outerCmdFactions");
				field.setAccessible(true);

				this.factionsInstance = (com.massivecraft.factions.cmd.CmdFactions) field.get(factions);

			} catch(Exception e) {
				FactionsFramework.get().err(e);
				return null;
			}
		}

		return this.factionsInstance;
	}

}
