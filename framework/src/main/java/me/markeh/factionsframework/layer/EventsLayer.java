package me.markeh.factionsframework.layer;

import org.bukkit.event.Listener;

import me.markeh.factionsframework.enums.FactionsVersion;

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
			try {
				switch (FactionsVersion.get()) {
					case Factions_1_6 :
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_1_6.Events_1_6").newInstance();
						break;
					case Factions_2_6 :
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_6.Events_2_6").newInstance();
						break;
					case Factions_2_8_2 :
					case Factions_2_8_6 :
					case Factions_2_8_7 :
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_8_6.Events_2_8_6").newInstance();
						break;
					default :
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return layer;
	}
	
}