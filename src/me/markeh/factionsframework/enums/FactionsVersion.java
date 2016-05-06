package me.markeh.factionsframework.enums;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public enum FactionsVersion {
	
	Factions_1_6,
	Factions_1_8,
	Factions_2_6,
	Factions_2_8_2,
	Factions_2_8_6,
	Factions_2_8_7,
	
	;
	
	private static FactionsVersion versionCache = null;
	public static FactionsVersion get() {
		if (versionCache == null) versionCache = determine();
		return versionCache;
	}
	
	private static FactionsVersion determine() {
		// Fetch the Factions plugin and ensure it exists 
		Plugin factionsPlugin = Bukkit.getPluginManager().getPlugin("Factions");
		
		String factionsVersion = factionsPlugin.getDescription().getVersion();
		
		if (factionsVersion.startsWith("1.6.9.5-U")) {
			// Factions UUID starts with '1.6.9.5-U', we only support the
			// latest version of FactionsUUID
			return Factions_1_6;
		} else if (factionsVersion.startsWith("1.8")) {
			return Factions_1_8;
		} else if (factionsVersion.startsWith("2.")) {
			// Is a 2.x variation, however there are lots of changes through these
			// minor releases we can use to determine the version.
			
			try {
				// Factions Versions <= 2.6 use the universe system (or is unsupported) 
				Class.forName("com.massivecraft.factions.entity.UPlayer");
				return Factions_2_6;
			} catch (Exception e) { }
			
			try {
				// Factions Versions <= 2.8.6 has Spigot integration in an older spot 
				Class.forName("com.massivecraft.factions.spigot.SpigotFeatures");
				
				try {
					Class.forName("com.massivecraft.massivecore.cmd.arg.ARString");
					// Only different between 2.8.2 and 2.8.6 is command versioning
					return Factions_2_8_2;
				} catch (Exception e) { }
				
				return Factions_2_8_6;
			} catch (Exception e) { }
			
			// We assume it's a more recent version of Factions 
			return Factions_2_8_7;					
		}
		
		throw new Error("Please use FactionsUUID (1.6.9.5-U), FactionsOne(1.8.x), or Factions 2.5+");
	}

	public boolean isAtLeast(FactionsVersion version) {
		return (this.ordinal() >= version.ordinal());
	}
	
}
