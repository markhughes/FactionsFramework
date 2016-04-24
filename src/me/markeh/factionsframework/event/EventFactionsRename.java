package me.markeh.factionsframework.event;

import org.bukkit.event.HandlerList;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;

public class EventFactionsRename extends BaseEventFactions<EventFactionsRename> {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public EventFactionsRename(Faction faction, String newName, FPlayer fplayer) {
		this.faction = faction;
		this.fplayer = fplayer;
		this.newName = newName;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private final Faction faction;
	private final FPlayer fplayer;
	private final String newName;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //

	public final Faction getFaction() {
		return this.faction;
	}
	
	public final FPlayer getFPlayer() {
		return this.fplayer;
	}
	
	public final String getNewName() {
		return this.newName;
	}
	
	// -------------------------------------------------- //
	// HANDLERLIST
	// -------------------------------------------------- //
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
