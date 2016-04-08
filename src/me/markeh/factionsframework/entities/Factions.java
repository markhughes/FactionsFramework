package me.markeh.factionsframework.entities;

import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Location;

import me.markeh.factionsframework.FactionsFramework;

public abstract class Factions {

	// -------------------------------------------------- //
	// STATIC GETTERS  
	// -------------------------------------------------- //

	public static Faction getFor(FPlayer player) {
		return FactionsFramework.get().getFactions().getForPlayer(player);
	}
	
	public static Faction getById(String id) {
		if (id == null) return null;
		
		return FactionsFramework.get().getFactions().get(id);
	}
	
	public static Faction getByName(String name, String universe) {
		return FactionsFramework.get().getFactions().getUsingName(name, universe);
	}
	
	public static Faction getNone() {
		return FactionsFramework.get().getFactions().getFactionNone();
	}
	
	public static Faction getFactionAt(Chunk chunk) {
		return FactionsFramework.get().getFactions().getAt(chunk);
	}
	
	public static Faction getFactionAt(Location location) {
		return getFactionAt(location.getChunk());
	}
	
	public static Set<Faction> getAll() {
		return FactionsFramework.get().getFactions().getAllFactions();
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS  
	// -------------------------------------------------- //
	
	public abstract Faction get(String id);
	public abstract Faction getUsingName(String id, String universe);
	public abstract Faction getAt(Chunk chunk);
	public abstract Faction getFactionNone();
	public abstract Set<Faction> getAllFactions();
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	public final Faction getForPlayer(FPlayer player) {
		return player.getFaction();
	}
	
}
