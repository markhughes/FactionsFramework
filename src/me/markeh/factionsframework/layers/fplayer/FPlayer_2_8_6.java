package me.markeh.factionsframework.layers.fplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;

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
		if (this.id == "@console") return Factions.getNone();

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
	public Rel getRank() {
		return Factions_2_8_6.convertRelationship(this.mplayer.getRole());
	}
	
}
