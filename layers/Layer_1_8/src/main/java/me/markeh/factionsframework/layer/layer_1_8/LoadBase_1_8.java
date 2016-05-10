package me.markeh.factionsframework.layer.layer_1_8;

import com.massivecraft.factions.Patch;

import me.markeh.factionsframework.FactionsFramework;
import me.markeh.factionsframework.layer.LoadBase;

public class LoadBase_1_8 extends LoadBase {

	@Override
	public void enabled() {
		try {
			String[] v = Patch.VERSION.split(".");
			
			Integer major = Integer.valueOf(v[0]);
			String preMinor = (v[1]);
			Integer minor = 0;
			
			if (preMinor.indexOf("-") > 0) {
				minor = Integer.valueOf(preMinor.split("-")[0]);
			} else {
				minor = Integer.valueOf(preMinor);
			}
			
			if (major == 1 && minor < 3) {
				FactionsFramework.get().log("Warning! Your version of FactionsOne does not yet support all the features we need to run FactionsFramework!");
				FactionsFramework.get().log("Please upgrade FactionsOne to at least 1.3!");
				
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}

}
