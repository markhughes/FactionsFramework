package me.markeh.factionsframework.layer.layer_0_2_2;

import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.zcore.util.TL;
import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

public class Command_0_2_2 extends FCommand implements CommandBase {

    private FactionsCommand command;

    public Command_0_2_2(FactionsCommand command) {
        this.command = command;

        this.aliases.addAll(command.getAliases());
        this.requiredArgs.addAll(command.getRequiredArguments());
        this.optionalArgs.putAll(command.getOptionalArguments());

        this.setHelpShort(this.command.getDescription());

        this.errorOnToManyArgs = !command.overflowAllowed();

        this.permission = command.getPermission();

        this.setHelpShort(this.command.getDescription());

        if (command.getSubCommands().size() > 0) {
            for (FactionsCommand subCommand : command.getSubCommands()) {
                this.subCommands.add(new Command_0_2_2(subCommand));
            }
        }

        this.commandChain.add(this);
    }

    @Override
    public void perform() {
        // Create an information block
        FactionsCommandInformation info = new FactionsCommandInformation();
        info.setArgs(this.args);
        info.setFPlayer(FPlayers.getBySender(this.sender));
        info.setSender(this.sender);

        // Execute it with that information
        try {
            command.executeWith(info);
        } catch (Exception e) {
            FactionsFramework.get().err(e);
        }
    }

    @Override
    public FactionsCommand getAsFactionsCommand() {
        return this.command;
    }

    // FactionsUUID specific, is required - but useless
    public TL getUsageTranslation() {
        return null;
    }

}