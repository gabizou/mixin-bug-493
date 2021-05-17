package org.spongepowered.bugs.launch;

import com.google.auto.service.AutoService;
import cpw.mods.modlauncher.api.ILaunchHandlerService;

@AutoService(ILaunchHandlerService.class)
public class TestBenchServerLaunchHandler extends AbstractTestBenchLaunchHandlerService {

    @Override
    public String name() {
        return "test_bench_server";
    }

    @Override
    protected void launchService0(final String[] args, final ClassLoader loader) throws Exception {
        Class.forName("net.minecraft.server.Main", true, loader)
            .getMethod("main", String[].class)
            .invoke(null, (Object) args);
    }

}
