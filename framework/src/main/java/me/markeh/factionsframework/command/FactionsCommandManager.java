package me.markeh.factionsframework.command;

import org.bukkit.command.CommandSender;

import me.markeh.factionsframework.entities.Handler;
import me.markeh.factionsframework.enums.FactionsVersion;

public abstract class FactionsCommandManager implements Handler {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	private static FactionsCommandManager instance;
	public static FactionsCommandManager get() {
		if (instance == null) {
			try {
				switch (FactionsVersion.get()) {
					case Factions_0_2_2:
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_0_2_2.CommandManager_0_2_2").newInstance();
						break;
					case Factions_1_6 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_1_6.CommandManager_1_6").newInstance();
						break;
					case Factions_1_8 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_1_8.CommandManager_1_8").newInstance();
						break;
					case Factions_2_6 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_6.CommandManager_2_6").newInstance();
						break;
					case Factions_2_7 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_7.CommandManager_2_7").newInstance();
						break;
					case Factions_2_8_2 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_2.CommandManager_2_8_2").newInstance();
						break;
					case Factions_2_8_6 :
					case Factions_2_8_7 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_6.CommandManager_2_8_6").newInstance();
						break;
					case Factions_2_8_8 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_8.CommandManager_2_8_8").newInstance();
						break;
					case Factions_2_8_16 :
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_8_16.CommandManager_2_8_16").newInstance();
//						FactionsFramework.get().log("[FactionsCommandManager] No CommandManager for version?");
//						instance = null;
						break;
					default:
					case Factions_2_14:
						instance = (FactionsCommandManager) Class.forName("me.markeh.factionsframework.layer.layer_2_14.CommandManager_2_14").newInstance();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
	public static void overrideHandler(Handler handler) {
		instance = (FactionsCommandManager) handler;
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS 
	// -------------------------------------------------- //
	
	public abstract void add(FactionsCommand command);
	public abstract void remove(FactionsCommand command);
	public abstract void removeAll();
	public abstract void showHelpFor(FactionsCommand command, CommandSender sender);

}
