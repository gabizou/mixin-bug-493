package org.spongepowered.bugs.launch;

import cpw.mods.gross.Java9ClassLoaderUtil;
import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public abstract class AbstractTestBenchLaunchHandlerService implements ILaunchHandlerService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String[] CLASSLOADER_EXCLUSIONS = {
        "org.spongepowered.test.vanillagradle.launch.",
        "org.apache.logging.log4j."
    };

    @Override
    public void configureTransformationClassLoader(final ITransformingClassLoaderBuilder builder) {
        for (final URL url : Java9ClassLoaderUtil.getSystemClassPathURLs()) {
            if (url.toString().contains("mixin") && url.toString().endsWith(".jar")) {
                continue;
            }

            try {
                builder.addTransformationPath(Paths.get(url.toURI()));
            } catch (final URISyntaxException ex) {
                AbstractTestBenchLaunchHandlerService.LOGGER.error("Failed to add Mixin transformation path", ex);
            }
        }
    }

    @Override
    public Callable<Void> launchService(final String[] arguments, final ITransformingClassLoader launchClassLoader) {
        launchClassLoader.addTargetPackageFilter(clazz -> {
            for (final String prefix : AbstractTestBenchLaunchHandlerService.CLASSLOADER_EXCLUSIONS) {
                if (clazz.startsWith(prefix)) {
                    return false;
                }
            }
            return true;
        });

        return () -> {
            this.launchService0(arguments, launchClassLoader.getInstance());
            return null;
        };
    }

    protected abstract void launchService0(final String[] args, final ClassLoader loader) throws Exception;
}
