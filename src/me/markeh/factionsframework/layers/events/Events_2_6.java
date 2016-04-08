package me.markeh.factionsframework.layers.events;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSFormatHumanSpace;

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
import me.markeh.factionsframework.layers.EventsLayer;
import me.markeh.factionsframework.layers.factions.Factions_2_6;

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
	// CHUNKS CHANGE EVENTS 
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsChunksChangeClaim(com.massivecraft.factions.event.EventFactionsChunkChange event) {		
		com.massivecraft.factions.entity.Faction factionsFaction = com.massivecraft.factions.entity.BoardColls.get().getFactionAt(event.getChunk());
		Chunk chunk = event.getChunk().asBukkitChunk();
		
		// Grab all the information for the event 
		Set<Chunk> chunks = new TreeSet<Chunk>();
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
			
			if (event.getNewFaction() == Factions.getNone()) {
				this.handleNewUnclaims(this.getUSender(event), factionsFaction, eventFactionsChunksChange.getChunks());

			} else {
				this.handleNewClaims(this.getUSender(event), factionsFaction, eventFactionsChunksChange.getChunks());
			}
			
			return;
		}
		
		// Must only be one (only 1 called in this event) 
		if (this.getFinalChunk(chunks) != chunk) {
			if (event.getNewFaction() == Factions.getNone()) {
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
		this.doSetAt(Collections.singleton(ps), uplayer, FactionColls.get().get2(Factions.getNone().getId()));
		return true;
	}
	
	// Minimics an claim for a player (based off existing factions 1.6 code) 
	private Boolean mimicClaim(com.massivecraft.factions.entity.UPlayer uplayer, com.massivecraft.factions.entity.Faction faction, PS ps) {
		doSetAt(Collections.singleton(ps), uplayer, faction);
		return true;
	}
	
	// Based off original code 
	private void doSetAt(Set<PS> chunks, UPlayer player, com.massivecraft.factions.entity.Faction newFaction) {
		String formatOne = "<h>%s<i> %s <h>%d <i>chunk %s<i>.";
		String formatMany = "<h>%s<i> %s <h>%d <i>chunks near %s<i>.";
		
		HashMap<com.massivecraft.factions.entity.Faction, Set<PS>> oldFactionChunks = new HashMap<com.massivecraft.factions.entity.Faction, Set<PS>>();
		
		for (PS chunk : chunks) {
			com.massivecraft.factions.entity.Faction factionHere = BoardColls.get().getFactionAt(chunk);
			Set<PS> claims = new HashSet<PS>();
			
			if (oldFactionChunks.containsKey(factionHere)) {
				claims = oldFactionChunks.get(factionHere);
				claims.add(chunk);
			}
			
			oldFactionChunks.put(factionHere, claims);			
			BoardColls.get().setFactionAt(chunk, newFaction);
		}
		
		// Inform
		for (Entry<com.massivecraft.factions.entity.Faction, Set<PS>> entry : oldFactionChunks.entrySet()) {
			final com.massivecraft.factions.entity.Faction oldFaction = entry.getKey();
			final Set<PS> oldChunks = entry.getValue();
			final PS oldChunk = oldChunks.iterator().next();
			final Set<UPlayer> informees = Factions_2_6.getClaimInformees(player, oldFaction, newFaction, this);
			final EventFactionsChunkChangeType type = EventFactionsChunkChangeType.get(oldFaction, newFaction, player.getFaction());
			
			String chunkString = oldChunk.toString(PSFormatHumanSpace.get());
			String typeString = type.past;
			
			for (UPlayer informee : informees) {
				informee.msg((oldChunks.size() == 1 ? formatOne : formatMany), player.describeTo(informee, true), typeString, oldChunks.size(), chunkString);
				informee.msg("  <h>%s<i> --> <h>%s", oldFaction.describeTo(informee, true), newFaction.describeTo(informee, true));
			}
		}
	}
	
	// Grabs the last chunk in a set 
	private Chunk getFinalChunk(Set<Chunk> chunks) {
		Chunk finalChunk = null;
		for (Chunk oldChunk : chunks) finalChunk = oldChunk;
		
		return finalChunk;
	}
	
}
