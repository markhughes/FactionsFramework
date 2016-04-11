package me.markeh.factionsframework.entities;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
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
	
	public Rel getRole();
	
	public void setRole(Rel role);
	
	public Location getLocation();
	
	public World getWorld();
	
	public String getName();
	
	public double getPowerBoost();
	
	public void setPowerBoost(Double boost);
	
	public boolean hasPowerBoost();
	
	public double getPower();
	
	public int getPowerRounded();
	
	public boolean tryClaim(Faction faction, Location location);
	
	public boolean tryClaim(Faction faction, Collection<Location> locations);
	
}
