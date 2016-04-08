package me.markeh.factionsframework.layers.fplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;

import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.layers.factions.Factions_1_6;

public class FPlayer_1_6 extends Messenger implements FPlayer {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public FPlayer_1_6(String id) {
		this.id = id;
		
		if (id == "@console") {
			this.factionsfplayer = null;
		} else {
			this.factionsfplayer = com.massivecraft.factions.FPlayers.getInstance().getById(id);
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
		if (this.id == "@console") return Factions.getNone();
		
		return Factions.getById(this.factionsfplayer.getFaction().getId());
	}

	@Override
	public void setFaction(Faction faction) {
		if (this.id == "@console") return;
		
		this.factionsfplayer.setFaction(com.massivecraft.factions.Factions.getInstance().getFactionById(faction.getId()));		
	}

	@Override
	public Rel getRelationTo(Object object) {
		if (this.id == "@console") return Rel.NEUTRAL;
		
		if (object instanceof Faction) {
			object = com.massivecraft.factions.Factions.getInstance().getFactionById(((Faction) object).getId());
		}
		
		if (object instanceof FPlayer) {
			object = com.massivecraft.factions.FPlayers.getInstance().getById(((FPlayer) object).getId());
		}
		
		if (object instanceof Player) {
			object = com.massivecraft.factions.FPlayers.getInstance().getByPlayer(((Player) object));
		}
		
		Relation relation = this.factionsfplayer.getRelationTo((RelationParticipator) object);
		
		return Factions_1_6.convertRelationship(relation);
	}

	@Override
	public String getUniverse() {
		return "default";
	}

	@Override
	public void msg(String msg) {
		if (this.id == "@console") {
			Bukkit.getConsoleSender().sendMessage(msg);
			return;
		}
		
		this.factionsfplayer.sendMessage(Util.colourse(msg));		
	}

	@Override
	public Boolean isOnline() {
		if (this.id == "@console") return true;
		
		return this.factionsfplayer.isOnline();
	}
	
}
