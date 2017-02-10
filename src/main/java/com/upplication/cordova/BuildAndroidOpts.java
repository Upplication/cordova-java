package com.upplication.cordova;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuildAndroidOpts extends BuildOpts {
    /**
     * will build project with gradle (default)
     */
    private boolean gradle;
    /**
     * will build project with ant
     */
    private boolean ant;
    /**
     * Overrides the versionCode set in AndroidManifest.xml
     */
    private Integer versionCode;
    /**
     * Override minSdkVersion for this build. Useful for uploading multiple APKs. Requires --gradle.
     */
    private Integer minSdkVersion;
    /**
     * Key store used to build a signed archive. (Required)
     */
    private File keystore;
    /**
     * Alias for the key store. (Required)
     */
    private String alias;
    /**
     * Password for the key store. (Optional - prompted)
     */
    private String storePassword;
    /**
     * Password for the key. (Optional - prompted)
     */
    private String password;
    /**
     * Type of the keystore. (Optional)
     */
    private String keystoreType;

    private GArg[] gargs;

    public static BuildAndroidOpts create() {
        return new BuildAndroidOpts();
    }

    public boolean isGradle() {
        return gradle;
    }

    public BuildAndroidOpts withGradle(boolean gradle) {
        this.gradle = gradle;
        return this;
    }

    public boolean isAnt() {
        return ant;
    }

    public BuildAndroidOpts withAnt(boolean ant) {
        this.ant = ant;
        return this;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public BuildAndroidOpts withVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public Integer getMinSdkVersion() {
        return minSdkVersion;
    }

    public BuildAndroidOpts withMinSdkVersion(Integer minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
        return this;
    }

    public File getKeystore() {
        return keystore;
    }

    public BuildAndroidOpts withKeystore(File keystore) {
        this.keystore = keystore;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public BuildAndroidOpts withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public BuildAndroidOpts withStorePassword(String storePassword) {
        this.storePassword = storePassword;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BuildAndroidOpts withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getKeystoreType() {
        return keystoreType;
    }

    public BuildAndroidOpts withKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
        return this;
    }

    /**
     * Add extra gradle args
     * @param args only allower one, i think is a cordova limitation
     * @return this
     */
    public BuildAndroidOpts withGradleArgs(GArg args) {
        this.gargs = new GArg[]{args};
        return this;
    }

    @Override
    public List<String> toList() {
        List<String> commands = super.toList();

        List<String> androidCommands = new ArrayList<>();

        if (ant)
            androidCommands.add("--ant");

        if (gradle)
            androidCommands.add("--gradle");

        if (versionCode != null)
            androidCommands.add("--versionCode=" + versionCode);

        if (minSdkVersion != null)
            androidCommands.add("--minSdkVersion=" + minSdkVersion);

        if (keystore != null)
            androidCommands.add("--keystore=" + keystore.getAbsolutePath());

        if (alias != null)
            androidCommands.add("--alias=" + alias);

        if (password != null)
            androidCommands.add("--password=" + password);

        if (storePassword != null)
            androidCommands.add("--storePassword=" + storePassword);

        if (keystoreType != null)
            androidCommands.add("--keystoreType=" + keystoreType);


        if (gargs != null){
            StringBuilder gargBuilder = new StringBuilder();
            for (GArg garg : gargs) {
                gargBuilder.append(garg.get() + " "); // i dont know how to pass to gradle an array of gradleArgs.
            }

            androidCommands.add("--gradleArg=" + gargBuilder.toString());
        }

        if (!androidCommands.isEmpty()){
            androidCommands.add(0, "--");
        }

        commands.addAll(androidCommands);

        return commands;
    }

    public static class GArg {

        private String commandArg;

        /**
         * If this is set, then multiple APK files will be generated: One per native platform supported by library projects (x86, ARM, etc).
         * This can be important if your project uses large native libraries, which can drastically increase the size of the generated APK. If not set, then a single APK will be generated which can be used on all devices
         * @param buildMultipleApk boolean
         * @return this
         */
        public static GArg buildMultipleApk(boolean buildMultipleApk) {
            return new GArg("-PcdvBuildMultipleApk=" + buildMultipleApk);
        }

        /**
         * Overrides the automatically detected android.buildToolsVersion value
         * @param buildToolsVersion String like android-N, example: android-23
         * @return this
         */
        public static GArg buildToolsVersion(String buildToolsVersion) {
            return new GArg("-PcdvBuildToolsVersion=" + buildToolsVersion);
        }

        /**
         * Overrides the automatically detected android.compileSdkVersion value
         * @param compileSdkVersion String like 23, 22,
         * @return this
         */
        public static GArg compileSdkVersion(String compileSdkVersion) {
            return new GArg("-PcdvCompileSdkVersion=" + compileSdkVersion);
        }

        private GArg(String commandArg) {
            this.commandArg = commandArg;
        }

        private String get() {
            return commandArg;
        }
    }
}
