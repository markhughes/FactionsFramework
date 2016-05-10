package me.markeh.factionsframework.layer.layer_2_6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.IdUtil;

import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;

public class Factions_2_6 extends Factions {

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
			Faction_2_6 faction = new Faction_2_6(id);
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
		for (com.massivecraft.factions.entity.Faction faction : FactionColls.get().get(universe).getAll()) {
			if ( ! faction.getName().equalsIgnoreCase(name)) continue;
			
			return get(faction.getId());
		}
		
		return null;
	}
	
	@Override
	public Faction getAt(Chunk chunk) {
		com.massivecraft.factions.entity.Faction mfaction = BoardColls.get().getFactionAt(PS.valueOf(chunk));
		
		if (mfaction == null) return null;
				
		return get(mfaction.getId());
	}

	@Override
	public Faction getFactionNone(World world) {
		if (this.noneId == null) {
			try {
				this.noneId = FactionColls.get().getForWorld(world.getName()).getNone().getId();
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
				this.warzoneId = FactionColls.get().getForWorld(world.getName()).getWarzone().getId();
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
				this.safezoneId = FactionColls.get().getForWorld(world.getName()).getSafezone().getId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return get(this.safezoneId);
	}
	
	@Override
	public Set<Faction> getAllFactions() {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (FactionColl coll: FactionColls.get().getColls()) {
			for (com.massivecraft.factions.entity.Faction faction : coll.getAll()) {
				factions.add(Factions.getById(faction.getId()));
			}
		}
		
		return factions;
	}
	
	// -------------------------------------------------- //
	// UTIL METHODS 
	// -------------------------------------------------- //

	public static Set<UPlayer> getClaimInformees(UPlayer usender, com.massivecraft.factions.entity.Faction oldFaction, com.massivecraft.factions.entity.Faction newFaction, Object clazz) {
		Set<UPlayer> ret = new HashSet<UPlayer>();
		
		if (usender != null) ret.add(usender);
		
		for (com.massivecraft.factions.entity.Faction faction : com.massivecraft.factions.entity.FactionColls.get().get2(usender).getColl().getAll()) {
			if (faction == null) continue;
			if (faction.isNone()) continue;
			ret.addAll(getUPlayersIn(faction, clazz));
		}
		
		if (MConf.get().logLandClaims) {
			ret.add(UPlayer.get(IdUtil.getConsole()));
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<UPlayer> getUPlayersIn(com.massivecraft.factions.entity.Faction faction, Object clazz) {
		try { 
			List<UPlayer> uplayers = (List<UPlayer>) faction.getClass().getMethod("getUPlayers").invoke(clazz);
			return uplayers;
		} catch(Exception e) { }
		
		return new ArrayList<UPlayer>();
	}
	
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
