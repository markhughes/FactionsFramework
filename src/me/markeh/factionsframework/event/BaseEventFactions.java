package me.markeh.factionsframework.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class BaseEventFactions<T extends BaseEventFactions<T>> extends Event {
		
	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private Boolean cancelled = false;
	private HandlerList handlers = new HandlerList();
	
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
	
	public final HandlerList getHandlers() {
		return this.handlers;
	}

	public final HandlerList getHandlerList() {
		return this.handlers;
	}
	
}
