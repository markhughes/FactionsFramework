package me.markeh.factionsframework;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import me.markeh.factionsframework.jsonconf.JSONConf;

public class FrameworkConfig extends JSONConf<FrameworkConfig> {
	
	// -------------------------------------------------- //
	// FETCH INSTANCE
	// -------------------------------------------------- //
	
	private static Path configPath = Paths.get(FactionsFramework.get().getDataFolder().toString(), "config.json");
	private static FrameworkConfig instance;
	public static FrameworkConfig get() {
		if (instance == null) {
			instance = new FrameworkConfig();
			
			try {
				instance.loadFrom(configPath);
				instance.save();
			} catch (IOException e) {
				FactionsFramework.get().err(e);
			}
		}
		
		return instance;
	}
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	// Should metrics be enabled?
	public Boolean metrics = true;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public final Boolean save() {
		try {
			return this.saveTo(configPath);
		} catch (IOException e) {
			FactionsFramework.get().err(e);
		}
		
		return false;
	}
	
}
