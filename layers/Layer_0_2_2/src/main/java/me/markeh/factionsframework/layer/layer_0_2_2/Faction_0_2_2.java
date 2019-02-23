package me.markeh.factionsframework.layer.layer_0_2_2;

import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Role;
import me.markeh.factionsframework.Util;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.entities.Messenger;
import me.markeh.factionsframework.enums.Rel;
import org.bukkit.Location;

import java.util.Set;
import java.util.Optional;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Faction_0_2_2 extends Messenger implements Faction {

    private com.massivecraft.factions.Faction faction;

    public Faction_0_2_2(String id) {
        this.faction = com.massivecraft.factions.Factions.getInstance().getFactionById(id);
    }

    @Override
    public String getId() {
        return this.faction.getId();
    }

    @Override
    public String getName() {
        return this.faction.getTag();
    }

    @Override
    public String getDescription() {
        return this.faction.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.faction.setDescription(description);
    }

    @Override
    public Set<FPlayer> getMembers() {
        Set<FPlayer> members = new TreeSet<>();

        faction.getFPlayers().forEach(fPlayer -> members.add(FPlayers.getById(fPlayer.getId())));

        return members;
    }

    @Override
    public Set<FPlayer> getMembersExcept(Rel... rels) {
        Set<FPlayer> members = new TreeSet<>();

        List<Rel> relsList = new ArrayList<>(Arrays.asList(rels));

        for (com.massivecraft.factions.FPlayer player : faction.getFPlayers()) {
            FPlayer fPlayer = FPlayers.getById(player.getId());
            if (relsList.contains(fPlayer.getRole())) members.add(fPlayer);
        }

        return members;

    }

    @Override
    public Set<FPlayer> getOfficers() {
        Set<FPlayer> officers = new TreeSet<>();

        faction.getFPlayersWhereRole(Role.MODERATOR).forEach(fPlayer -> officers.add(FPlayers.getById(fPlayer.getId())));

        return officers;
    }

    @Override
    public Optional<FPlayer> leader() {
        return (Optional.of(FPlayers.getById(this.faction.getFPlayerAdmin().getId())));
    }

    @Override
    public FPlayer getLeader() {
        return this.leader().orElse(null);
    }

    @Override
    public Location getHome() {
        return this.faction.getHome();
    }

    @Override
    public Set<Faction> getRelationsWith(Rel rel) {
        Set<Faction> factions = new TreeSet<>();

        for (com.massivecraft.factions.Faction faction : com.massivecraft.factions.Factions.getInstance().getAllFactions())
            if (Factions_0_2_2.convertRelationship(faction.getRelationTo(this.faction)) == rel)
                factions.add(Factions.getById(faction.getId()));

        return factions;
    }

    @Override
    public Rel getRelationTo(Object rel) {
        if (rel instanceof Faction)
            rel = com.massivecraft.factions.Factions.getInstance().getFactionById(((Faction) rel).getId());

        if (rel instanceof FPlayer)
            rel = com.massivecraft.factions.FPlayers.getInstance().getById(((FPlayer) rel).getId());

        if (rel instanceof RelationParticipator)
            return Factions_0_2_2.convertRelationship(faction.getRelationTo((RelationParticipator) rel));

        // We couldn't do it, so return null
        return null;
    }

    @Override
    public int getLandCount() {
        return this.faction.getLandRounded();
    }

    @Override
    public double getPower() {
        return this.faction.getPower();
    }

    @Override
    public boolean isPermanentFaction() {
        return this.faction.isPermanent();
    }

    @Override
    public boolean isNone() {
        return this.faction.isWilderness();
    }

    @Override
    public void msg(String msg) {
        this.faction.sendMessage(Util.colourse(msg));
    }

    @Override
    public Boolean quiteDisband() {
        this.faction.remove();
        return true;
    }

    @Override
    public Boolean addMember(FPlayer fplayer) {
        return this.faction.addFPlayer(com.massivecraft.factions.FPlayers.getInstance().getById(fplayer.getId()));
    }

    @Override
    public Boolean isValid() {
        return this.faction != null;
    }

}