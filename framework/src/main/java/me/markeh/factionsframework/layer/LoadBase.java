package me.markeh.factionsframework.layer;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.enums.FactionsVersion;

public abstract class LoadBase {

	private static LoadBase i;
	public static LoadBase get() {
		if (i == null) {
			try {
				if (FactionsVersion.get() == FactionsVersion.Factions_1_8) {
					i = (LoadBase) Class.forName("me.markeh.factionsframework.layer.layer_1_8.LoadBase_1_8").newInstance();
				}
			} catch (Exception e) {
				FactionsFramework.get().logError(e);
			}
		}
		return i;
	}
	
	public abstract void enabled();
	public abstract void disabled();
	
}
