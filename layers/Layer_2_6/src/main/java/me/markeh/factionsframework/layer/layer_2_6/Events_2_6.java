package me.markeh.factionsframework.layer.layer_2_6;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.massivecore.ps.PS;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.LeaveReason;
import me.markeh.factionsframework.event.EventFactionsChunksChange;
import me.markeh.factionsframework.event.EventFactionsCreate;
import me.markeh.factionsframework.event.EventFactionsDisband;
import me.markeh.factionsframework.event.EventFactionsJoin;
import me.markeh.factionsframework.event.EventFactionsLeave;
import me.markeh.factionsframework.event.EventFactionsRename;
import me.markeh.factionsframework.layer.EventsLayer;

public class Events_2_6 extends EventsLayer {

	// -------------------------------------------------- //
	// JOIN EVENTS
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsJoin(com.massivecraft.factions.event.EventFactionsMembershipChange event) {
		if (event.getReason() != MembershipChangeReason.JOIN) return;
		
		Faction faction = Factions.getById(event.getNewFaction().getId());
		FPlayer fplayer = null;
		
		try {
			fplayer = FPlayers.getById(this.getUSender(event).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		EventFactionsJoin eventEventFactionsJoin = new EventFactionsJoin(faction, fplayer);
		eventEventFactionsJoin.setCancelled(event.isCancelled());
		eventEventFactionsJoin.call();
		
		event.setCancelled(eventEventFactionsJoin.isCancelled());
	}
	
	// -------------------------------------------------- //
	// CREATE EVENTS 
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsJoin(com.massivecraft.factions.event.EventFactionsCreate event) {
		String name = event.getEventName();
		FPlayer fplayer = null;
		
		try {
			fplayer = FPlayers.getById(this.getUSender(event).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EventFactionsCreate eventFactionsCreate = new EventFactionsCreate(name, fplayer);
		eventFactionsCreate.setCancelled(event.isCancelled());
		eventFactionsCreate.call();
		
		event.setCancelled(eventFactionsCreate.isCancelled());
	}
	
	
	// -------------------------------------------------- //
	// RENAME EVENTS 
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsRename(com.massivecraft.factions.event.EventFactionsNameChange event) {
		FPlayer fplayer = null;
		
		try {
			fplayer = FPlayers.getById(this.getUSender(event).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EventFactionsRename eventFactionsRename = new EventFactionsRename(Factions.getById(event.getFaction().getId()), event.getNewName(), fplayer);
		eventFactionsRename.setCancelled(event.isCancelled());
		eventFactionsRename.call();
		
		event.setCancelled(eventFactionsRename.isCancelled());
	}
	
	// -------------------------------------------------- //
	// CHUNKS CHANGE EVENTS 
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsChunksChangeClaim(com.massivecraft.factions.event.EventFactionsChunkChange event) {		
		com.massivecraft.factions.entity.Faction factionsFaction = com.massivecraft.factions.entity.BoardColls.get().getFactionAt(event.getChunk());
		Chunk chunk = event.getChunk().asBukkitChunk();
		
		// Grab all the information for the event 
		Set<Chunk> chunks = new HashSet<Chunk>();
		chunks.add(chunk);
		
		Faction newFaction = Factions.getById(event.getNewFaction().getId());
		
		FPlayer fplayer = FPlayers.getById(this.getUSender(event).getId());
		
		// Create and call our event 
		EventFactionsChunksChange eventFactionsChunksChange = new EventFactionsChunksChange(newFaction, fplayer, chunks);
		eventFactionsChunksChange.setCancelled(event.isCancelled());
		eventFactionsChunksChange.call();
		
		// Update event information
		event.setCancelled(eventFactionsChunksChange.isCancelled());
		
		// Check the chunks 
		if (eventFactionsChunksChange.getChunks().size() == 0) {
			// If all chunks have been removed from the set, cancel the event 
			event.setCancelled(true);
			return;
		}
		
		if (eventFactionsChunksChange.getChunks().size() != chunks.size()) {
			// Chunks have been removed/added so we'll redo this  
			event.setCancelled(true);
			
			if (event.getNewFaction().isNone()) {
				this.handleNewUnclaims(this.getUSender(event), factionsFaction, eventFactionsChunksChange.getChunks());

			} else {
				this.handleNewClaims(this.getUSender(event), factionsFaction, eventFactionsChunksChange.getChunks());
			}
			
			return;
		}
		
		// Must only be one (only 1 called in this event) 
		if (this.getFinalChunk(chunks) != chunk) {
			if (event.getNewFaction().isNone()) {
				this.handleNewUnclaims(this.getUSender(event), factionsFaction, eventFactionsChunksChange.getChunks());
			} else {
				this.handleNewClaims(this.getUSender(event), factionsFaction, eventFactionsChunksChange.getChunks());
			}

		}
	}
	
	// -------------------------------------------------- //
	// DISBAND EVENTS
	// -------------------------------------------------- //
	
	public void onEventFactionsDisband(com.massivecraft.factions.event.EventFactionsDisband event) {
		Faction faction = Factions.getById(event.getFactionId());
		
		EventFactionsDisband eventFactionsDisband = new EventFactionsDisband(faction);
		eventFactionsDisband.setCancelled(event.isCancelled());
		eventFactionsDisband.call();
		
		event.setCancelled(eventFactionsDisband.isCancelled());
	}
	
	// -------------------------------------------------- //
	// LEAVE EVENTS
	// -------------------------------------------------- //
	
	public void onEventFactionsLeave(com.massivecraft.factions.event.EventFactionsMembershipChange event) {
		if (event.getReason() != MembershipChangeReason.KICK && event.getReason() != MembershipChangeReason.DISBAND && event.getReason() != MembershipChangeReason.LEAVE) return;
		
		Faction faction = Factions.getById(this.getUSender(event).getFactionId());
		FPlayer fplayer = FPlayers.getById(this.getUSender(event).getId());
		
		LeaveReason reason = LeaveReason.Leave;
		Boolean canCancel = true;
		if (event.getReason() == MembershipChangeReason.KICK) reason = LeaveReason.Kicked;
		if (event.getReason() == MembershipChangeReason.DISBAND) {
			reason = LeaveReason.Disband;
			canCancel = false;
		}
		
		EventFactionsLeave eventEventFactionsLeave = new EventFactionsLeave(faction, fplayer, reason, canCancel);
		if (canCancel) eventEventFactionsLeave.setCancelled(event.isCancelled());
		eventEventFactionsLeave.call();
		
		if (canCancel) event.setCancelled(eventEventFactionsLeave.isCancelled());
	}
	
	// -------------------------------------------------- //
	// UTIL METHODS 
	// -------------------------------------------------- //

	// Call the getUSender method on an event
	private UPlayer getUSender(Event event) {
		UPlayer uplayer = null;
		
		try {
			uplayer = (UPlayer) event.getClass().getMethod("getUSender").invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return uplayer;
	}
	
	// Handles a set of unclaims 
	private void handleNewUnclaims(com.massivecraft.factions.entity.UPlayer uplayer, com.massivecraft.factions.entity.Faction faction, Set<Chunk> chunks) {
		for (Chunk chunk : chunks) {
			if ( ! this.mimicUnclaim(uplayer, faction, PS.valueOf(chunk))) return;
		}
	}
	
	// Handles a set of claims
	private void handleNewClaims(com.massivecraft.factions.entity.UPlayer uplayer, com.massivecraft.factions.entity.Faction faction, Set<Chunk> chunks) {		
		for (Chunk chunk : chunks) {
			if ( ! this.mimicClaim(uplayer, faction, PS.valueOf(chunk))) break;
		}
	}
	
	// Mimic an unclaim for a player 
	private Boolean mimicUnclaim(com.massivecraft.factions.entity.UPlayer uplayer, com.massivecraft.factions.entity.Faction faction, PS ps) {
		this.doSetAt(Collections.singleton(ps), uplayer, FactionColls.get().get2(Factions.getNone(ps.asBukkitWorld()).getId()));
		return true;
	}
	
	// Minimics an claim for a player (based off existing factions 2.6 code) 
	private Boolean mimicClaim(com.massivecraft.factions.entity.UPlayer uplayer, com.massivecraft.factions.entity.Faction faction, PS ps) {
		doSetAt(Collections.singleton(ps), uplayer, faction);
		return true;
	}
	
	private void doSetAt(Set<PS> chunks, UPlayer player, com.massivecraft.factions.entity.Faction newFaction) {
		for (PS chunk : chunks) {
			player.tryClaim(newFaction, chunk, true, true);
		}
	}
	
	// Grabs the last chunk in a set 
	private Chunk getFinalChunk(Set<Chunk> chunks) {
		Chunk finalChunk = null;
		for (Chunk oldChunk : chunks) finalChunk = oldChunk;
		
		return finalChunk;
	}
	
}
