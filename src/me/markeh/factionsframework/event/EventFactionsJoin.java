package me.markeh.factionsframework.event;

import org.bukkit.event.HandlerList;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;

public class EventFactionsJoin extends BaseEventFactions<EventFactionsJoin> {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public EventFactionsJoin(Faction faction, FPlayer fplayer) {
		this.faction = faction;
		this.fplayer = fplayer;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private final Faction faction;
	private final FPlayer fplayer;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //

	public final Faction getFaction() {
		return this.faction;
	}
	
	public final FPlayer getFPlayer() {
		return this.fplayer;
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
