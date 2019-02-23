package me.markeh.factionsframework.layer.layer_2_14;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSFormatHumanSpace;
import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.entities.Faction;
import me.markeh.factionsframework.entities.Factions;
import me.markeh.factionsframework.enums.LeaveReason;
import me.markeh.factionsframework.event.*;
import me.markeh.factionsframework.layer.EventsLayer;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;

import java.util.*;

public class Events_2_14 extends EventsLayer {

    @EventHandler
    public void onFactionJoin(com.massivecraft.factions.event.EventFactionsMembershipChange event) {
        if (event.getReason() != EventFactionsMembershipChange.MembershipChangeReason.JOIN) return;

        Faction faction = Factions.getById(event.getNewFaction().getId());
        FPlayer fplayer = FPlayers.getById(event.getMPlayer().getId());

        EventFactionsJoin joinEvent = new EventFactionsJoin(faction, fplayer);
        joinEvent.setCancelled(event.isCancelled());
        joinEvent.call();

        event.setCancelled(joinEvent.isCancelled());
    }

    @EventHandler
    public void onEventFactionsCreate(com.massivecraft.factions.event.EventFactionsCreate event) {
        String name = event.getFactionName();
        FPlayer fplayer = FPlayers.getById(event.getMPlayer().getId());

        EventFactionsCreate createEvent = new EventFactionsCreate(name, fplayer);
        createEvent.setCancelled(event.isCancelled());
        createEvent.call();

        event.setCancelled(createEvent.isCancelled());
    }

    @EventHandler
    public void onEventFactionsRename(com.massivecraft.factions.event.EventFactionsNameChange event) {
        FPlayer fplayer = FPlayers.getById(event.getMPlayer().getId());

        EventFactionsRename renameEvent = new EventFactionsRename(Factions.getById(event.getFaction().getId()), event.getNewName(), fplayer);
        renameEvent.setCancelled(event.isCancelled());
        renameEvent.call();

        event.setCancelled(renameEvent.isCancelled());
    }

    @EventHandler
    public void onEventFactionsChunksChange(com.massivecraft.factions.event.EventFactionsChunksChange event) {

        Faction newFaction = Factions.getById(event.getNewFaction().getId());
        FPlayer fplayer = FPlayers.getById(event.getMPlayer().getId());

        Set<Chunk> chunks = new HashSet<>();

        for (PS ps : event.getChunks()) {
            Chunk chunk = ps.asBukkitChunk();
            chunks.add(chunk);
        }

        EventFactionsChunksChange chunksChangeEvent = new EventFactionsChunksChange(newFaction, fplayer, chunks);
        chunksChangeEvent.setCancelled(event.isCancelled());
        chunksChangeEvent.call();

        event.setCancelled(chunksChangeEvent.isCancelled());

        // If all chunks have been removed, we cancel the event
        if (chunksChangeEvent.getChunks().size() == 0) {
            event.setCancelled(true);
            return;
        }

        // Size change, so handle it
        if (chunksChangeEvent.getChunks().size() != chunks.size()) {
            event.setCancelled(true);
            if (event.getNewFaction().isNone()) {
                this.handleNewUnclaims(event.getMPlayer(), chunks);
            } else {
                this.handleNewClaims(event.getMPlayer(), event.getNewFaction(), chunks);
            }
        }

        // Same size, check over the chunks
        for (Chunk chunk : chunksChangeEvent.getChunks()) {
            if (chunks.contains(chunk)) continue;

            event.setCancelled(true);

            if (event.getNewFaction().isNone()) {
                this.handleNewUnclaims(event.getMPlayer(), chunks);
            } else {
                this.handleNewClaims(event.getMPlayer(), event.getNewFaction(), chunks);
            }

            return;
        }
    }

    @EventHandler
    public void onEventFactionsDisband(com.massivecraft.factions.event.EventFactionsDisband event) {
        Faction faction = Factions.getById(event.getFactionId());

        EventFactionsDisband eventFactionsDisband = new EventFactionsDisband(faction);
        eventFactionsDisband.setCancelled(event.isCancelled());
        eventFactionsDisband.call();

        event.setCancelled(eventFactionsDisband.isCancelled());
    }

