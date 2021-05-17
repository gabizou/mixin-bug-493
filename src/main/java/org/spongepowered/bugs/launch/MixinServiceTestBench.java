package org.spongepowered.bugs.launch;

import com.google.auto.service.AutoService;
import org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher;
import org.spongepowered.asm.util.Constants;

@AutoService(IMixinService.class)
public class MixinServiceTestBench extends MixinServiceModLauncher {

    private final TestBenchContainerHandle primaryContainer = new TestBenchContainerHandle(this.getName());

    @Override
    public ContainerHandleModLauncher getPrimaryContainer() {
        return this.primaryContainer;
    }

    static class TestBenchContainerHandle extends ContainerHandleModLauncher {

        public TestBenchContainerHandle(final String name) {
            super(name);

            this.setAttribute(Constants.ManifestAttributes.MIXINCONNECTOR, "org.spongepowered.bugs.launch.TestBenchMixinConnector");
        }
    }
}
