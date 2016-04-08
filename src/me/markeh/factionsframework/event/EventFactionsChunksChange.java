package me.markeh.factionsframework.event;

import java.util.Set;

import org.bukkit.Chunk;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;

public class EventFactionsChunksChange extends BaseEventFactions<EventFactionsChunksChange> {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public EventFactionsChunksChange(Faction newFaction, FPlayer fplayer, Set<Chunk> chunks) {
		this.newFaction = newFaction;
		this.fplayer = fplayer;
		this.chunks = chunks;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private Faction newFaction;
	private final FPlayer fplayer;
	private Set<Chunk> chunks;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //

	public final Faction getNewFaction() {
		return this.newFaction;
	}
	
	public final FPlayer getFPlayer() {
		return this.fplayer;
	}
	
	public final Set<Chunk> getChunks() {
		return this.chunks;
	}
	
	public final void setChunks(Set<Chunk> chunks) {
		this.chunks = chunks;
	}
	
}
