package me.markeh.factionsframework.event;

import org.bukkit.event.HandlerList;

import me.markeh.factionsframework.entities.Faction;

public class EventFactionsDisband extends BaseEventFactions<EventFactionsDisband> {

	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public EventFactionsDisband(Faction faction) {
		this.faction = faction;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private final Faction faction;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public final Faction getFaction() {
		return this.faction;
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
