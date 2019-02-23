package me.markeh.factionsframework.layer;

import org.bukkit.ChatColor;

import me.markeh.factionsframework.enums.FactionsVersion;

public abstract class ConfLayer {
	
	private static ConfLayer layer = null;
	public static ConfLayer get() {
		if (layer == null) {
			try {
				if (FactionsVersion.get().isAtLeast(FactionsVersion.Factions_2_6)) {
					layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_6.Conf_2").newInstance();
				} else {
					if (FactionsVersion.get() == FactionsVersion.Factions_1_8) {
						layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_1_8.Conf_1_8").newInstance();
					} else if (FactionsVersion.get() == FactionsVersion.Factions_1_6) {
						layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_1_6.Conf_1_6").newInstance();
					} else if (FactionsVersion.get() == FactionsVersion.Factions_0_2_2) {
						layer = (ConfLayer) Class.forName("me.markeh.factionsframework.layer.layer_0_2_2.Conf_0_2_2").newInstance();
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return layer;
	}
	
	public static void overrideConfLayer(ConfLayer confLayer) {
		layer = confLayer;
	}
	
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
