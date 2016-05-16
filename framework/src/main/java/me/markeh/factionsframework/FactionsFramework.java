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

package me.markeh.factionsframework;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.deprecation.Deprecation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.FactionsVersion;
import me.markeh.factionsframework.layer.ConfLayer;
import me.markeh.factionsframework.layer.EventsLayer;
import me.markeh.factionsframework.layer.LoadBase;

/**
 * Factions Framework has a 6 month deprecation policy.
 * See me.markeh.factionsframework.deprecation.Deprecation for information.
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
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private LogUtil logUtil = new LogUtil(this, ChatColor.AQUA);
	
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
		
		try {
			new Metrics(this).enable();
		} catch (IOException e) {
			this.err(e);
		}
		
		log("Factions version is: " + FactionsVersion.get().toString());
		
		if (LoadBase.get() != null) {
			LoadBase.get().enabled();
		}
	}
	
	// -------------------------------------------------- //
	// DISABLE  
	// -------------------------------------------------- //
	
	public final void onDisable() {
		// Unregister our events layer 
		HandlerList.unregisterAll(EventsLayer.get());
		
		FactionsCommandManager.get().removeAll();
		
		if (LoadBase.get() != null) {
			LoadBase.get().disabled();
		}
	}
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	public final void err(Exception e) {
		this.logUtil.err(e);
	}
	
	public final void log(String...msgs) {
		for (String msg : msgs) this.logUtil.log(msg);
	}
	
	public final Gson getGson() {
		return this.gson;
	}
	
	/**
	 * Get Factions instance 
	 * 
	 * @deprecated to be removed on 06/12/2016
	 * Use the static methods instead of this instance
	 */
	@Deprecated
	public final Factions getFactions() {
		Deprecation.showDeprecationWarningForMethod("FactionsFramework#getFactions");
		return ((Factions) Factions.getHandler());
	}
	
	/**
	 * Get FPlayers instance 
	 * 
	 * @deprecated to be removed on 06/12/2016
	 * Use the static methods instead of this instance
	 */
	@Deprecated
	public final FPlayers getFPlayers() {
		Deprecation.showDeprecationWarningForMethod("FactionsFramework#getFPlayers");
		return ((FPlayers) Factions.getHandler());
	}
	
	/**
	 * Log an error
	 * 
	 * @deprecated to be removed on 16/12/2016
	 * Use the err method instead 
	 */
	@Deprecated
	public final void logError(Exception e) {
		this.logUtil.err(e);
	}
	
}
