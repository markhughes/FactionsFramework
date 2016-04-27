package me.markeh.factionsframework.layers.fplayer;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.layers.factions.Factions_2_8_6;

public class FPlayer_2_8_6 extends Messenger implements FPlayer {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public FPlayer_2_8_6(String id) {
		this.id = id;
		
		if (this.id == "@console") {
			this.mplayer = MPlayer.get(Bukkit.getConsoleSender());
		} else {
			this.mplayer = MPlayer.get(id);
		}
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private String id;
	private com.massivecraft.factions.entity.MPlayer mplayer;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Player asBukkitPlayer() {
		return this.mplayer.getPlayer();
	}

	@Override
	public Faction getFaction() {
		if (this.id == "@console") return Factions.getNone(this.mplayer.getPlayer().getWorld());

		return Factions.getById(this.mplayer.getFactionId());
	}

	@Override
	public void setFaction(Faction faction) {
		if (this.id == "@console") return;
		
		this.mplayer.setFaction(FactionColl.get().get(faction.getId()));
		
	}

	@Override
	public Rel getRelationTo(Object object) {
		if (this.id == "@console") return Rel.NEUTRAL;
		
		if (object instanceof Faction) {
			object = FactionColl.get().get(((Faction) object).getId());
		}
		
		if (object instanceof FPlayer) {
			object = MPlayer.get(((FPlayer) object).getId());
		}
		
		if (object instanceof Player) {
			object = MPlayer.get((Player) object);
		}
		
		return Factions_2_8_6.convertRelationship(this.mplayer.getRelationTo((RelationParticipator) object));
	}

	@Override
	public String getUniverse() {
		return "default";
	}

	@Override
	public void msg(String msg) {
		if (this.id == "@console") {
			Bukkit.getConsoleSender().sendMessage(Util.colourse(msg));
			return;
		}
		
		this.mplayer.msg(Util.colourse(msg));
	}

	@Override
	public Boolean isOnline() {
		if (this.id == "@console") return true;
		
		return this.mplayer.isOnline();
	}

	@Override
	public Rel getRole() {
		return Factions_2_8_6.convertRelationship(this.mplayer.getRole());
	}

	@Override
	public void setRole(Rel role) {
		this.mplayer.setRole(com.massivecraft.factions.Rel.valueOf(role.toString()));
	}

	@Override
	public Location getLocation() {
		return this.mplayer.getPlayer().getLocation();
	}
	
	@Override
	public Faction getFactionAt() {
		return Factions.getFactionAt(this.mplayer.getPlayer().getLocation());
	}

	@Override
	public World getWorld() {
		return this.mplayer.getPlayer().getWorld();
	}

	@Override
	public String getName() {
		return this.mplayer.getName();
	}

	@Override
	public double getPowerBoost() {
		return this.mplayer.getPowerBoost();
	}

	@Override
	public void setPowerBoost(Double boost) {
		this.mplayer.setPowerBoost(boost);
	}

	@Override
	public boolean hasPowerBoost() {
		return (this.getPowerBoost() != 0D);
	}

	@Override
	public double getPower() {
		return this.mplayer.getPower();
	}

	@Override
	public int getPowerRounded() {
		return (int) Math.floor(this.getPower());
	}

	@Override
	public boolean tryClaim(Faction faction, Location location) {
		Collection<PS> locationsCol = new ArrayList<PS>();
		locationsCol.add(PS.valueOf(location));
		
		return this.mplayer.tryClaim(FactionColl.get().get(faction.getId()), locationsCol);
	}

	@Override
	public boolean tryClaim(Faction faction, Collection<Location> locations) {
		Collection<PS> locationsCol = new ArrayList<PS>();
		for (Location location : locations) {
			locationsCol.add(PS.valueOf(location));
		}
		
		return this.mplayer.tryClaim(FactionColl.get().get(faction.getId()), locationsCol);
	}
	
}
