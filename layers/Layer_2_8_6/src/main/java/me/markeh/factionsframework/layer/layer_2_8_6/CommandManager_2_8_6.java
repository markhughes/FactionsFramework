package me.markeh.factionsframework.layer.layer_2_8_6;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.command.HelpCommand;
import com.massivecraft.massivecore.command.MassiveCommand;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;

public class CommandManager_2_8_6 extends FactionsCommandManager {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Map<FactionsCommand, Command_2_8_6> cmdMap = new HashMap<FactionsCommand, Command_2_8_6>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		Command_2_8_6 nativeCommand = new Command_2_8_6(command);
		
		this.cmdMap.put(command, nativeCommand);
		
		try {
			this.getCmdFactions().getClass().getMethod("addChild", MassiveCommand.class).invoke(getCmdFactions(), command);
		} catch (Exception e1) {
			try {
				this.getCmdFactions().getClass().getMethod("addSubCommand", MassiveCommand.class).invoke(getCmdFactions(), command);
			} catch (Exception e2) {
				this.cmdMap.remove(command);
				FactionsFramework.get().logError(e1);
				FactionsFramework.get().logError(e2);
			}
		}
	}

	@Override
	public void remove(FactionsCommand command) {
		try {
			getCmdFactions().getClass().getMethod("removeChild", MassiveCommand.class).invoke(getCmdFactions(), command);
			
			this.cmdMap.remove(command);
		} catch (Exception e1) {
			try {
				getCmdFactions().getClass().getMethod("removeSubCommand", MassiveCommand.class).invoke(getCmdFactions(), command);
				
				this.cmdMap.remove(command);
			} catch (Exception e2) {
				FactionsFramework.get().logError(e1);
				FactionsFramework.get().logError(e2);
			}
		}		
	}
	
	@Override
	public void removeAll() {
		for (FactionsCommand command : new HashSet<FactionsCommand>(this.cmdMap.keySet())) {
			this.remove(command);
		}		
	}
	
	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		// TODO: in the future this changes to MassiveCommandHelp:
		//  https://github.com/MassiveCraft/MassiveCore/blob/master/src/com/massivecraft/massivecore/command/MassiveCommandHelp.java
		HelpCommand.get().execute(sender, command.getArgs(), this.cmdMap.get(command).getChain());
	}
	
	// -------------------------------------------------- //
	// UTILS
	// -------------------------------------------------- //
	
	private com.massivecraft.factions.cmd.CmdFactions cmdFactionsInstance = null;
	public com.massivecraft.factions.cmd.CmdFactions getCmdFactions() {
		if (this.cmdFactionsInstance == null) {
			try { 
				Method getMethod = com.massivecraft.factions.Factions.class.getMethod("get");
				Factions factionsInstance = (Factions) getMethod.invoke(null);
							
				Field field = com.massivecraft.factions.Factions.class.getDeclaredField("outerCmdFactions");
				field.setAccessible(true);
							
				this.cmdFactionsInstance = (com.massivecraft.factions.cmd.CmdFactions) field.get(factionsInstance);
				
			} catch(Exception e) {
				FactionsFramework.get().logError(e);
				return null;
			}
		}
		
		return this.cmdFactionsInstance;
	}
	
}
