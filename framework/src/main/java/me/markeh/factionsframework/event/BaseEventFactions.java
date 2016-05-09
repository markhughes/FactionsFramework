package me.markeh.factionsframework.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public abstract class BaseEventFactions<T extends BaseEventFactions<T>> extends Event {
		
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Boolean cancelled = false;
	
	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public final T call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
		return (T) this;
	}
	
	public final Boolean isCancelled() {
		return this.cancelled;
	}
	
	public final void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
		
}
