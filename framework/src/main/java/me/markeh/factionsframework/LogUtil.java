package me.markeh.factionsframework;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.markeh.factionsframework.enums.FactionsVersion;

public class LogUtil {
	
	// -------------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------------- //
	
	public LogUtil(JavaPlugin plugin, ChatColor colour) {
		this.plugin = plugin;
		this.colour = colour;
		this.console = plugin.getServer().getConsoleSender();
		
		this.logsFolder = Paths.get(this.plugin.getDataFolder().getAbsolutePath(), "logs");
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private JavaPlugin plugin;
	private ChatColor colour;
	private ConsoleCommandSender console;
	private Path logsFolder;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public String getPluginName() {
		return this.plugin.getDescription().getName();
	}
	
	public ChatColor getColour() {
		return this.colour;
	}
	
	public ConsoleCommandSender getConsole() {
		return this.console;
	}
	
	public Path getLogsFolder() {
		return this.logsFolder;
	}
	
	public void log(String msg) {
		this.getConsole().sendMessage(this.colour + "[" + this.getPluginName() + "] " + ChatColor.WHITE + msg );
	}
	
	public void err(Exception e) {
					
		this.getConsole().sendMessage(
				ChatColor.RED + "[" + this.getPluginName() + "] " + 
				ChatColor.MAGIC + "!!" + ChatColor.RESET + ChatColor.RED + "ERROR" + ChatColor.MAGIC + "!!" + ChatColor.RESET + 
				ChatColor.GOLD + " An internal error occured! " + e.toString());
		
		e.printStackTrace();
		
		Throwable cause = null;
		
		cause = e.getCause();
		
		while (cause != null) {
			this.getConsole().sendMessage(ChatColor.GOLD + "caused by " + cause.getMessage());
			cause.printStackTrace();
			
			cause = cause.getCause();
		}
		
		String factionsversion = FactionsVersion.get().name();
		String frameworkversion = FactionsFramework.get().getDescription().getVersion();
		String serverversion = FactionsFramework.get().getServer().getVersion();
		
		this.getConsole().sendMessage(ChatColor.GOLD + "version_info: factionsversion=" + factionsversion + ",frameworkversion=" + frameworkversion + ",serverversion=" + serverversion + "//");
	}
	
}
