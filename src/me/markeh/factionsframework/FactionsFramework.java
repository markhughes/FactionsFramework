package me.markeh.factionsframework;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.FactionsVersion;
import me.markeh.factionsframework.layers.*;
import me.markeh.factionsframework.layers.factions.*;
import me.markeh.factionsframework.layers.fplayers.*;

/**
 * Factions Framework-  Way too much caffeine was required to make this.
 * Copyright (C) 2016  Mark Hughes ("MarkehMe") <m@rkhugh.es>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * https://github.com/MarkehMe/FactionsFramework
 *
 */
public class FactionsFramework extends JavaPlugin {
	
	// -------------------------------------------------- //
	// SINGLETON  
	// -------------------------------------------------- //
	
	private static FactionsFramework i;
	public static FactionsFramework get() { return i; }
	public FactionsFramework() { i = this; }
	
	// -------------------------------------------------- //
	// FIELDS  
	// -------------------------------------------------- //
		
	private Factions factionsInstance = null;
	private FPlayers fplayersInstance = null;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	// -------------------------------------------------- //
	// ENABLE  
	// -------------------------------------------------- //

	@Override
	public final void onEnable() {
		// Load our config by running get()
		FrameworkConfig.get();
		
		// Initialise ConfLayer early 
		ConfLayer.get();
	
		// Enable events
		this.getServer().getPluginManager().registerEvents(EventsLayer.get(), this);
	}
	
	// -------------------------------------------------- //
	// DISABLE  
	// -------------------------------------------------- //
	
	public final void onDisable() {
		// Unregister our events layer 
		HandlerList.unregisterAll(EventsLayer.get());
		
		FactionsCommandManager.get().removeAll();
	}
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	public final void logError(Exception e) {
		String factionsVersion = this.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion();
		
		log("An internal error ocurred, if you're reporting this include from here until you get to the three dashes at the end ");
		log("Factions version registered as: " + FactionsVersion.get().toString());
		log("Factions Plugin Version: " + factionsVersion);
		
		e.printStackTrace();
		
		log(" - - - ");
		
	}
	
	public final Factions getFactions() {
		if (this.factionsInstance == null) {
			switch (FactionsVersion.get()) {
				case Factions_1_6 :
					this.factionsInstance = new Factions_1_6();
					break;
				case Factions_2_6 :
					this.factionsInstance = new Factions_2_6();
					break;
				case Factions_2_8_6 :
					this.factionsInstance = new Factions_2_8_6();
					break;
				case Factions_2_8_7 :
					this.factionsInstance = new Factions_2_8_7();
					break;
				default :
					this.factionsInstance = null;
					break;
			}
		}
		
		return this.factionsInstance;
	}
	
	public final FPlayers getFPlayers() {
		if (this.fplayersInstance == null) {
			switch (FactionsVersion.get()) {
				case Factions_1_6 :
					this.fplayersInstance = new FPlayers_1_6();
					log("Factions instance ");
					break;
				case Factions_2_6 :
					this.fplayersInstance = new FPlayers_2_6();
					break;
				case Factions_2_8_6 :
					this.fplayersInstance = new FPlayers_2_8_6();
					break;
				case Factions_2_8_7 :
					this.fplayersInstance = new FPlayers_2_8_7();
					break;
				default :
					this.fplayersInstance = null;
					break;
			}
		}
		
		return this.fplayersInstance;
	}
	
	public final void log(String...msgs) {
		for (String msg : msgs) this.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[FactionsFramework] " + msg);
	}
	
	public final Gson getGson() {
		return this.gson;
	}
	
}
