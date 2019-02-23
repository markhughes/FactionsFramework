package me.markeh.factionsframework.layer.layer_2_14;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

import java.util.Map.Entry;

public class Command_2_14 extends com.massivecraft.factions.cmd.FactionsCommand implements CommandBase {


    private FactionsCommand command;

    public Command_2_14(FactionsCommand command) {
        this.command = command;

        this.aliases.addAll(command.getAliases());
        this.setDesc(command.getDescription());

        try {
            // Register the required arguments
            for (String reqArg : command.getRequiredArguments()) {
                this.addParameter(TypeString.get(), reqArg);
            }

            // Register the optional arguments
            for (Entry<String, String> arg : command.getOptionalArguments().entrySet()) {
                this.addParameter(TypeString.get(), arg.getKey(), arg.getValue());
            }

        } catch (Exception e) {
            FactionsFramework.get().err(e);
        }

        this.setOverflowSensitive(!command.overflowAllowed());

        if (command.getSubCommands().size() > 0) {
            for (FactionsCommand subCommand : command.getSubCommands()) {
                this.addChild(new Command_2_14(subCommand));
            }
        }

    }

    @Override
    public void perform() throws MassiveException {
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

}