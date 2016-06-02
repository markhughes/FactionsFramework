package me.markeh.factionsframework.entities;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Location;

import me.markeh.factionsframework.enums.Rel;

public interface Faction {
	
	/**
	 * Get Faction Id
	 * 
	 * @return the faction id
	 */
	public String getId();
	
	/**
	 * Get Faction name
	 * 
	 * @return the faction name
	 */
	public String getName();
	
	/**
	 * Get Faction description
	 * 
	 * @return the faction description
	 */
	public String getDescription();
	
	/**
	 * Set the Faction description
	 */
	public void setDescription(String description);
	
	public Set<FPlayer> getMembers();
	
	public Set<FPlayer> getOfficers();
	
	public Set<FPlayer> getMembersExcept(Rel... rels);
	
	public Optional<FPlayer> leader();
	
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
	
	/**
	 * Get Faction Leader (old)
	 * 
	 * @deprecated to be removed on 03/02/2017
	 * Now using Java 8 Optional in the leader method
	 * use .leader().isPresent() to check for a leader and
	 * .leader().get() to get the leader.
	 */
	@Deprecated
	public FPlayer getLeader();
	
}
