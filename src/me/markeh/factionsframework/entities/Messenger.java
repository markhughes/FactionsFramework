package me.markeh.factionsframework.entities;

import me.markeh.factionsframework.Util;

public abstract class Messenger {
	
	public abstract void msg(String msg);
	
	public void msg(String msg, String... allocations) {
		msg = Util.colourse(msg);
		
		String key = null;
		
		for (String allocation : allocations) {
			if (key == null) {
				key = allocation;
			} else {
				msg.replaceAll("{" + key + "}" , allocation);
				key = null;
			}
		}
		
		this.msg(msg);
	}
}
