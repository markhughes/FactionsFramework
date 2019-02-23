package me.markeh.factionsframework.layer.layer_2_14;

import com.massivecraft.factions.cmd.CmdFactions;
import com.massivecraft.massivecore.command.MassiveCommandHelp;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.entities.Handler;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CommandManager_2_14 extends FactionsCommandManager {

    // -------------------------------------------------- //
    // FIELDS
    // -------------------------------------------------- //

    private Map<FactionsCommand, Command_2_14> cmdMap = new HashMap<>();

    // -------------------------------------------------- //
    // METHODS
    // -------------------------------------------------- //

    @Override
    public void add(FactionsCommand command) {
        Command_2_14 nativeCommand = new Command_2_14(command);

        this.cmdMap.put(command, nativeCommand);

        CmdFactions.get().addChild(nativeCommand);
    }

    @Override
    public void remove(FactionsCommand command) {
        CmdFactions.get().removeChild(this.cmdMap.get(command));
    }

    @Override
    public void removeAll() {
        for (FactionsCommand command : new HashSet<FactionsCommand>(this.cmdMap.keySet())) {
            this.remove(command);
        }
    }

    @Override
    public void showHelpFor(FactionsCommand command, CommandSender sender) {
        MassiveCommandHelp.get().execute(sender, command.getArgs());
    }

    @Override
    public Handler asHandler() {
        return this;
    }

}