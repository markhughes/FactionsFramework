package me.markeh.factionsframework.layer.layer_2_14;

import com.massivecraft.factions.RelationParticipator;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;
import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.entities.*;
import me.markeh.factionsframework.enums.Rel;
import me.markeh.factionsframework.event.EventFactionsDisband;
import org.bukkit.Location;

import java.util.*;

public class Faction_2_14 extends Messenger implements Faction {

    private FactionColl factioncoll_instance = null;
    private com.massivecraft.factions.entity.Faction mFaction;

    public Faction_2_14(String id) {
        this.mFaction = getFactionColl().get(id);
    }

    @Override
    public String getId() {
        return mFaction.getId();
    }

    @Override
    public String getName() {
        return mFaction.getName();
    }

    @Override
    public String getDescription() {
        return mFaction.getDescription();
    }

    @Override
    public void setDescription(String description) {
        mFaction.setDescription(description);
    }

    @Override
    public Set<FPlayer> getMembers() {

        Set<FPlayer> fPlayers = new TreeSet<>();

        mFaction.getMPlayers().forEach(mPlayer -> fPlayers.add(FPlayers.getById(mPlayer.getId())));

        return fPlayers;

    }

    @Override
    public Set<FPlayer> getOfficers() {
        Set<FPlayer> players = getMembers();

        players.removeIf(fPlayer -> fPlayer.getRole() != Rel.OFFICER);

        return players;
    }

    @Override
    public Set<FPlayer> getMembersExcept(Rel... rels) {

        Set<FPlayer> members = new TreeSet<>();
        List<Rel> relList = Arrays.asList(rels);

        for (FPlayer player : getMembers())
            if (relList.contains(player.getRole())) members.add(player);

        return members;

    }

    @Override
    public Optional<FPlayer> leader() {
        return Optional.of(FPlayers.getById(mFaction.getLeader().getId()));
    }

    @Override
    public Location getHome() {
        return mFaction.getHome().asBukkitLocation();
    }

    @Override
    public Set<Faction> getRelationsWith(Rel rel) {

        Set<Faction> factions = new TreeSet<>();

        for (com.massivecraft.factions.entity.Faction faction : getFactionColl().getAll()) {
            Rel factionRel = Factions_2_14.convertRelationship(faction.getRelationTo(mFaction));

            if (factionRel == rel) factions.add(Factions.getById(faction.getId()));

        }

        return factions;
    }

    @Override
    public Rel getRelationTo(Object rel) {

        if (rel instanceof Faction) rel = mFaction.getColl().get(((Faction) rel).getId());
        if (rel instanceof FPlayer) rel = MPlayer.get(((FPlayer) rel).getId());
        if (rel instanceof RelationParticipator)
            return Factions_2_14.convertRelationship(mFaction.getRelationTo((RelationParticipator) rel));

        return null;
    }

    @Override
    public int getLandCount() {
        return mFaction.getLandCount();
    }

    @Override
    public double getPower() {
        return mFaction.getPower();
    }

    @Override
    public boolean isPermanentFaction() {
        return mFaction.getFlag(MFlag.getFlagPermanent());
    }

    @Override
    public boolean isNone() {
        return mFaction.isNone();
    }

    @Override
    public Boolean quiteDisband() {
        if (isPermanentFaction()) return false;

        EventFactionsDisband event = new EventFactionsDisband(this);
        event.call();

        if (event.isCancelled()) return false;

        mFaction.detach();

        return true;

    }

    @Override
    public Boolean addMember(FPlayer fplayer) {
        MPlayer.get(fplayer.getId()).setFaction(FactionColl.get().get(getId()));
        return true;
    }

    @Override
    public Boolean isValid() {
        return mFaction != null;
    }

    @Override
    public void msg(String msg) {
        mFaction.msg(msg);
    }

    @Override
    public FPlayer getLeader() {
        return leader().orElse(null);
    }

    public FactionColl getFactionColl() {
        if (this.factioncoll_instance == null) {
            try {
                this.factioncoll_instance = (FactionColl) FactionColl.class.getMethod("get").invoke(this);
            } catch (Exception e) {
                FactionsFramework.get().err(e);
            }
        }

        return this.factioncoll_instance;
    }

}