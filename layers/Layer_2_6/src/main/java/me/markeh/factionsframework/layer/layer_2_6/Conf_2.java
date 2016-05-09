package me.markeh.factionsframework.layer.layer_2_6;

import org.bukkit.ChatColor;

import com.massivecraft.factions.entity.MConf;

import me.markeh.factionsframework.layer.ConfLayer;

/**
 * Configuration files don't change much between versions
 * so MConf is the same.
 *
 */
public class Conf_2 extends ConfLayer {

	@Override
	public String getPrefixLeader() {
		return MConf.get().prefixLeader;
	}

	@Override
	public String getPrefixOfficer() {
		return MConf.get().prefixOfficer;
	}

	@Override
	public String getPrefixMember() {
		return MConf.get().prefixMember;
	}

	@Override
	public String getPrefixRecruit() {
		return MConf.get().prefixRecruit;
	}

	@Override
	public ChatColor getColorMember() {
		return MConf.get().colorMember;
	}

	@Override
	public ChatColor getColorAlly() {
		return MConf.get().colorAlly;
	}

	@Override
	public ChatColor getColorNeutral() {
		return MConf.get().colorNeutral;
	}

	@Override
	public ChatColor getColorTruce() {
		return MConf.get().colorTruce;
	}

	@Override
	public ChatColor getColorEnemy() {
		return MConf.get().colorEnemy;
	}

}
