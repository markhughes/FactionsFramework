package me.markeh.factionsframework.layer.layer_2_6;

import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandInformation;
import me.markeh.factionsframework.entities.FPlayers;
import me.markeh.factionsframework.layer.CommandBase;

public class Command_2_6 extends FCommand implements CommandBase {

	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //

	public Command_2_6(FactionsCommand command) {
		this.command = command;

		this.aliases.addAll(command.getAliases());
		this.requiredArgs.addAll(command.getRequiredArguments());
		this.optionalArgs.putAll(command.getOptionalArguments());
		this.setHelp(this.command.getDescription());

		this.errorOnToManyArgs = ! command.overflowAllowed();
		
		this.addRequirements(ReqHasPerm.get(command.getPermission()));
		
		if (command.getSubCommands().size() > 0) {
			for (FactionsCommand subCommand : command.getSubCommands()) {
				this.subCommands.add(new Command_2_6(subCommand));
			}
		}

		this.commandChain.add(this);
	}

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //

	private FactionsCommand command;

	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //

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
			FactionsFramework.get().logError(e);
		}
	}

	@Override
	public FactionsCommand getAsFactionsCommand() {
		return this.command;
	}

}
