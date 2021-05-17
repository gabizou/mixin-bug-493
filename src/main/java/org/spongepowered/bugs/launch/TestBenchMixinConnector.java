package org.spongepowered.bugs.launch;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class TestBenchMixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration("mixins.bug.core.json");
    }
}
