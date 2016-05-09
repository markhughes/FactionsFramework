package me.markeh.factionsframework.layer.layer_2_8_7;

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

public class Factions_2_8_7 extends Factions {

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
			Faction_2_8_7 faction = new Faction_2_8_7(id);
			this.factionsMap.put(id, faction);
		}
		
		if ( ! this.factionsMap.get(id).isValid()) {
			this.factionsMap.remove(id);
			return null;
		}
		
		return this.factionsMap.get(id);
	}

	@Override
	public Faction getUsingName(String id, String universe) {
		com.massivecraft.factions.entity.Faction faction = FactionColl.get().getByName(id);
		if (faction == null) return null;
		
		return this.get(faction.getId());
	}
	
	@Override
	public Faction getAt(Chunk chunk) {
		return (this.get(BoardColl.get().getFactionAt(PS.valueOf(chunk)).getId()));
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
	
}
