package me.markeh.factionsframework.layer;

import org.bukkit.ChatColor;

import me.markeh.factionsframework.enums.FactionsVersion;

public abstract class ConfLayer {
	
	// -------------------------------------------------- //
	// FETCH INSTANCE
	// -------------------------------------------------- //
	
	private static ConfLayer layer = null;
	public static ConfLayer get() {
		if (layer == null) {
			try {
				if (FactionsVersion.get().isAtLeast(FactionsVersion.Factions_2_6)) {
					layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_6.Conf_2").newInstance();
				} else {
					if (FactionsVersion.get() == FactionsVersion.Factions_1_8) {
						layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_1_8.Conf_1_8").newInstance();
					} else {
						layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_1_6.Conf_1_6").newInstance();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return layer;
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS
	// -------------------------------------------------- //
	
	public abstract String getPrefixLeader();
	public abstract String getPrefixOfficer();
	public abstract String getPrefixMember();
	public abstract String getPrefixRecruit();
	
	public abstract ChatColor getColorMember();
	public abstract ChatColor getColorAlly();
	public abstract ChatColor getColorNeutral();
	public abstract ChatColor getColorTruce();
	public abstract ChatColor getColorEnemy();
	
}
