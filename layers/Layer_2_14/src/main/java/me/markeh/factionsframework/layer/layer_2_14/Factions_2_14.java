package me.markeh.factionsframework.layer.layer_2_14;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.ps.PS;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.Rel;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Factions_2_14 extends Factions {

    private Map<String, Faction> factionsMap;
    private String noneID;
    private String warzoneID;
    private String safezoneID;

    public Factions_2_14() {
        factionsMap = new HashMap<>();
        noneID = null;
        warzoneID = null;
        safezoneID = null;
    }

    @Override
    public Faction get(String id) {
        if (id == null) return null;

        if (!factionsMap.containsKey(id)) {
            factionsMap.put(id, new Faction_2_14(id));
        }

        Faction faction = factionsMap.get(id);

        if (!faction.isValid()) {
            factionsMap.remove(id);
            return null;
        }

        return faction;

    }

    @Override
    public Faction getUsingName(String id, String universe) {
        return get(FactionColl.get().getByName(id).getId());
    }

    @Override
    public Faction getAt(Chunk chunk) {

        com.massivecraft.factions.entity.Faction mFaction = BoardColl.get().getFactionAt(PS.valueOf(chunk));

        return mFaction == null ? null : get(mFaction.getId());

    }

    @Override
    public Faction getFactionNone(World world) {

        if (noneID == null) noneID = com.massivecraft.factions.Factions.ID_NONE;

        return get(noneID);

    }

    @Override
    public Faction getFactionWarZone(World world) {

        if (warzoneID == null) warzoneID = (String) getFactionsField("ID_WARZONE");

        return get(warzoneID);

    }

    @Override
    public Faction getFactionSafeZone(World world) {

        if (this.safezoneID == null) safezoneID = (String) getFactionsField("ID_SAFEZONE");

        return get(safezoneID);

    }

    @Override
    public Set<Faction> getAllFactions() {

        Set<Faction> factions = new TreeSet<>();

        for (com.massivecraft.factions.entity.Faction faction : BoardColl.get().getFactionToChunks().keySet()) {
            factions.add(get(faction.getId()));
        }

        return factions;

    }

    // -------------------------------------------------- //
    // Utils
    // -------------------------------------------------- //

    private Object getFactionsField(String field) {
        try {
            return com.massivecraft.factions.Factions.class.getField(field).get(this);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Rel convertRelationship(com.massivecraft.factions.Rel rel) {

        switch (rel) {
            case ALLY:
                return Rel.ALLY;
            case ENEMY:
                return Rel.ENEMY;
            case MEMBER:
                return Rel.MEMBER;
            case TRUCE:
                return Rel.TRUCE;
            case LEADER:
                return Rel.LEADER;
            case NEUTRAL:
                return Rel.NEUTRAL;
            case OFFICER:
                return Rel.OFFICER;
            case RECRUIT:
                return Rel.RECRUIT;
            default:
                return null;
        }

    }

}