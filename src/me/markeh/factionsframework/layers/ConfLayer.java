package me.markeh.factionsframework.layers;

import org.bukkit.ChatColor;

import me.markeh.factionsframework.enums.FactionsVersion;
import me.markeh.factionsframework.layers.conf.Conf_1_6;
import me.markeh.factionsframework.layers.conf.Conf_2;

/**
 * ConfLayer is not ready yet
 *
 */
public abstract class ConfLayer {
	
	// -------------------------------------------------- //
	// FETCH INSTANCE
	// -------------------------------------------------- //
	
	private static ConfLayer layer = null;
	public static ConfLayer get() {
		if (layer == null) {
			if (FactionsVersion.get().isAtLeast(FactionsVersion.Factions_2_6)) {
				layer = new Conf_2();
			} else {
				layer = new Conf_1_6();
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
