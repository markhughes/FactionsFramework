package me.markeh.factionsframework;

import org.bukkit.ChatColor;

public class Util {

	public static String colourse(String msg) {
		for (ChatColor color : ChatColor.values()) {
			msg.replaceAll("<" + color.name().toLowerCase() + ">", color+"");
		}
		
		return msg;
	}
}
