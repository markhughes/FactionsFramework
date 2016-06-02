package me.markeh.factionsframework.layer.layer_2_6;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Location;

import com.massivecraft.factions.FFlag;
import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.event.EventFactionsDisband;

public class Faction_2_6 extends Messenger implements Faction {

	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public Faction_2_6(String id) {
		for (FactionColl fc : FactionColls.get().getColls()) {
			for (com.massivecraft.factions.entity.Faction faction : fc.getAll()) {
				if ( ! faction.getId().equalsIgnoreCase(id)) continue;
				
				this.faction = faction;
			}
		}
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private com.massivecraft.factions.entity.Faction faction;
	
	// -------------------------------------------------- //
	// OVERRIDE METHODS
	// -------------------------------------------------- //
	
	@Override
	public String getId() {
		return faction.getId();
	}

	@Override
	public String getName() {
		return faction.getName();
	}

	@Override
	public String getDescription() {
		return faction.getDescription();
	}
	
	@Override
	public void setDescription(String description) {
		faction.setDescription(description);
	}

	@Override
	public Set<FPlayer> getMembers() {
		Set<FPlayer> members = new TreeSet<FPlayer>();
		
		for (UPlayer uplayer : this.faction.getUPlayers()) {
			members.add(FPlayers.getById(uplayer.getId()));
		}
		
		return members;
	}
	
	@Override
	public Set<FPlayer> getMembersExcept(Rel... rels) {
		Set<FPlayer> members = new TreeSet<FPlayer>();
		
		ArrayList<Rel> relsList = new ArrayList<Rel>();
		for (Rel rel : rels) relsList.add(rel);
		
		for (UPlayer uplayer : this.faction.getUPlayers()) {
			FPlayer fplayer = FPlayers.getById(uplayer.getId());
			
			if (relsList.contains(fplayer.getRole())) members.add(fplayer);
		}
		
		return members;
	}


	@Override
	public Set<FPlayer> getOfficers() {
		Set<FPlayer> officers = new TreeSet<FPlayer>();
		
		for (UPlayer uplayer : this.faction.getUPlayers()) {
			if (uplayer.getRole() != com.massivecraft.factions.Rel.OFFICER) continue;
			
			officers.add(FPlayers.getById(uplayer.getId()));
		}
		
		return officers;
	}

	@Override
	public Optional<FPlayer> leader() {
		return Optional.of(FPlayers.getById(this.faction.getLeader().getId()));
	}
	
	@Override
	public FPlayer getLeader() {
		return this.leader().get();
	}

	@Override
	public Location getHome() {
		return this.faction.getHome().asBukkitLocation();
	}

	@Override
	public Set<Faction> getRelationsWith(Rel rel) {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (com.massivecraft.factions.entity.Faction faction : this.faction.getColl().getAll()) {
			Rel factionRel = Factions_2_6.convertRelationship((faction.getRelationTo(this.faction)));
			
			if (factionRel != rel) continue;
			
			factions.add(Factions.getById(faction.getId()));
		}
		
		return factions;
	}

	@Override
	public Rel getRelationTo(Object comparing) {
		// Convert our FactionsFramework Factions object to their object
		if (comparing instanceof Faction) {
			Faction faction = (Faction) comparing;
			
			comparing = this.faction.getColl().get(faction.getId());
		}
		
		// Convert our FactionsFramework FPlayer object to their object
		if (comparing instanceof FPlayer) {
			FPlayer fplayer = (FPlayer) comparing;
			
			comparing = UPlayer.get(fplayer.getId());
		}
		
		if (comparing instanceof RelationParticipator) {
			Rel rel = Factions_2_6.convertRelationship(this.faction.getRelationTo((RelationParticipator) comparing));
			
			return rel;
		}
		
		return null;
	}

	@Override
	public int getLandCount() {
		return this.faction.getLandCount();
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
		this.faction.msg(msg);
	}

	@Override
	public Boolean quiteDisband() {
		if (this.faction.getFlag(FFlag.PERMANENT)) return false;
		
		EventFactionsDisband event = new EventFactionsDisband(this);
		event.call();
		
		if (event.isCancelled()) return false;
		
		this.faction.detach();
		
		return true;
	}
	
	@Override
	public Boolean addMember(FPlayer fplayer) {
		UPlayer.get(fplayer.getId()).setFaction(FactionColls.get().get2(this.getId()));
		return true;
	}
	
	@Override
	public Boolean isValid() {
		if (this.faction == null) return false;
		return true;
	}
	
}
