package me.markeh.factionsframework.layer.layer_2_14;

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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FPlayer_2_14 extends Messenger implements FPlayer {

    private String id;
    private com.massivecraft.factions.entity.MPlayer mPlayer;

    public FPlayer_2_14(String id) {
        this.id = id;

        if (isConsole()) {
            mPlayer = MPlayer.get(Bukkit.getConsoleSender());
        } else {
            mPlayer = MPlayer.get(id);
        }

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Player asBukkitPlayer() {
        return mPlayer.getPlayer();
    }

    @Override
    public Faction getFaction() {
        return isConsole() ? Factions.getNone(Bukkit.getWorlds().get(0)) : Factions.getById(mPlayer.getFaction().getId());
    }

    @Override
    public void setFaction(Faction faction) {
        if (!isConsole()) mPlayer.setFaction(FactionColl.get().get(faction.getId()));
    }

    @Override
    public Rel getRelationTo(Object object) {
        if (isConsole()) return Rel.NEUTRAL;

        if (object instanceof Faction) object = FactionColl.get().get(((Faction) object).getId());
        if (object instanceof FPlayer) object = MPlayer.get(((FPlayer) object).getId());
        if (object instanceof Player) object = MPlayer.get(object);

        return Factions_2_14.convertRelationship(mPlayer.getRelationTo((RelationParticipator) object));
    }

    @Override
    public String getUniverse() {
        return "default";
    }

    @Override
    public void msg(String msg) {
        if (isConsole()) {
            Bukkit.getConsoleSender().sendMessage(Util.colourse(msg));
            return;
        }

        this.mPlayer.msg(Util.colourse(msg));
    }

    @Override
    public Boolean isOnline() {
        return isConsole() || mPlayer.isOnline();
    }

    @Override
    public Rel getRole() {
        return Factions_2_14.convertRelationship(mPlayer.getRole());
    }

    @Override
    public void setRole(Rel role) {
        mPlayer.setRole(com.massivecraft.factions.Rel.valueOf(role.name()));
    }

    @Override
    public Location getLocation() {
        return asBukkitPlayer().getLocation();
    }

    @Override
    public Faction getFactionAt() {
        return Factions.getFactionAt(getLocation());
    }

    @Override
    public World getWorld() {
        return asBukkitPlayer().getWorld();
    }

    @Override
    public String getName() {
        return mPlayer.getName();
    }

    @Override
    public double getPowerBoost() {
        return mPlayer.getPowerBoost();
    }

    @Override
    public void setPowerBoost(Double boost) {
        mPlayer.setPowerBoost(boost);
    }

    @Override
    public boolean hasPowerBoost() {
        return getPowerBoost() != 0;
    }

    @Override
    public double getPower() {
        return mPlayer.getPower();
    }

    @Override
    public int getPowerRounded() {
        return (int) getPower();
    }

    @Override
    public void setPower(Double power) {
        mPlayer.setPower(power);
    }

    @Override
    public boolean tryClaim(Faction faction, Location location) {
        return mPlayer.tryClaim(FactionColl.get().get(faction.getId()), Collections.singletonList(PS.valueOf(location)));
    }

    @Override
    public boolean tryClaim(Faction faction, Collection<Location> locations) {
        Collection<PS> locationColl = new ArrayList<>();
        locations.forEach(location -> locationColl.add(PS.valueOf(location)));

        return mPlayer.tryClaim(FactionColl.get().get(faction.getId()), locationColl);
    }

    @Override
    public boolean isUsingAdminMode() {
        return mPlayer.isOverriding();
    }

    private boolean isConsole() {
        return id.equals("@console");
    }

}