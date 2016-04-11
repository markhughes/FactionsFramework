package me.markeh.factionsframework.command;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.enums.FactionsVersion;
import me.markeh.factionsframework.layers.commandmanager.CommandManager_1_6;
import me.markeh.factionsframework.layers.commandmanager.CommandManager_2_6;
import me.markeh.factionsframework.layers.commandmanager.CommandManager_2_8_2;
import me.markeh.factionsframework.layers.commandmanager.CommandManager_2_8_6;
import me.markeh.factionsframework.layers.commandmanager.CommandManager_2_8_7;

public abstract class FactionsCommandManager {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	private static FactionsCommandManager instance;
	public static FactionsCommandManager get() {
		if (instance == null) {
			switch (FactionsVersion.get()) {
				case Factions_1_6 :
					instance = new CommandManager_1_6();
					break;
				case Factions_2_6 :
					instance = new CommandManager_2_6();
					break;
				case Factions_2_8_2 :
					instance = new CommandManager_2_8_2();
					break;
				case Factions_2_8_6 :
					instance = new CommandManager_2_8_6();
					break;
				case Factions_2_8_7 :
					instance = new CommandManager_2_8_7();
					break;
				default :
					instance = null;
					break;
			}
		}
		
		return instance;
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS 
	// -------------------------------------------------- //
	
	public abstract void add(FactionsCommand command);
	public abstract void remove(FactionsCommand command);
	public abstract void removeAll();
	public abstract void showHelpFor(FactionsCommand command, CommandSender sender);

}
