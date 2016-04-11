package me.markeh.factionsframework.layers;

import org.bukkit.event.Listener;

import me.markeh.factionsframework.enums.FactionsVersion;
import me.markeh.factionsframework.layers.events.Events_1_6;
import me.markeh.factionsframework.layers.events.Events_2_6;
import me.markeh.factionsframework.layers.events.Events_2_8_6;
import me.markeh.factionsframework.layers.events.Events_2_8_7;

public abstract class EventsLayer implements Listener {
	
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private static EventsLayer layer = null;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public static EventsLayer get() {
		if (layer == null) {
			switch (FactionsVersion.get()) {
				case Factions_1_6 :
					layer = new Events_1_6();
					break;
				case Factions_2_6 :
					layer = new Events_2_6();
					break;
				case Factions_2_8_2 :
				case Factions_2_8_6 :
					layer = new Events_2_8_6();
					break;
				case Factions_2_8_7 :
					layer = new Events_2_8_7();
					break;
				default :
					break;
			}
		}
		
		return layer;
	}
	
}
