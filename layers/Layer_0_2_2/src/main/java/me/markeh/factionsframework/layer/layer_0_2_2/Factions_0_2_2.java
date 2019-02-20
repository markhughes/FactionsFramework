package me.markeh.factionsframework.layer.layer_0_2_2;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Factions_0_2_2 extends Factions {

    private Map<String, Faction> factionsMap;
    private String noneID;
    private String warzoneID;
    private String safezoneID;

    public Factions_0_2_2() {
        factionsMap = new HashMap<>();
        noneID = null;
        warzoneID = null;
        safezoneID = null;
    }

    @Override
    public Faction get(String id) {
        if (!this.factionsMap.containsKey(id)) {
            this.factionsMap.put(id, new Faction_0_2_2(id));
        }

        Faction faction = factionsMap.get(id);

        if (!faction.isValid()) {
            this.factionsMap.remove(id);
            return null;
        }

        return faction;
    }


    @Override
    public Faction getUsingName(String name, String universe) {
        return this.get(com.massivecraft.factions.Factions.getInstance().getByTag(name).getId());
    }

    @Override
    public Faction getAt(Chunk chunk) {
        FLocation flocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
        String id = Board.getInstance().getIdAt(flocation);

        return id == null ? null : get(id);
    }

    @Override
    public Faction getFactionNone(World world) {
        if (this.noneID == null) this.noneID = com.massivecraft.factions.Factions.getInstance().getWilderness().getId();

        return this.get(this.noneID);
    }

    @Override
    public Faction getFactionWarZone(World world) {
        if (this.warzoneID == null)
            this.warzoneID = com.massivecraft.factions.Factions.getInstance().getWarZone().getId();

        return this.get(this.warzoneID);
    }


    @Override
    public Faction getFactionSafeZone(World world) {
        if (this.safezoneID == null)
            this.safezoneID = com.massivecraft.factions.Factions.getInstance().getSafeZone().getId();

        return this.get(this.safezoneID);
    }

    @Override
    public Set<Faction> getAllFactions() {
        Set<Faction> factions = new TreeSet<>();

        com.massivecraft.factions.Factions.getInstance().getAllFactions().forEach(faction -> factions.add(Factions.getById(faction.getId())));

        return factions;
    }

    // Convert to our relation type
    public static Rel convertRelationship(Relation relation) {
        if (relation == Relation.ALLY) return Rel.ALLY;
        if (relation == Relation.ENEMY) return Rel.ENEMY;
        if (relation == Relation.MEMBER) return Rel.MEMBER;
        if (relation == Relation.TRUCE) return Rel.TRUCE;

        return null;
    }

}