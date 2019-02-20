package me.markeh.factionsframework.layer.layer_0_2_2;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FPlayers_0_2_2 extends FPlayers {

    private Map<String, FPlayer> fplayersMap;

    public FPlayers_0_2_2() {
        fplayersMap = new HashMap<>();
    }

    @Override
    public FPlayer get(CommandSender sender) {
        if (!(sender instanceof Player)) return get((UUID) null);
        else return get(((Player) sender).getUniqueId());
    }

    @Override
    public FPlayer get(UUID uuid) {
        String id = uuid == null ? "@console" : uuid.toString();

        if (!this.fplayersMap.containsKey(id)) {
            this.fplayersMap.put(id, new FPlayer_0_2_2(id));
        }

        return this.fplayersMap.get(id);
    }

    @Override
    public FPlayer get(String name) {
        return this.get(Bukkit.getPlayer(name).getUniqueId());
    }

}