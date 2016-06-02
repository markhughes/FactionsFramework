package me.markeh.factionsframework.layer.layer_1_8;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Location;

import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.FFlag;

import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;

public class Faction_1_8 extends Messenger implements Faction {

	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public Faction_1_8(String id) {
		this.faction = com.massivecraft.factions.Factions.i.get(id);
	}

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private com.massivecraft.factions.Faction faction;
	
	// -------------------------------------------------- //
	// OVERRIDE METHODS
	// -------------------------------------------------- //
		
	@Override
	public String getId() {
		return this.faction.getId();
	}

	@Override
	public String getName() {
		return this.faction.getTag();
	}

	@Override
	public String getDescription() {
		return this.faction.getDescription();
	}
	
	@Override
	public void setDescription(String description) {
		this.faction.setDescription(description);		
	}

	@Override
	public Set<FPlayer> getMembers() {
		Set<FPlayer> members = new TreeSet<FPlayer>();
		
		for (com.massivecraft.factions.FPlayer oplayer : this.faction.getFPlayers()) {
			members.add(FPlayers.getById(oplayer.getId()));
		}
		
		return members;
	}
	
	@Override
	public Set<FPlayer> getMembersExcept(Rel... rels) {
		Set<FPlayer> members = new TreeSet<FPlayer>();
		
		ArrayList<Rel> relsList = new ArrayList<Rel>();
		for (Rel rel : rels) relsList.add(rel);
		
		for (com.massivecraft.factions.FPlayer oplayer : this.faction.getFPlayers()) {
			FPlayer fplayer = FPlayers.getById(oplayer.getId());
			
			if (relsList.contains(fplayer.getRole())) members.add(fplayer);
		}
		
		return members;

	}

	@Override
	public Set<FPlayer> getOfficers() {
		Set<FPlayer> officers = new TreeSet<FPlayer>();
		
		for (com.massivecraft.factions.FPlayer oplayer : this.faction.getFPlayersWhereRole(com.massivecraft.factions.struct.Rel.OFFICER)) {
			officers.add(FPlayers.getById(oplayer.getId()));
		}
		
		return officers;
	}
	
	@Override
	public Optional<FPlayer> leader() {
		return Optional.of(FPlayers.getById(this.faction.getFPlayerLeader().getId()));
	}

	@Override
	public FPlayer getLeader() {
		return this.leader().get();
	}

	@Override
	public Location getHome() {
		return this.faction.getHome();
	}

	@Override
	public Set<Faction> getRelationsWith(Rel rel) {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (com.massivecraft.factions.Faction afaction : com.massivecraft.factions.Factions.i.get()) {
			Rel newRel = Factions_1_8.convertRelationship(afaction.getRelationTo(this.faction));
			if (newRel != rel) continue;
			
			factions.add(Factions.getById(afaction.getId()));
		}
		
		return factions;
	}

	@Override
	public Rel getRelationTo(Object comparing) {
		// Convert our FactionsFramework Factions object to their object
		if (comparing instanceof Faction) {
			Faction faction = (Faction) comparing;
			
			comparing = com.massivecraft.factions.Factions.i.get(faction.getId());
		}
		
		// Convert our FactionsFramework FPlayer object to their object
		if (comparing instanceof FPlayer) {
			FPlayer fplayer = (FPlayer) comparing;
			
			comparing = com.massivecraft.factions.FPlayers.i.get(fplayer.getId());
		}
		
		// Cast to RelationParticipator if we can
		if (comparing instanceof RelationParticipator) {
			RelationParticipator rp = (RelationParticipator) comparing;
			
			com.massivecraft.factions.struct.Rel factionsRelation = this.faction.getRelationTo(rp);
						
			return Factions_1_8.convertRelationship(factionsRelation);
		}
		
		// We couldn't do it, so return null
		return null;
	}

	@Override
	public int getLandCount() {
		return this.faction.getLandRounded();
	}

	@Override
	public double getPower() {
		return this.faction.getPower();
	}

	@Override
	public boolean isPermanentFaction() {
		return this.faction.getFlag(FFlag.PERMANENT);
	}

	@Override
	public boolean isNone() {
		return this.faction.isNone();
	}

	@Override
	public void msg(String msg) {
		this.faction.sendMessage(Util.colourse(msg));		
	}
	
	@Override
	public Boolean quiteDisband() {
		this.faction.detach();
		return true;
	}
	
	@Override
	public Boolean addMember(FPlayer fplayer) {
		com.massivecraft.factions.FPlayers.i.get(fplayer.getId()).setFaction(this.faction);
		return true;
	}
	
	@Override
	public Boolean isValid() {
		if (this.faction == null) return false;
		
		return true;
	}
	
}
