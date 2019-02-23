package me.markeh.factionsframework.layer.layer_2_14;

import me.markeh.factionsframework.entities.FPlayer;
import me.markeh.factionsframework.entities.FPlayers;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FPlayers_2_14 extends FPlayers {

    private Map<String, FPlayer> fPlayers;

    public FPlayers_2_14() {
        fPlayers = new HashMap<>();
    }

    @Override
    public FPlayer get(CommandSender sender) {
        if (!(sender instanceof Player)) return get((UUID) null);
        return get(((Player) sender).getUniqueId());
    }

    @Override
    public FPlayer get(UUID uuid) {

        String id;

        if (uuid == null) {
            id = "@console";
        } else {
            id = uuid.toString();
        }

        if (!fPlayers.containsKey(id)) {
            fPlayers.put(id, new FPlayer_2_14(id));
        }

        return fPlayers.get(id);

    }

    @Override
    public FPlayer get(String name) {
        return get(Bukkit.getPlayer(name).getUniqueId());
    }

}