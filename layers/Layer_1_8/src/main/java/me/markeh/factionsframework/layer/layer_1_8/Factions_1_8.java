package me.markeh.factionsframework.layer.layer_1_8;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;

public class Factions_1_8  extends Factions {
	
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
			Faction_1_8 faction = new Faction_1_8(id);
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
		return this.get(com.massivecraft.factions.Factions.i.getByTag(name).getId());
	}

	@Override
	public Faction getAt(Chunk chunk) {
		FLocation flocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
		String id = Board.getIdAt(flocation);
		
		if (id == null) return null;
		
		return get(id);
	}

	@Override
	public Faction getFactionNone(World world) {
		if (this.noneId == null) this.noneId = com.massivecraft.factions.Factions.i.getNone().getId();
		
		return this.get(this.noneId);
	}
	
	@Override
	public Faction getFactionWarZone(World world) {
		if (this.warzoneId == null) this.warzoneId = com.massivecraft.factions.Factions.i.get("-2").getId();
		
		return this.get(this.warzoneId);
	}


	@Override
	public Faction getFactionSafeZone(World world) {
		if (this.safezoneId == null) this.safezoneId = com.massivecraft.factions.Factions.i.get("-1").getId();
		
		return this.get(this.warzoneId);
	}
	
	@Override
	public Set<Faction> getAllFactions() {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (com.massivecraft.factions.Faction faction : com.massivecraft.factions.Factions.i.get()) {
			factions.add(Factions.getById(faction.getId()));
		}
		
		return factions;
	}
	
	// -------------------------------------------------- //
	// UTILS
	// -------------------------------------------------- //
	
	// Convert to our relation type
	public static Rel convertRelationship(com.massivecraft.factions.struct.Rel relation) {
		if (relation == com.massivecraft.factions.struct.Rel.ALLY) return Rel.ALLY;
		if (relation == com.massivecraft.factions.struct.Rel.ENEMY) return Rel.ENEMY;
		if (relation == com.massivecraft.factions.struct.Rel.MEMBER) return Rel.MEMBER;
		if (relation == com.massivecraft.factions.struct.Rel.TRUCE) return Rel.TRUCE;
		if (relation == com.massivecraft.factions.struct.Rel.NEUTRAL) return Rel.NEUTRAL;
		
		if (relation == com.massivecraft.factions.struct.Rel.LEADER) return Rel.LEADER;
		if (relation == com.massivecraft.factions.struct.Rel.OFFICER) return Rel.OFFICER;
		if (relation == com.massivecraft.factions.struct.Rel.MEMBER) return Rel.MEMBER;
		if (relation == com.massivecraft.factions.struct.Rel.RECRUIT) return Rel.RECRUIT;
		
		return null;
	}
	
}