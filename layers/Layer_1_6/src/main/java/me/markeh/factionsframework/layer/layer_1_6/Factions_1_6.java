package me.markeh.factionsframework.layer.layer_1_6;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.struct.Relation;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;

public class Factions_1_6 extends Factions {
	
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
			Faction_1_6 faction = new Faction_1_6(id);
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
		return this.get(com.massivecraft.factions.Factions.getInstance().getByTag(name).getId());
	}

	@Override
	public Faction getAt(Chunk chunk) {
		FLocation flocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
		String id = Board.getInstance().getIdAt(flocation);
		
		if (id == null) return null;
		
		return get(id);
	}

	@Override
	public Faction getFactionNone(World world) {
		if (this.noneId == null) this.noneId = com.massivecraft.factions.Factions.getInstance().getWilderness().getId();
		
		return this.get(this.noneId);
	}
	
	@Override
	public Faction getFactionWarZone(World world) {
		if (this.warzoneId == null) this.warzoneId = com.massivecraft.factions.Factions.getInstance().getWarZone().getId();
		
		return this.get(this.warzoneId);
	}


	@Override
	public Faction getFactionSafeZone(World world) {
		if (this.safezoneId == null) this.safezoneId = com.massivecraft.factions.Factions.getInstance().getSafeZone().getId();
		
		return this.get(this.warzoneId);
	}
	
	@Override
	public Set<Faction> getAllFactions() {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (com.massivecraft.factions.Faction faction : com.massivecraft.factions.Factions.getInstance().getAllFactions()) {
			factions.add(Factions.getById(faction.getId()));
		}
		
		return factions;
	}
	
	// -------------------------------------------------- //
	// UTILS
	// -------------------------------------------------- //
	
	// Convert to our relation type
	public static Rel convertRelationship(Relation relation) {
		if (relation == Relation.ALLY) return Rel.ALLY;
		if (relation == Relation.ENEMY) return Rel.ENEMY;
		if (relation == Relation.MEMBER) return Rel.MEMBER;
		if (relation == Relation.TRUCE) return Rel.TRUCE;
		
		return null;
	}
	
}
