package me.markeh.factionsframework.layer.layer_2_7;

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
import me.markeh.factionsframework.entities.Handler;

public class CommandManager_2_7 extends FactionsCommandManager {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Map<FactionsCommand, Command_2_7> cmdMap = new HashMap<FactionsCommand, Command_2_7>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		Command_2_7 nativeCommand = new Command_2_7(command);
		
		this.cmdMap.put(command, nativeCommand);
		
		try {
			this.getCmdFactions().getClass().getMethod("addChild", MassiveCommand.class).invoke(getCmdFactions(), nativeCommand);
		} catch (Exception e1) {
			try {
				this.getCmdFactions().getClass().getMethod("addSubCommand", MassiveCommand.class).invoke(getCmdFactions(), nativeCommand);
			} catch (Exception e2) {
				this.cmdMap.remove(command);
				FactionsFramework.get().err(e1);
				FactionsFramework.get().err(e2);
			}
		}
	}

	@Override
	public void remove(FactionsCommand command) {
		try {
			getCmdFactions().getClass().getMethod("removeChild", MassiveCommand.class).invoke(getCmdFactions(), this.cmdMap.get(command));
			
			this.cmdMap.remove(command);
		} catch (Exception e1) {
			try {
				getCmdFactions().getClass().getMethod("removeSubCommand", MassiveCommand.class).invoke(getCmdFactions(), this.cmdMap.get(command));
				
				this.cmdMap.remove(command);
			} catch (Exception e2) {
				FactionsFramework.get().err(e1);
				FactionsFramework.get().err(e2);
			}
		}		
	}
	
	@Override
	public void removeAll() {
		for (FactionsCommand command : new HashSet<FactionsCommand>(this.cmdMap.keySet())) {
			this.remove(command);
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		try {
			HelpCommand.get().execute(sender, command.getArgs(), (List<MassiveCommand>) this.cmdMap.get(command).getClass().getMethod("getChain").invoke(this));
		} catch (Exception e) {
			FactionsFramework.get().err(e);
		}
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
				FactionsFramework.get().err(e);
				return null;
			}
		}
		
		return this.cmdFactionsInstance;
	}

	@Override
	public Handler asHandler() {
		return this;
	}
	
}
