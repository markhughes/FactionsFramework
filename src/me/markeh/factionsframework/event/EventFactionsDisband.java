package me.markeh.factionsframework.event;

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
}
