package me.markeh.factionsframework.layer.layer_1_8;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.P;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent.PlayerLeaveReason;
import com.massivecraft.factions.iface.EconomyParticipator;
import com.massivecraft.factions.integration.Econ;

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

public class Events_1_8  extends EventsLayer {
	
	// -------------------------------------------------- //
	// JOIN EVENTS
	// -------------------------------------------------- //

	@EventHandler
	public void onEventFactionsJoin(com.massivecraft.factions.event.FPlayerJoinEvent event) {
		EventFactionsJoin eventFactionsJoin = new EventFactionsJoin(Factions.getById(event.getFaction().getId()), FPlayers.getById(event.getFPlayer().getId()));
		eventFactionsJoin.setCancelled(event.isCancelled());
		eventFactionsJoin.call();
		
		event.setCancelled(eventFactionsJoin.isCancelled());
	}
	
	// -------------------------------------------------- //
	// CREATE EVENTS 
	// -------------------------------------------------- //

	@EventHandler
	public void onEventFactionsCreate(com.massivecraft.factions.event.FactionCreateEvent event) {
		EventFactionsCreate eventFactionsCreate = new EventFactionsCreate(event.getFactionTag(), FPlayers.getById(event.getFPlayer().getId()));
		eventFactionsCreate.setCancelled(event.isCancelled());
		eventFactionsCreate.call();
		
		event.setCancelled(eventFactionsCreate.isCancelled());
	}
	
	// -------------------------------------------------- //
	// RENAME EVENTS
	// -------------------------------------------------- //

	@EventHandler
	public void onEventFactionsRename(com.massivecraft.factions.event.FactionRenameEvent event) {
		EventFactionsRename eventFactionsRename = new EventFactionsRename(Factions.getById(event.getFaction().getId()), event.getFactionTag(), FPlayers.getById(event.getFPlayer().getId()));
		eventFactionsRename.setCancelled(event.isCancelled());
		eventFactionsRename.call();
		
		event.setCancelled(eventFactionsRename.isCancelled());
	}
	
	// -------------------------------------------------- //
	// CHUNKS CHANGE EVENTS 
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsChunksChange(LandClaimEvent event) {
		// Grab all the information for the event 
		FLocation flocation = event.getLocation();
		
		Chunk chunk = flocation.getWorld().getChunkAt(Math.toIntExact(flocation.getX()), Math.toIntExact(flocation.getZ()));
		
		Set<Chunk> chunks = new TreeSet<Chunk>();
		chunks.add(chunk);
		
		Faction newFaction = Factions.getById(event.getFaction().getId());
		
		FPlayer fplayer = FPlayers.getById(event.getFPlayer().getId());
		
		// Create and call our event 
		EventFactionsChunksChange eventFactionsChunksChange = new EventFactionsChunksChange(newFaction, fplayer, chunks);
		eventFactionsChunksChange.setCancelled(event.isCancelled());
		eventFactionsChunksChange.call();
		
		// Update event information
		event.setCancelled(eventFactionsChunksChange.isCancelled());
		
		// If all the chunks have been removed, cancel the event 
		if (eventFactionsChunksChange.getChunks().size() == 0) {
			event.setCancelled(true);
			return;
		}
		
		// If there are more than 1 or the only chunk there is not the existing chunk
		if (eventFactionsChunksChange.getChunks().size() > 1 || this.getFinalChunk(eventFactionsChunksChange.getChunks()) != chunk) {
			event.setCancelled(true);
			this.handleNewClaims(event.getFPlayer(), event.getFaction(), eventFactionsChunksChange.getChunks());
			return;
		}
	}
	
