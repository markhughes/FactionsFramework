package me.markeh.factionsframework.entities;

import org.bukkit.entity.Player;

import me.markeh.factionsframework.enums.Rel;

public interface FPlayer {

	public String getId();
	
	public Player asBukkitPlayer();
	
	public Faction getFaction();
	
	public void setFaction(Faction faction);
	
	public Rel getRelationTo(Object object);
	
	public String getUniverse();
	
	public void msg(String msg);
	
	public void msg(String msg, String... allocations);
	
	public Boolean isOnline();
	
}
