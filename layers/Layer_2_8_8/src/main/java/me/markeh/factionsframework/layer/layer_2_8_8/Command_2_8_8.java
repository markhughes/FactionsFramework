package me.markeh.factionsframework.layer.layer_2_8_8;

import java.util.Map.Entry;

import com.massivecraft.massivecore.MassiveException;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

public class Command_2_8_8 extends com.massivecraft.factions.cmd.FactionsCommand implements CommandBase {

	// ---------------------------------------- //
	// FIELDS
	// ---------------------------------------- //

	private FactionsCommand command;

	// ---------------------------------------- //
	// CONSTRUCTOR
	// ---------------------------------------- //

	public Command_2_8_8(FactionsCommand command) {
		this.command = command;

		this.aliases.addAll(command.getAliases());
		this.setDesc(command.getDescription());

		try {
			// Register the required arguments
			for (String reqArg : command.getRequiredArguments()) {
				this.addParameter(com.massivecraft.massivecore.command.type.primitive.TypeString.get(), reqArg);
			}

			// Register the optional arguments
			for (Entry<String, String> arg : command.getOptionalArguments().entrySet()) {
				this.addParameter(com.massivecraft.massivecore.command.type.primitive.TypeString.get(), arg.getKey(), arg.getValue());
			}

		} catch (Exception e) {
			FactionsFramework.get().err(e);
		}

		this.overflowSensitive = ! command.overflowAllowed();


		if (command.getSubCommands().size() > 0) {
			for (FactionsCommand subCommand : command.getSubCommands()) {
				this.addChild(new Command_2_8_8(subCommand));
			}
		}

	}

	// ---------------------------------------- //
	// METHODS
	// ---------------------------------------- //

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