	@EventHandler
	public void onEventFactionsChunksChange(LandUnclaimEvent event) {
		// Prepare all our variables for the event 
		Set<Chunk> chunks = new TreeSet<Chunk>();
		Chunk chunk = event.getLocation().getWorld().getChunkAt(Math.toIntExact(event.getLocation().getX()), Math.toIntExact(event.getLocation().getZ()));
		chunks.add(chunk);
		
		Faction newFaction = Factions.getNone(event.getLocation().getWorld());
		
		FPlayer fplayer = FPlayers.getById(event.getFPlayer().getId());
		
		// Create and call our event 
		EventFactionsChunksChange eventFactionsChunksChange = new EventFactionsChunksChange(newFaction, fplayer, chunks);
		eventFactionsChunksChange.setCancelled(event.isCancelled());
		eventFactionsChunksChange.call();
		
		// Update event information
		event.setCancelled(eventFactionsChunksChange.isCancelled());

		// If all the chunks have been removed, cancel the event 
		if (eventFactionsChunksChange.getChunks().size() == 0) {
			event.setCancelled(true);
			return;
		}
		
		// If there are more than 1 or the only chunk there is not the existing chunk
		if (eventFactionsChunksChange.getChunks().size() > 1 || this.getFinalChunk(eventFactionsChunksChange.getChunks()) != chunk) {
			event.setCancelled(true);
			this.handleNewUnclaims(event.getFPlayer(), event.getFaction(), eventFactionsChunksChange.getChunks());
			return;
		}

	}
	
	@EventHandler
	public void onEventFactionsChunksChange(LandUnclaimAllEvent event) {
		// Grab all the information for the event 
		Set<FLocation> flocations = event.getFaction().getClaims();
		Set<Chunk> chunks = new TreeSet<Chunk>();
		
		World world = null;
		
		for (FLocation flocation : flocations) {
			Chunk chunk = flocation.getWorld().getChunkAt(Math.toIntExact(flocation.getX()), Math.toIntExact(flocation.getZ()));
			chunks.add(chunk);
			
			if (world == null) world = flocation.getWorld();
		}
		
		Faction newFaction = Factions.getNone(world);
		
		FPlayer fplayer = FPlayers.getById(event.getFPlayer().getId());
		
		// Create and call our event 
		EventFactionsChunksChange eventFactionsChunksChange = new EventFactionsChunksChange(newFaction, fplayer, chunks);
		eventFactionsChunksChange.call();
		
		// Update event information
		// event.setCancelled(eventFactionsChunksChange.isCancelled()); // TODO: can't cancel
		
		// If all the chunks have been removed, cancel the event 
		if (eventFactionsChunksChange.getChunks().size() == 0) {
			//event.setCancelled(true); // TODO: can't cancel
			return;
		}
		
		Boolean doChange = false;
			
		if (eventFactionsChunksChange.getChunks().size() != chunks.size()) {
			// Size is different, set as a new change
			doChange = true;
		} else {				
			for (Chunk chunk : eventFactionsChunksChange.getChunks()) {
				// Look over the chunks and check if they've changed 
				if (chunks.contains(chunk)) continue;
				doChange = true;
				break;
			}
		}
			
		if ( ! doChange) return; // no changes at all here  
		
		// event.setCancelled(true); // TODO: can't cancel
		
		this.handleNewUnclaims(event.getFPlayer(), event.getFaction(), chunks);
	}
	
	// -------------------------------------------------- //
	// DISBAND EVENT
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsDisband(FactionDisbandEvent event) {
		Faction faction = Factions.getById(event.getFaction().getId());
		
		EventFactionsDisband eventEventFactionsDisband = new EventFactionsDisband(faction);
		eventEventFactionsDisband.setCancelled(event.isCancelled());
		eventEventFactionsDisband.call();
		
		event.setCancelled(eventEventFactionsDisband.isCancelled());
	}
	
	// -------------------------------------------------- //
	// LEAVE EVENT
	// -------------------------------------------------- //
	
