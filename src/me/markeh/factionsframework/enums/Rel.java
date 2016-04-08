package me.markeh.factionsframework.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.ChatColor;

import me.markeh.factionsframework.layers.ConfLayer;

// This is heavily based off the existing Factions Rel enum
public enum Rel {
	
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //
	
	ENEMY(
		"an enemy", "enemies", "an enemy faction", "enemy factions",
		"Enemy"
	),
	NEUTRAL(
		"someone neutral to you", "those neutral to you", "a neutral faction", "neutral factions",
		"Neutral"
	),
	TRUCE(
		"someone in truce with you", "those in truce with you", "a faction in truce", "factions in truce",
		"Truce"
	),
	ALLY(
		"an ally", "allies", "an allied faction", "allied factions",
		"Ally"
	),
	RECRUIT(
		"a recruit in your faction", "recruits in your faction", "", "",
		"Recruit"
	),
	MEMBER(
		"a member in your faction", "members in your faction", "your faction", "your factions",
		"Member"
	),
	OFFICER
	(
		"an officer in your faction", "officers in your faction", "", "",
		"Officer", "Moderator"
	),
	LEADER("your faction leader", "your faction leader", "", "",
		"Leader", "Admin", "Owner"
	),
	
	// END OF LIST
	;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public int getValue() { return this.ordinal(); }
	
	private final String descPlayerOne;
	public String getDescPlayerOne() { return this.descPlayerOne; }
	
	private final String descPlayerMany;
	public String getDescPlayerMany() { return this.descPlayerMany; }
	
	private final String descFactionOne;
	public String getDescFactionOne() { return this.descFactionOne; }
	
	private final String descFactionMany;
	public String getDescFactionMany() { return this.descFactionMany; }
	
	private final Set<String> names;
	public Set<String> getNames() { return this.names; }
	public String getName() { return this.getNames().iterator().next(); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	private Rel(String descPlayerOne, String descPlayerMany, String descFactionOne, String descFactionMany, String... names)
	{
		this.descPlayerOne = descPlayerOne;
		this.descPlayerMany = descPlayerMany;
		this.descFactionOne = descFactionOne;
		this.descFactionMany = descFactionMany;
		this.names = Collections.unmodifiableSet(new TreeSet<String>(Arrays.asList(names)));
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public boolean isAtLeast(Rel rel)
	{
		return this.getValue() >= rel.getValue();
	}
	
	public boolean isAtMost(Rel rel)
	{
		return this.getValue() <= rel.getValue();
	}
	
	public boolean isLessThan(Rel rel)
	{
		return this.getValue() < rel.getValue();
	}
	
	public boolean isMoreThan(Rel rel)
	{
		return this.getValue() > rel.getValue();
	}
	
	public boolean isRank()
	{
		return this.isAtLeast(Rel.RECRUIT);
	}
	
	// Used for friendly fire.
	public boolean isFriend()
	{
		return this.isAtLeast(TRUCE);
	}
	
	public ChatColor getColor()
	{
		if (this.isAtLeast(RECRUIT))
			return ConfLayer.get().getColorMember();
		else if (this == ALLY)
			return ConfLayer.get().getColorAlly();
		else if (this == NEUTRAL)
			return ConfLayer.get().getColorNeutral();
		else if (this == TRUCE)
			return ConfLayer.get().getColorTruce();
		else
			return ConfLayer.get().getColorEnemy();
	}
	
	public String getPrefix()
	{
		if (this == LEADER)
		{
			return ConfLayer.get().getPrefixLeader();
		} 
		
		if (this == OFFICER)
		{
			return ConfLayer.get().getPrefixOfficer();
		}
		
		if (this == MEMBER)
		{
			return ConfLayer.get().getPrefixMember();
		}
		
		if (this == RECRUIT)
		{
			return ConfLayer.get().getPrefixRecruit();
		}
		
		return "";
	}

}
