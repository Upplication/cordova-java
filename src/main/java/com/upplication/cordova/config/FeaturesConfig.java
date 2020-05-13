package com.upplication.cordova.config;

import com.upplication.cordova.Feature;
import com.upplication.cordova.Platform;
import com.upplication.cordova.Preference;
import com.upplication.cordova.util.IConfigProcessor;

import java.io.IOException;
import java.util.List;

/**
 * cordova.config().features().add("name", Feature.Param.create("name", "value"), Feature.Param.create("name2", "value2"));
 */
public class FeaturesConfig {

    private IConfigProcessor configProcessor;
    private Platform platform;

    /**
     * @param configProcessor IConfigProcessor, mandatory
     * @param platform Platform, can be null
     */
    public FeaturesConfig(IConfigProcessor configProcessor, Platform platform){
        this.configProcessor = configProcessor;
        this.platform = platform;
    }

    public void add(String name, Feature.Param ... params) throws IOException {
        configProcessor.addFeature(getPlatform(), name, params);
    }

    public void add(Feature feature) throws IOException {
        this.add(feature.getName(), feature.getParams());
    }

    public void add(String name, List<Feature.Param> params) throws IOException {
        this.add(name, params.toArray(new Feature.Param[0]));
    }

    public List<Feature> getAll() throws IOException {
        return configProcessor.getFeatures(getPlatform());
    }

    private String getPlatform() {
        if (platform != null) {
            return platform.getName().toLowerCase();
        } else {
            return null;
        }
    }
}