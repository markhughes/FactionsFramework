package me.markeh.factionsframework.layer.layer_0_2_2;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FPlayer_0_2_2 extends Messenger implements FPlayer {

    private String id;
    private com.massivecraft.factions.FPlayer factionsfplayer;

    public FPlayer_0_2_2(String id) {
        this.id = id;

        factionsfplayer = isConsole() ? null : com.massivecraft.factions.FPlayers.getInstance().getById(id);

    }

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
        if (isConsole()) return Factions.getNone(this.factionsfplayer.getPlayer().getWorld());

        return Factions.getById(this.factionsfplayer.getFaction().getId());
    }

    @Override
    public void setFaction(Faction faction) {
        if (isConsole()) return;

        this.factionsfplayer.setFaction(com.massivecraft.factions.Factions.getInstance().getFactionById(faction.getId()));
    }

    @Override
    public Rel getRelationTo(Object object) {
        if (isConsole()) return Rel.NEUTRAL;

        if (object instanceof Faction)
            object = com.massivecraft.factions.Factions.getInstance().getFactionById(((Faction) object).getId());

        if (object instanceof FPlayer)
            object = com.massivecraft.factions.FPlayers.getInstance().getById(((FPlayer) object).getId());

        if (object instanceof Player)
            object = com.massivecraft.factions.FPlayers.getInstance().getByPlayer(((Player) object));

        return Factions_0_2_2.convertRelationship(factionsfplayer.getRelationTo((RelationParticipator) object));
    }

    @Override
    public String getUniverse() {
        return "default";
    }

    @Override
    public void msg(String msg) {
        if (isConsole()) Bukkit.getConsoleSender().sendMessage(Util.colourse(msg));
        else this.factionsfplayer.sendMessage(Util.colourse(msg));
    }

    @Override
    public Boolean isOnline() {
        return isConsole() || this.factionsfplayer.isOnline();
    }

    @Override
    public Rel getRole() {
        Role role = this.factionsfplayer.getRole();

        if (role == Role.ADMIN) return Rel.LEADER;
        if (role == Role.MODERATOR) return Rel.OFFICER;

        return Rel.MEMBER;
    }

    @Override
    public void setRole(Rel role) {
        if (role == Rel.LEADER) this.factionsfplayer.setRole(Role.ADMIN);
        else if (role == Rel.OFFICER) this.factionsfplayer.setRole(Role.MODERATOR);
        else this.factionsfplayer.setRole(Role.NORMAL);
    }

    @Override
    public Location getLocation() {
        return this.factionsfplayer.getPlayer().getLocation();
    }

    @Override
    public Faction getFactionAt() {
        return Factions.getById(Board.getInstance().getFactionAt(this.factionsfplayer.getLastStoodAt()).getId());
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
        return (int) this.getPower();
    }

    @Override
    public void setPower(Double power) {
        this.factionsfplayer.alterPower(power - this.getPower());
    }

    @Override
    public boolean tryClaim(Faction faction, Location location) {
        return this.factionsfplayer.attemptClaim(com.massivecraft.factions.Factions.getInstance().getFactionById(faction.getId()), location, true);
    }

    @Override
    public boolean tryClaim(Faction faction, Collection<Location> locations) {
        for (Location location : locations)
            if (!this.tryClaim(faction, location)) return false;
        return true;
    }

    @Override
    public boolean isUsingAdminMode() {
        return this.factionsfplayer.isAdminBypassing();
    }

    private boolean isConsole() {
        return id.equals("@console");
    }

}