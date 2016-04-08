package me.markeh.factionsframework.layers.faction;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Location;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.event.EventFactionsDisband;
import me.markeh.factionsframework.layers.factions.Factions_2_8_6;

public class Faction_2_8_6 extends Messenger implements Faction {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public Faction_2_8_6(String id) {
		this.faction = this.getFactionColl().get(id);
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private com.massivecraft.factions.entity.Faction faction;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public String getId() {
		return this.faction.getId();
	}

	@Override
	public String getName() {
		return this.faction.getName();
	}

	@Override
	public String getDescription() {
		return this.faction.getDescription();
	}

	@Override
	public Set<FPlayer> getMembers() {
		Set<FPlayer> fplayers = new TreeSet<FPlayer>();
		
		for (MPlayer mplayer : this.faction.getMPlayers()) {
			fplayers.add(FPlayers.getById(mplayer.getId()));
		}
		
		return fplayers;
	}

	@Override
	public Set<FPlayer> getOfficers() {
		Set<FPlayer> officers = new TreeSet<FPlayer>();
		
		for (MPlayer mplayer: this.faction.getMPlayersWhereRole(com.massivecraft.factions.Rel.OFFICER)) {
			officers.add(FPlayers.getById(mplayer.getId()));
		}
		
		return officers;
	}

	@Override
	public FPlayer getLeader() {
		return FPlayers.getById(this.faction.getLeader().getId());
	}

	@Override
	public Location getHome() {
		return this.faction.getHome().asBukkitLocation();
	}

	@Override
	public Set<Faction> getRelationsWith(Rel rel) {
		Set<Faction> factions = new TreeSet<Faction>();
		
		for (com.massivecraft.factions.entity.Faction faction: this.getFactionColl().getAll()) {
			Rel factionRel = Factions_2_8_6.convertRelationship(faction.getRelationTo(this.faction));
			
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
			
			comparing = MPlayer.get(fplayer.getId());
		}
		
		if (comparing instanceof RelationParticipator) {
			Rel rel = Factions_2_8_6.convertRelationship(this.faction.getRelationTo((RelationParticipator) comparing));
			
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
		return this.faction.getFlag(MFlag.getFlagPermanent());
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
		if (this.faction.getFlag(MFlag.getFlagPermanent())) return false;
		
		EventFactionsDisband event = new EventFactionsDisband(this);
		event.call();
		
		if (event.isCancelled()) return false;
		
		this.faction.detach();
		
		return true;
	}

	@Override
	public Boolean addMember(FPlayer fplayer) {
		MPlayer.get(fplayer.getId()).setFaction(FactionColl.get().get(this.getId()));
		return true;
	}
	
	@Override
	public Boolean isValid() {
		if (this.faction == null) return false;
		return true;
	}
	
	// -------------------------------------------------- //
	// UTILS
	// -------------------------------------------------- //
	
	private FactionColl factioncoll_instance;
	public FactionColl getFactionColl() {
		if (this.factioncoll_instance == null) {
			try {
				this.factioncoll_instance = (FactionColl) FactionColl.class.getMethod("get").invoke(this);
			} catch (Exception e) {
				FactionsFramework.get().logError(e);
			}
		}
		
		return this.factioncoll_instance;
	}
	
}
