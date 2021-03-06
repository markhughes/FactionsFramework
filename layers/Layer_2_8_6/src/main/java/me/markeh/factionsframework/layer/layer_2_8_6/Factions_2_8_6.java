package me.markeh.factionsframework.layer.layer_2_8_6;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.ps.PS;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;

public class Factions_2_8_6 extends Factions {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private HashMap<String, Faction> factionsMap = new HashMap<String, Faction>();
	private String noneId = null;
	private String warzoneId = null;
	private String safezoneId = null;
	
	// -------------------------------------------------- //
	// METHODS 
	// -------------------------------------------------- //
	
	@Override
	public Faction get(String id) {
		if ( ! this.factionsMap.containsKey(id)) {
			Faction_2_8_6 faction = new Faction_2_8_6(id);
			this.factionsMap.put(id, faction);
		}
		
		if ( ! this.factionsMap.get(id).isValid()) {
			this.factionsMap.remove(id);
			return null;
		}
		
		return this.factionsMap.get(id);
	}
	
	@Override
	public Faction getUsingName(String name, String universe) {
		return this.get(FactionColl.get().getByName(name).getId());
	}

	@Override
	public Faction getAt(Chunk chunk) {
		com.massivecraft.factions.entity.Faction mfaction = BoardColl.get().getFactionAt(PS.valueOf(chunk));
		
		if (mfaction == null) return null;
		
		return get(mfaction.getId());
	}

	@Override
	public Faction getFactionNone(World world) {
		if (this.noneId == null) {
			try {
				this.noneId = (String) com.massivecraft.factions.Factions.class.getField("ID_NONE").get(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return get(this.noneId);
	}

	@Override
	public Faction getFactionWarZone(World world) {
		if (this.warzoneId == null) {
			try {
				this.warzoneId = (String) com.massivecraft.factions.Factions.class.getField("ID_WARZONE").get(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return get(this.warzoneId);
	}

	@Override
	public Faction getFactionSafeZone(World world) {
		if (this.safezoneId == null) {
			try {
				this.safezoneId = (String) com.massivecraft.factions.Factions.class.getField("ID_SAFEZONE").get(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return get(this.safezoneId);
	}
	
	@Override
	public Set<Faction> getAllFactions() {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (com.massivecraft.factions.entity.Faction faction : BoardColl.get().getFactionToChunks().keySet()) {
			factions.add(Factions.getById(faction.getId()));
		}
		
		return factions;
	}
	
	// -------------------------------------------------- //
	// UTILS
	// -------------------------------------------------- //
	
	public static Rel convertRelationship(com.massivecraft.factions.Rel rel) {
		if (rel == com.massivecraft.factions.Rel.ALLY) return Rel.ALLY;
		if (rel == com.massivecraft.factions.Rel.ENEMY) return Rel.ENEMY;
		if (rel == com.massivecraft.factions.Rel.LEADER) return Rel.LEADER;
		if (rel == com.massivecraft.factions.Rel.MEMBER) return Rel.MEMBER;
		if (rel == com.massivecraft.factions.Rel.NEUTRAL) return Rel.NEUTRAL;
		if (rel == com.massivecraft.factions.Rel.OFFICER) return Rel.OFFICER;
		if (rel == com.massivecraft.factions.Rel.RECRUIT) return Rel.RECRUIT;
		if (rel == com.massivecraft.factions.Rel.TRUCE) return Rel.TRUCE;
		
		return null;
	}

}