	@EventHandler
	public void onEventFactionsLeave(FPlayerLeaveEvent event) {
		FPlayer fplayer = FPlayers.getById(event.getFPlayer().getId());
		Faction faction = Factions.getById(event.getFaction().getId());
		
		LeaveReason reason = LeaveReason.Leave;
		Boolean canCancel = true;
		
		if (event.getReason() == PlayerLeaveReason.KICKED) reason = LeaveReason.Kicked;
		if (event.getReason() == PlayerLeaveReason.DISBAND) {
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
	
	// Handle a set of unclaims 
	private void handleNewUnclaims(com.massivecraft.factions.FPlayer fplayer, com.massivecraft.factions.Faction faction, Set<Chunk> chunks) {
		for (Chunk chunk : chunks) {
			FLocation flocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
			if( ! this.mimicUnclaim(fplayer, faction, flocation)) return;
		}
	}
	
	// Mimic an unclaim for a player  (based off existing factions 1.6 code)  
	private Boolean mimicUnclaim(com.massivecraft.factions.FPlayer fplayer, com.massivecraft.factions.Faction faction, FLocation flocation) {
		if (Econ.shouldBeUsed()) {
			double refund = Econ.calculateClaimRefund(faction.getLandRounded());
			
			if (Conf.bankEnabled && Conf.bankFactionPaysLandCosts) {
				if ( ! Econ.modifyMoney(faction, refund, "to unclaim this land", "for unclaiming this land")) return false;
			} else {
				if ( ! Econ.modifyMoney(fplayer, refund, "to unclaim this land", "for unclaiming this land")) return false;
			}
		}
		
		Board.removeAt(flocation);
		
		faction.msg("%s<i> unclaimed some land.", fplayer.describeTo(faction, true));

		// Log if factions wants us to 
		if (Conf.logLandUnclaims) {
			P.p.log(fplayer.getName()+" unclaimed land at ("+flocation.getCoordString()+") from the faction: "+faction.getTag());
		}
		
		return true;
	}
	
	// Hands a set of claims 
	private void handleNewClaims(com.massivecraft.factions.FPlayer fplayer, com.massivecraft.factions.Faction faction, Set<Chunk> chunks) {		
		for (Chunk chunk : chunks) {
			FLocation flocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
			if ( ! this.mimicClaim(fplayer, faction, flocation)) break;
		}
	}
	
	// Minimics an claim for a player (based off existing factions 1.6 code) 
	private Boolean mimicClaim(com.massivecraft.factions.FPlayer fplayer, com.massivecraft.factions.Faction faction, FLocation flocation) {
		
		boolean mustPay = Econ.shouldBeUsed() && ! fplayer.hasAdminMode() && faction.getId() != Factions.getSafeZone(flocation.getWorld()).getId() && faction.getId() != Factions.getWarZone(flocation.getWorld()).getId();
		double cost = 0.0;
		EconomyParticipator payee = null;
		
		if (mustPay) {
			cost = Econ.calculateClaimCost(faction.getLandRounded(), faction.isNormal());
			
			if (Conf.econClaimUnconnectedFee != 0.0 && faction.getLandRoundedInWorld(flocation.getWorldName()) > 0 && !Board.isConnectedLocation(flocation, faction)) {
				cost += Conf.econClaimUnconnectedFee;
			}
			
			if (Conf.bankEnabled && Conf.bankFactionPaysLandCosts && fplayer.hasFaction()) {
				payee = faction;
			} else {
				payee = fplayer;
			}
			
			if ( ! Econ.hasAtLeast(payee, cost, "to claim this land")) return false;
		}
		
		// then make 'em pay (if applicable)
		if (mustPay && ! Econ.modifyMoney(payee, -cost, "to claim this land", "for claiming this land")) return false;
		
		// announce success
		Set<com.massivecraft.factions.FPlayer> informTheseFPlayers = new HashSet<com.massivecraft.factions.FPlayer>();
		informTheseFPlayers.add(fplayer);
		informTheseFPlayers.addAll(faction.getFPlayersWhereOnline(true));
		
		for (com.massivecraft.factions.FPlayer fp : informTheseFPlayers) {
			fp.msg("<h>%s<i> claimed land for <h>%s<i> from <h>%s<i>.", faction.describeTo(fp, true), faction.describeTo(fp), faction.describeTo(fp));
		}
		
		Board.setFactionAt(faction, flocation);
		
		// Log if factions wants us to
		if (Conf.logLandClaims) {
			com.massivecraft.factions.P.p.log(fplayer.getName()+" claimed land at ("+flocation.getCoordString()+") for the faction: "+faction.getTag());
		}
		
		return true;
	}	
	
	// Grabs the last chunk in a set 
	private Chunk getFinalChunk(Set<Chunk> chunks) {
		Chunk finalChunk = null;
		for (Chunk oldChunk : chunks) finalChunk = oldChunk;
		
		return finalChunk;
	}

}