package me.markeh.factionsframework.layers.fplayer;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.massivecore.ps.PS;

import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.layers.factions.Factions_2_6;

public class FPlayer_2_6 extends Messenger implements FPlayer {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public FPlayer_2_6(String id) {
		this.id = id;
		
		if (this.id == "@console") {
			this.uplayer = UPlayer.get(Bukkit.getConsoleSender());
		} else {
			this.uplayer = UPlayer.get(id);
		}
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private String id;
	private com.massivecraft.factions.entity.UPlayer uplayer;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Player asBukkitPlayer() {
		return this.uplayer.getPlayer();
	}

	@Override
	public Faction getFaction() {
		if (this.id == "@console") return Factions.getNone();
		
		return Factions.getById(this.uplayer.getFaction().getId());
	}

	@Override
	public void setFaction(Faction faction) {
		if (this.id == "@console") return;
		
		this.uplayer.setFaction(FactionColls.get().get2(faction.getId()));		
	}

	@Override
	public Rel getRelationTo(Object object) {
		if (this.id == "@console") return Rel.NEUTRAL;
		
		if (object instanceof Faction) {
			object = FactionColls.get().get2(((Faction) object).getId());
		}
		
		if (object instanceof FPlayer) {
			object = UPlayer.get(((FPlayer) object).getId());
		}
		
		if (object instanceof Player) {
			object = UPlayer.get((Player) object);
		}
		
		return Factions_2_6.convertRelationship(this.uplayer.getRelationTo((RelationParticipator) object));
	}

	@Override
	public String getUniverse() {
		if (this.id == "@console") return null;
		
		return this.uplayer.getUniverse();
	}

	@Override
	public void msg(String msg) {
		if (this.id == "@console") {
			Bukkit.getConsoleSender().sendMessage(Util.colourse(msg));
			return;
		}
		this.uplayer.sendMessage(Util.colourse(msg));
	}

	@Override
	public Boolean isOnline() {
		if (this.id == "@console") return true;
		
		return this.uplayer.isOnline();
	}

	@Override
	public Rel getRole() {
		return Factions_2_6.convertRelationship(this.uplayer.getRole());
	}

	@Override
	public void setRole(Rel role) {
		this.uplayer.setRole(com.massivecraft.factions.Rel.valueOf(role.toString()));
	}

	@Override
	public Location getLocation() {
		return this.uplayer.getPlayer().getLocation();
	}

	@Override
	public World getWorld() {
		return this.uplayer.getPlayer().getWorld();
	}

	@Override
	public String getName() {
		return this.uplayer.getName();
	}

	@Override
	public double getPowerBoost() {
		return this.uplayer.getPowerBoost();
	}

	@Override
	public void setPowerBoost(Double boost) {
		this.uplayer.setPowerBoost(boost);
	}

	@Override
	public boolean hasPowerBoost() {
		return (this.getPowerBoost() != 0D);
	}

	@Override
	public double getPower() {
		return this.uplayer.getPower();
	}

	@Override
	public int getPowerRounded() {
		return (int) Math.floor(this.getPower());
	}

	@Override
	public boolean tryClaim(Faction faction, Location location) {
		return this.uplayer.tryClaim(FactionColls.get().get2(faction.getId()), PS.valueOf(location), false, false);
	}

	@Override
	public boolean tryClaim(Faction faction, Collection<Location> locations) {
		for (Location location : locations) {
			if ( ! this.tryClaim(faction, location)) return false;
		}
		return true;
	}
	
}
