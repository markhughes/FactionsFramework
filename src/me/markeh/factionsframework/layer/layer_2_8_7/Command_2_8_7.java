package me.markeh.factionsframework.layer.layer_2_8_7;

import java.util.ArrayList;
import java.util.List;

import com.massivecraft.massivecore.command.MassiveCommand;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.layer.layer_2_8_6.Command_2_8_6;

public class Command_2_8_7 extends Command_2_8_6 {

	public Command_2_8_7(FactionsCommand command) {
		super(command);
		
		// TODO: should we switch 2_8_6 to 2_8_7? Not really required 
		/*
		List<MassiveCommand> children = new ArrayList<MassiveCommand>(this.getChildren());
		for (MassiveCommand cmd : children) {
			this.removeChild(cmd);
		}
		
		for (FactionsCommand subCommand : command.getSubCommands()) {
			this.addChild(new Command_2_8_7(subCommand));
		}
		*/
	}

}
