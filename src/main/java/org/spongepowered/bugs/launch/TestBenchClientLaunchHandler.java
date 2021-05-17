package org.spongepowered.bugs.launch;

import com.google.auto.service.AutoService;
import cpw.mods.modlauncher.api.ILaunchHandlerService;

@AutoService(ILaunchHandlerService.class)
public class TestBenchClientLaunchHandler extends AbstractTestBenchLaunchHandlerService {

    @Override
    public String name() {
        return "test_bench_client";
    }

    @Override
    protected void launchService0(final String[] args, final ClassLoader loader) throws Exception {
        Class.forName("net.minecraft.client.main.Main", true, loader)
            .getMethod("main", String[].class)
            .invoke(null, (Object) args);
    }
}
