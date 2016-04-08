package me.markeh.factionsframework.entities;

import java.util.Set;

import org.bukkit.Location;

import me.markeh.factionsframework.enums.Rel;

public interface Faction {
	
	public String getId();
	
	public String getName();
	
	public String getDescription();
	
	public Set<FPlayer> getMembers();
	
	public Set<FPlayer> getOfficers();
	
	public FPlayer getLeader();
	
	public Location getHome();
	
	public Set<Faction> getRelationsWith(Rel rel);
	
	public Rel getRelationTo(Object rel);
	
	public int getLandCount();
	
	public double getPower();
	
	public boolean isPermanentFaction();
	
	public boolean isNone();
		
	public Boolean quiteDisband();
	
	public Boolean addMember(FPlayer fplayer);
	
	public Boolean isValid();
	
	public void msg(String msg);
	
	public void msg(String msg, String... allocations);
	
}
