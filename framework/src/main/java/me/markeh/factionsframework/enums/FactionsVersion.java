package me.markeh.factionsframework.enums;

import org.bukkit.plugin.Plugin;

import me.markeh.factionsframework.FactionsFramework;

public enum FactionsVersion {
	
	Factions_0_2_2,
	Factions_1_6,
	Factions_1_8,
	Factions_2_6,
	Factions_2_7,
	Factions_2_8_2,
	Factions_2_8_6,
	Factions_2_8_7,
	Factions_2_8_8,
	Factions_2_8_16,
	Factions_2_14;
	
	private static FactionsVersion versionCache = null;
	public static FactionsVersion get() {
		if (versionCache == null) versionCache = determine();
		return versionCache;
	}
	
	private static FactionsVersion determine() {
		// Fetch the Factions plugin and ensure it exists 
		Plugin factionsPlugin = FactionsFramework.get().getServer().getPluginManager().getPlugin("Factions");
		
		String factionsVersion = factionsPlugin.getDescription().getVersion();
		
		if (factionsVersion.startsWith("1.6.9.5-U")) {
			// Factions UUID starts with '1.6.9.5-U', we only support the
			// latest version of FactionsUUID
			if (factionsVersion.startsWith("1.6.9.5-U0.1.6")) {
				return Factions_1_6;
			} else if (factionsVersion.startsWith("1.6.9.5-U0.2.2")) {
				return Factions_0_2_2;
			}
		} else if (factionsVersion.startsWith("1.8")) {
			return Factions_1_8;
		} else if (factionsVersion.startsWith("2.")) {
			// Is a 2.x variation, however there are lots of changes through these
			// minor releases we can use to determine the version.
			
			if (factionsVersion.equals("2.14.0")) {
				return Factions_2_14;
			}
			
			// Factions >= 2.8.15 has new TypeDamageModifier
			try {
				Class.forName("com.massivecraft.massivecore.command.type.enumeration.TypeDamageModifier");
				return Factions_2_8_16;
			} catch (Exception e) { }
			
			// must be an older version .. 
			
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
					
					try {
						Class.forName("com.massivecraft.massivecore.MassiveException");
						return Factions_2_8_2;
					} catch (Exception e) {
						return Factions_2_7;
					}
					
				} catch (Exception e) { }
				
				return Factions_2_8_6;
			} catch (Exception e) { }
			
			try {
				Class.forName("com.massivecraft.massivecore.command.MassiveCommandHelp");
			} catch (Exception e) {
				return Factions_2_8_7;					

			}
			// We assume it's a more recent version of Factions 
			return Factions_2_8_8;					
		}
		
		throw new Error("Please use FactionsUUID (1.6.9.5-U0.x), FactionsOne(1.8.x), or Factions 2.5+");
	}

	public boolean isAtLeast(FactionsVersion version) {
		return (this.ordinal() >= version.ordinal());
	}
	
}
