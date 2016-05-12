package me.markeh.factionsframework.entities;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import me.markeh.factionsframework.deprecation.Deprecation;
import me.markeh.factionsframework.enums.FactionsVersion;

public abstract class Factions implements Handler {

	// -------------------------------------------------- //
	// STATIC GETTERS  
	// -------------------------------------------------- //

	/**
	 * Get a Faction for a player
	 * @param player
	 * @return faction for that player 
	 */
	public static Faction getFor(FPlayer player) {
		return ((Factions) getHandler()).getForPlayer(player);
	}
	
	/**
	 * Get a Faction by it's id
	 * @param id
	 * @return faction with that id, or null otherwise 
	 */
	public static Faction getById(String id) {
		if (id == null) return null;
		
		return ((Factions) getHandler()).get(id);
	}
	
	/**
	 * Get a Faction by it's name/tag
	 * @param name
	 * @param universe
	 * @return faction of that name, or null otherwise 
	 */
	public static Faction getByName(String name, String universe) {
		return ((Factions) getHandler()).getUsingName(name, universe);
	}
	
	/**
	 * Get Faction none (old)
	 * 
	 * @deprecated to be removed on 27/11/2016
	 * We need to support the universe system, so we will required passing
	 * a world to the method
	 */
	@Deprecated
	public static Faction getNone() {
		Deprecation.showDeprecationWarningForMethod("Factions#getNone");
		return ((Factions) getHandler()).getFactionNone(Bukkit.getWorlds().get(0));
	}
	
	/**
	 * Get Wilderness Faction 
	 * @param world 
	 * @return faction none for that world
	 */
	public static Faction getNone(World world) {
		return ((Factions) getHandler()).getFactionNone(world);
	}
	
	/**
	 * Get WarZone Faction
	 * @param world 
	 * @return faction none for that world
	 */
	public static Faction getWarZone(World world) {
		return ((Factions) getHandler()).getFactionWarZone(world);
	}
	
	/**
	 * Get WarZone Faction
	 * @param world 
	 * @return faction none for that world
	 */
	public static Faction getSafeZone(World world) {
		return ((Factions) getHandler()).getFactionSafeZone(world);
	}
	
	/**
	 * Get Faction at a Chunk
	 * @param chunk
	 * @return faction at location
	 */
	public static Faction getFactionAt(Chunk chunk) {
		return ((Factions) getHandler()).getAt(chunk);
	}
	
	/**
	 * Get Faction at a Location
	 * @param location
	 * @return faction at location
	 */
	public static Faction getFactionAt(Location location) {
		return getFactionAt(location.getChunk());
	}
	
	/**
	 * Get all Factions
	 * @return set of factions
	 */
	public static Set<Faction> getAll() {
		return ((Factions) getHandler()).getAllFactions();
	}
	
	private static Handler factionsInstance = null;
	public static Handler getHandler() {
		if (factionsInstance == null) {
			try {
				switch (FactionsVersion.get()) {
					case Factions_1_6 :
						factionsInstance = (Factions) Class.forName("me.markeh.factionsframework.layer.layer_1_6.Factions_1_6").newInstance();
						break;
					case Factions_1_8 :
						factionsInstance = (Factions) Class.forName("me.markeh.factionsframework.layer.layer_1_8.Factions_1_8").newInstance();
						break;
					case Factions_2_6 :
						factionsInstance = (Factions) Class.forName("me.markeh.factionsframework.layer.layer_2_6.Factions_2_6").newInstance();
						break;
					case Factions_2_7:
					case Factions_2_8_2 :
					case Factions_2_8_6 :
					case Factions_2_8_7 :
					case Factions_2_8_8 :
					default :
						factionsInstance = (Factions) Class.forName("me.markeh.factionsframework.layer.layer_2_8_6.Factions_2_8_6").newInstance();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return factionsInstance;
	}
	
	// -------------------------------------------------- //
	// ABSTRACT METHODS  
	// -------------------------------------------------- //
	
	public abstract Faction get(String id);
	public abstract Faction getUsingName(String id, String universe);
	public abstract Faction getAt(Chunk chunk);
	public abstract Faction getFactionNone(World world);
	public abstract Faction getFactionWarZone(World world);
	public abstract Faction getFactionSafeZone(World world);
	
	public abstract Set<Faction> getAllFactions();
	
	// -------------------------------------------------- //
	// METHODS  
	// -------------------------------------------------- //
	
	public final Faction getForPlayer(FPlayer player) {
		return player.getFaction();
	}
	
	public Handler asHandler() {
		return (Handler) this;
	}
	
}
