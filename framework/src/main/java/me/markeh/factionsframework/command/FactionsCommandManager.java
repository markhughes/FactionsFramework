package me.markeh.factionsframework.command;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.enums.FactionsVersion;

public abstract class FactionsCommandManager {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	private static FactionsCommandManager instance;
	public static FactionsCommandManager get() {
		if (instance == null) {
			try {
				switch (FactionsVersion.get()) {
					case Factions_1_6 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_1_6.CommandManager_1_6").newInstance();
						break;
					case Factions_2_6 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_6.CommandManager_2_6").newInstance();
						break;
					case Factions_2_8_2 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_2.CommandManager_2_8_2").newInstance();
						break;
					case Factions_2_8_6 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_6.CommandManager_2_8_6").newInstance();
						break;
					case Factions_2_8_7 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_7.CommandManager_2_8_7").newInstance();
						break;
					default :
						instance = null;
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
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
