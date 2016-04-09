package me.markeh.factionsframework.layers.commandmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Conf;
import com.massivecraft.factions.P;
import com.massivecraft.factions.integration.Econ;

import me.markeh.factionsframework.command.FactionsCommand;
import me.markeh.factionsframework.command.FactionsCommandManager;
import me.markeh.factionsframework.layers.commands.Command_1_6;

public class CommandManager_1_6 extends FactionsCommandManager {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Map<FactionsCommand, Command_1_6> cmdMap = new HashMap<FactionsCommand, Command_1_6>();
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@Override
	public void add(FactionsCommand command) {
		// If its added already don't add it again  
		if (this.cmdMap.containsKey(command)) return;
		
		// Create the command and add it to our map
		Command_1_6 originalCommand = new Command_1_6(command);
		this.cmdMap.put(command, originalCommand);
		
		// Add it to Factions 
		P.p.cmdBase.addSubCommand(originalCommand);
		
		//this.updateHelp();
	}

	@Override
	public void remove(FactionsCommand command) {
		// Only attempt to remove if it has been added 
		if ( ! this.cmdMap.containsKey(command)) return;
		
		// Remove it from Factions
		P.p.cmdBase.subCommands.remove(this.cmdMap.get(command));
		
		// Remove it from out map
		this.cmdMap.remove(command);
		
		//this.updateHelp();
	}

	@Override
	public void removeAll() {
		for (FactionsCommand command : this.cmdMap.keySet()) {
			this.remove(command);
		}
	}
	
	@Override
	public void showHelpFor(FactionsCommand command, CommandSender sender) {
		P.p.cmdAutoHelp.execute(sender, command.getArgs(), cmdMap.get(command).commandChain);		
	}
	
	private ArrayList<ArrayList<String>> pagesBackup = null;
	
	public void updateHelp() {
		if ( ! P.p.getConfig().getBoolean("use-old-help", true)) return;
		
		if (pagesBackup == null) {
			if (P.p.cmdBase.cmdHelp.helpPages == null) {
				P.p.cmdBase.cmdHelp.updateHelp();
			}
			
			pagesBackup = new ArrayList<ArrayList<String>>(P.p.cmdBase.cmdHelp.helpPages);
		}
		
		P.p.cmdBase.cmdHelp.helpPages.clear();
		
		int pageOverride = 6;
		if (Econ.isSetup() && Conf.econEnabled && Conf.bankEnabled) pageOverride++;
		
		int page = 1;
		for (ArrayList<String> lines : pagesBackup) {
			// Pages 6 is where the info blocks start, so we'll inject
			// into the help before that
			if (page == pageOverride) {
				ArrayList<String> newLines = new ArrayList<String>();
				newLines.add("FactionsFramework!");
				
				for (Command_1_6 command : this.cmdMap.values()) {
					newLines.add(command.getUseageTemplate(true));
					
					if (newLines.size() >= 6) {
						P.p.cmdBase.cmdHelp.helpPages.add(newLines);
						newLines.clear();
					}
				}
				
				if ( ! newLines.isEmpty()) {
					P.p.cmdBase.cmdHelp.helpPages.add(newLines);
					newLines.clear();
				}
				
			} 
			
			// Add back the old page 
			P.p.cmdBase.cmdHelp.helpPages.add(lines);
			page++;
		}
	}
	
}
