package com.upplication.cordova;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuildOpts {

    /**
     * Platform name(s) to build. If not specified, all platforms are built.
     */
    private Platform[] platforms;
    /**
     * Perform a debug build. This typically translates to debug mode for the underlying platform being built.
     */
    private boolean debug;
    /**
     * Perform a release build. This typically translates to release mode for the underlying platform being built.
     */
    private boolean release;
    /**
     * Build it for a device
     */
    private boolean device;
    /**
     * Build it for an emulator. In particular, the platform architecture might be different for a device Vs emulator.
     */
    private boolean emulator;
    /**
     * Default: build.json in cordova root directory.
     * Use the specified build configuration file. build.json file is used to specify paramaters to customize the app build process esecially related to signing the package.
     */
    private File configFile;
    /**
     * Compile plugin JS at build time using browserify instead of runtime
     */
    private boolean browserify;

    public static BuildOpts create() {
        return new BuildOpts();
    }

    public boolean isDebug() {
        return debug;
    }

    public BuildOpts withDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public boolean isRelease() {
        return release;
    }

    public BuildOpts withRelease(boolean release) {
        this.release = release;
        return this;
    }

    public boolean isDevice() {
        return device;
    }

    public BuildOpts withDevice(boolean device) {
        this.device = device;
        return this;
    }

    public boolean isEmulator() {
        return emulator;
    }

    public BuildOpts withEmulator(boolean emulator) {
        this.emulator = emulator;
        return this;
    }

    public File getConfigFile() {
        return configFile;
    }

    public BuildOpts withConfigFile(File configFile) {
        this.configFile = configFile;
        return this;
    }

    public boolean isBrowserify() {
        return browserify;
    }

    public BuildOpts withBrowserify(boolean browserify) {
        this.browserify = browserify;
        return this;
    }

    public Platform[] getPlatforms() {
        return platforms;
    }

    public BuildOpts withPlatforms(Platform ... platforms) {
        this.platforms = platforms;
        return this;
    }

    public List<String> toList() {
        List<String> commands = new ArrayList<>();

        if (platforms != null) {
            for (Platform platform : platforms) {
                commands.add(platform.name().toLowerCase());
            }
        }

        if (debug)
            commands.add("--debug");

        if (release)
            commands.add("--release");

        if (device)
            commands.add("--device");

        if (emulator)
            commands.add("--emulator");

        if (browserify)
            commands.add("--browserify");

        if (configFile != null) {
            commands.add("--buildConfig " + configFile.getAbsolutePath());
        }

        return commands;
    }
}
