package me.markeh.factionsframework.deprecation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Factions Framework has a minimum 6 month deprecation policy.
 * 
 * Fields and methods up for deprecation will add the deprecated annotation
 * and we will automatically convert the use of that method or field to the
 * newer or more appropriate method.
 * 
 * If it is being removed and there is no alternative it will be left as-is,
 * after the 6 months its contents will be removed and it will instead
 * trigger a deprecation warning. Following 1 month after this it will be
 * removed from the code base.
 * 
 */
public class Deprecation {
	
	// -------------------------------------------------- //
	// EXAMPLE
	// -------------------------------------------------- //
	
	/**
	 * Get a player name as a string
	 * 
	 * @deprecated to be removed on 12/XX/201X
	 */
	@Deprecated
	public String getPlayerName(Player player) {		
		// PlayerDatabase database = PlayerDatabase.get(player); 
		// return database.fetchName();
		
		return player.getName();
		
		// -------------------------------------------------- //
		// README
		// -------------------------------------------------- //
		// This method has been deprecated and subject to removal 
		// in 6 months. Its old code has been commented out and 
		// the better alternative has been added. 
	}
	
	/**
	 * Get a player name as a string
	 * 
	 * @deprecated removing on 12/XX/201X
	 */
	@Deprecated
	public String getPlayerName2(Player player) {
		Deprecation.showDeprecationWarningForMethod("Deprecation#getPlayerName2", "Player");
		return player.getName();
		
		// -------------------------------------------------- //
		// README
		// -------------------------------------------------- //
		// As the 6 month period has passed, it is now subject
		// to removal. We add in the deprecation warning and 
		// allow about 4 weeks before it is removed from the
		// code base.
	}
	
	
	// -------------------------------------------------- //
	// UTILS
	// -------------------------------------------------- //
	// These methods aren't part of the examples
	
	public static void showDeprecationWarningForMethod(String method, String... args) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(ChatColor.RED + "[Warning] " + method + "(");
		
		Boolean hasArgs = false;
		
		for (String arg : args) {
			if (arg == null) continue;
			
			if (hasArgs) sb.append(", ");
			
			sb.append(arg);
		}
		
		sb.append(") ");
		
		sb.append("Has been deprecated and will be removed soon. Ask the plugin developer to update.");
		
		Bukkit.getConsoleSender().sendMessage(sb.toString());
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This is NOT an issue with FactionsFramework but with a plugin created using FactionsFramework.");
		
	}
	
	public static void showDeprecationWarningForMethod(String method) {
		showDeprecationWarningForMethod(method, (String) null);
	}
}