    @EventHandler
    public void onEventFactionsLeave(com.massivecraft.factions.event.EventFactionsMembershipChange event) {
        if (event.getReason() != EventFactionsMembershipChange.MembershipChangeReason.KICK && event.getReason() != EventFactionsMembershipChange.MembershipChangeReason.DISBAND && event.getReason() != EventFactionsMembershipChange.MembershipChangeReason.LEAVE)
            return;

        Faction faction = Factions.getById(event.getMPlayer().getFactionId());
        FPlayer fplayer = FPlayers.getById(event.getMPlayer().getId());

        // Determine the reason
        LeaveReason reason = LeaveReason.Leave;
        Boolean canCancel = true;
        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.KICK) reason = LeaveReason.Kicked;
        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.DISBAND) {
            reason = LeaveReason.Disband;
            canCancel = false;
        }

        EventFactionsLeave eventEventFactionsLeave = new EventFactionsLeave(faction, fplayer, reason, canCancel);
        if (canCancel) eventEventFactionsLeave.setCancelled(event.isCancelled());
        eventEventFactionsLeave.call();

        if (canCancel) event.setCancelled(eventEventFactionsLeave.isCancelled());

    }

    private void handleNewUnclaims(com.massivecraft.factions.entity.MPlayer mplayer, Set<Chunk> chunks) {
        for (Chunk chunk : chunks) {
            if (!this.mimicUnclaim(mplayer, PS.valueOf(chunk))) return;
        }
    }

    private void handleNewClaims(com.massivecraft.factions.entity.MPlayer mplayer, com.massivecraft.factions.entity.Faction faction, Set<Chunk> chunks) {
        for (Chunk chunk : chunks) {
            if (!this.mimicClaim(mplayer, faction, PS.valueOf(chunk))) break;
        }
    }

    private Boolean mimicUnclaim(com.massivecraft.factions.entity.MPlayer mplayer, PS ps) {
        this.doSetAt(Collections.singleton(ps), mplayer, FactionColl.get().get(Factions.getNone(ps.asBukkitWorld()).getId()));
        return true;
    }

    private Boolean mimicClaim(com.massivecraft.factions.entity.MPlayer mplayer, com.massivecraft.factions.entity.Faction faction, PS ps) {
        doSetAt(Collections.singleton(ps), mplayer, faction);
        return true;
    }

    private void doSetAt(Set<PS> chunks, MPlayer player, com.massivecraft.factions.entity.Faction newFaction) {
        String formatOne = "<h>%s<i> %s <h>%d <i>chunk %s<i>.";
        String formatMany = "<h>%s<i> %s <h>%d <i>chunks near %s<i>.";

        HashMap<com.massivecraft.factions.entity.Faction, Set<PS>> oldFactionChunks = new HashMap<com.massivecraft.factions.entity.Faction, Set<PS>>();

        for (PS chunk : chunks) {
            com.massivecraft.factions.entity.Faction factionHere = BoardColl.get().getFactionAt(chunk);
            Set<PS> claims = new HashSet<PS>();

            if (oldFactionChunks.containsKey(factionHere)) {
                claims = oldFactionChunks.get(factionHere);
                claims.add(chunk);
            }

            oldFactionChunks.put(factionHere, claims);
            BoardColl.get().setFactionAt(chunk, newFaction);
        }

        // Inform
        for (Map.Entry<com.massivecraft.factions.entity.Faction, Set<PS>> entry : oldFactionChunks.entrySet()) {
            final com.massivecraft.factions.entity.Faction oldFaction = entry.getKey();
            final Set<PS> oldChunks = entry.getValue();
            final PS oldChunk = oldChunks.iterator().next();
            final Set<MPlayer> informees = MPlayer.getClaimInformees(player, oldFaction, newFaction);
            final EventFactionsChunkChangeType type = EventFactionsChunkChangeType.get(oldFaction, newFaction, player.getFaction());

            String chunkString = oldChunk.toString(PSFormatHumanSpace.get());
            String typeString = type.past;

            for (MPlayer informee : informees) {
                informee.msg((oldChunks.size() == 1 ? formatOne : formatMany), player.describeTo(informee, true), typeString, oldChunks.size(), chunkString);
                informee.msg("  <h>%s<i> --> <h>%s", oldFaction.describeTo(informee, true), newFaction.describeTo(informee, true));
            }
        }
    }

}