package me.markeh.factionsframework.event;

import org.bukkit.event.HandlerList;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.enums.LeaveReason;

public class EventFactionsLeave extends BaseEventFactions<EventFactionsLeave> {

	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public EventFactionsLeave(Faction faction, FPlayer fplayer, LeaveReason reason, Boolean isCancellable) {
		this.faction = faction;
		this.fplayer = fplayer;
		this.reason = reason;
		this.isCancellable = isCancellable;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private final Faction faction;
	private final FPlayer fplayer;
	private final LeaveReason reason;
	private final Boolean isCancellable;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public final Faction getFaction() {
		return this.faction;
	}
	
	public final FPlayer getFPlayer() {
		return this.fplayer;
	}
	
	public final LeaveReason getReason() {
		return this.reason;
	}
	
	public final Boolean canCancel() {
		return this.isCancellable;
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
