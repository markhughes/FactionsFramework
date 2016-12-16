package me.markeh.factionsframework.layer.layer_1_8;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.iface.RelationParticipator;

import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;

public class FPlayer_1_8 extends Messenger implements FPlayer {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public FPlayer_1_8(String id) {
		this.id = id;
		
		if (id == "@console") {
			this.factionsfplayer = null;
		} else {
			this.factionsfplayer = com.massivecraft.factions.FPlayers.i.get(id);
		}
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private String id;
	private com.massivecraft.factions.FPlayer factionsfplayer;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public Player asBukkitPlayer() {
		return this.factionsfplayer.getPlayer();
	}
	
	@Override
	public Faction getFaction() {
		if (this.id == "@console") return Factions.getNone(this.factionsfplayer.getPlayer().getWorld());
		
		return Factions.getById(this.factionsfplayer.getFaction().getId());
	}

	@Override
	public void setFaction(Faction faction) {
		if (this.id == "@console") return;
		
		this.factionsfplayer.setFaction(com.massivecraft.factions.Factions.i.get(faction.getId()));		
	}

	@Override
	public Rel getRelationTo(Object object) {
		if (this.id == "@console") return Rel.NEUTRAL;
		
		if (object instanceof Faction) {
			object = com.massivecraft.factions.Factions.i.get(((Faction) object).getId());
		}
		
		if (object instanceof FPlayer) {
			object = com.massivecraft.factions.FPlayers.i.get(((FPlayer) object).getId());
		}
		
		if (object instanceof Player) {
			object = com.massivecraft.factions.FPlayers.i.get(((Player) object));
		}
		
		com.massivecraft.factions.struct.Rel relation = this.factionsfplayer.getRelationTo((RelationParticipator) object);
		
		return Factions_1_8.convertRelationship(relation);
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
		
		this.factionsfplayer.sendMessage(Util.colourse(msg));		
	}

	@Override
	public Boolean isOnline() {
		if (this.id == "@console") return true;
		
		return this.factionsfplayer.isOnline();
	}

	@Override
	public Rel getRole() {
		com.massivecraft.factions.struct.Rel role = this.factionsfplayer.getRole();
		
		return Factions_1_8.convertRelationship(role);
	}

	@Override
	public void setRole(Rel role) {
		if (role == Rel.LEADER) {
			this.factionsfplayer.setRole(com.massivecraft.factions.struct.Rel.LEADER);
			return;
		}
		
		if (role == Rel.OFFICER) {
			this.factionsfplayer.setRole(com.massivecraft.factions.struct.Rel.OFFICER);
			return;
		}
		
		if (role == Rel.RECRUIT) {
			this.factionsfplayer.setRole(com.massivecraft.factions.struct.Rel.RECRUIT);
			return;
		}
		
		if (role == Rel.MEMBER) {
			this.factionsfplayer.setRole(com.massivecraft.factions.struct.Rel.MEMBER);
			return;
		}
	}

	@Override
	public Location getLocation() {
		return this.factionsfplayer.getPlayer().getLocation();
	}
	
	@Override
	public Faction getFactionAt() {
		return Factions.getById(Board.getFactionAt(this.factionsfplayer.getLastStoodAt()).getId());
	}
	
	@Override
	public World getWorld() {
		return this.factionsfplayer.getPlayer().getWorld();
	}

	@Override
	public String getName() {
		return this.factionsfplayer.getName();
	}

	@Override
	public double getPowerBoost() {
		return this.factionsfplayer.getPowerBoost();
	}

	@Override
	public void setPowerBoost(Double boost) {
		this.factionsfplayer.setPowerBoost(boost);
	}

	@Override
	public boolean hasPowerBoost() {
		return (this.getPower() != 0D);
	}

	@Override
	public double getPower() {
		return this.factionsfplayer.getPower();
	}

	@Override
	public int getPowerRounded() {
		return (int) Math.floor(this.getPower());
	}

	@Override
	public void setPower(Double power) {
		try {
			Method method = this.factionsfplayer.getClass().getDeclaredMethod("alterPower", double.class);
			method.setAccessible(true);
			method.invoke(this.factionsfplayer, power);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean tryClaim(Faction faction, Location location) {
		return this.factionsfplayer.attemptClaim(com.massivecraft.factions.Factions.i.get(faction.getId()), location, true);
	}

	@Override
	public boolean tryClaim(Faction faction, Collection<Location> locations) {
		for (Location location : locations) {
			if (! this.tryClaim(faction, location)) return false;
		}
		return true;
	}

	@Override
	public boolean isUsingAdminMode() {
		return this.factionsfplayer.hasAdminMode();
	}

}
