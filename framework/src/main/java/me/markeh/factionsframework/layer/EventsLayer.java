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
					case Factions_1_8 :
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_1_8.Events_1_8").newInstance();
						break;
					case Factions_2_6 :
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_6.Events_2_6").newInstance();
						break;
					case Factions_2_7:
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_7.Events_2_7").newInstance();
						break;
					case Factions_2_8_2 :
					case Factions_2_8_6 :
					case Factions_2_8_7 :
					case Factions_2_8_8 :
					case Factions_2_8_16 :
						layer = (EventsLayer) Class.forName("me.markeh.factionsframework.layer.layer_2_8_6.Events_2_8_6").newInstance();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return layer;
	}
	
	public static void overrideEventsLayer(EventsLayer eventsLayer) {
		layer = eventsLayer;
	}
	
}
