package com.upplication.cordova.util;

import com.upplication.cordova.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


/**
 * Processes the cordova config.xml file
 */
public class ConfigProcessor implements IConfigProcessor{

    private Path configFile;

    private DocumentUtil documentUtil;

    public ConfigProcessor(Path config) {
        this.configFile = config;
        this.documentUtil = new DocumentUtil();
    }

    /**
     * Sets the version of the application in the config.xml file
     *
     * @param version            Version
     * @param iosCfBundleVersion ios Version, String, optional can be null
     * @param androidVersionCode android Version, Integer, optional can be null
     * @throws IOException if the file cant be opened or writted or closed
     */
    public void setVersion(String version, String iosCfBundleVersion, Integer androidVersionCode) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).setVersion(version, iosCfBundleVersion, androidVersionCode);

        saveConfig(configFile, document);
    }

    @Override
    public Version getVersion() throws IOException {
        Document document = openConfig(configFile);
        return getProcessor(document).getVersion();
    }


    @Override
    public void setName(String name) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).setName(name);

        saveConfig(configFile, document);
    }


    @Override
    public String getName() throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getName();
    }

    @Override
    public void setDescription(String description) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).setDescription(description);

        saveConfig(configFile, document);
    }

    @Override
    public String getDescription() throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getDescription();
    }

    @Override
    public void setAuthorName(String authorName) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).setAuthorName(authorName);

        saveConfig(configFile, document);
    }

    @Override
    public String getAuthorName() throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getAuthorName();
    }

    @Override
    public void setAuthorEmail(String authorEmail) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).setAuthorEmail(authorEmail);

        saveConfig(configFile, document);
    }

    @Override
    public String getAuthorEmail() throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getAuthorEmail();
    }

    @Override
    public void setAuthorHref(String authorHref) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).setAuthorHref(authorHref);

        saveConfig(configFile, document);
    }

    @Override
    public String getAuthorHref() throws IOException {
        Document document = openConfig(configFile);
        return getProcessor(document).getAuthorHref();
    }

    @Override
    public void addAccess(String accessOrigin, String launchExternal, String subdomains) throws IOException {

        Document document = openConfig(configFile);

        getProcessor(document).addAccess(accessOrigin, launchExternal, subdomains);

        saveConfig(configFile, document);
    }

    @Override
    public List<Access> getAccess() throws IOException {

        Document document = openConfig(configFile);

        return getProcessor(document).getAccess();
    }

    @Override
    public void addAllowNavigation(String href) throws IOException {

        Document document = openConfig(configFile);

        getProcessor(document).addAllowNavigation(href);

        saveConfig(configFile, document);
    }

    @Override
    public List<AllowNavigation> getAllowNavigation() throws IOException {

        Document document = openConfig(configFile);

        return getProcessor(document).getAllowNavigation();
    }

    @Override
    public void addPreference(String platform, String name, String value) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addPreference(platform, name, value);

        saveConfig(configFile, document);
    }

    @Override
    public List<Preference> getPreferences(String platform) throws IOException {
        Document document = openConfig(configFile);
        return getProcessor(document).getPreferences(platform);
    }

    @Override
    public void addFeature(String platform, String name, Feature.Param... params) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addFeature(platform, name, params);

        saveConfig(configFile, document);
    }

    @Override
    public List<Feature> getFeatures(String platform) throws IOException {
        Document document = openConfig(configFile);
        return getProcessor(document).getFeatures(platform);
    }

    @Override
    public void addIcon(String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addIcon(platform, src, width, height, density);

        saveConfig(configFile, document);
    }

    @Override
    public List<Icon> getIcons(String platform) throws IOException {
        Document document = openConfig(configFile);
        return getProcessor(document).getIcons(platform);
    }

    @Override
    public void addSplash(String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addSplash(platform, src, width, height, density);

        saveConfig(configFile, document);
    }

    @Override
    public List<Splash> getSplashs(String platform) throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getSplashs(platform);
    }

    @Override
    public void addEditConfig(String platform, String file, String target, String mode, String content) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addEditConfig(platform, file, target, mode, content);

        saveConfig(configFile, document);
    }

    @Override
    public List<EditConfig> getEditConfig(String platform) throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getEditConfig(platform);
    }

    @Override
    public void addConfigFile(String platform, String target, String parent, String after, String content) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addConfigFile(platform, target, parent, after, content);

        saveConfig(configFile, document);
    }

    @Override
    public List<ConfigFile> getConfigFile(String platform) throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getConfigFile(platform);
    }

    @Override
    public void addResourceFile(String platform, String src, String target) throws IOException {
        Document document = openConfig(configFile);

        getProcessor(document).addResourceFile(platform, src, target);

        saveConfig(configFile, document);
    }

    @Override
    public List<ResourceFile> getResourceFile(String platform) throws IOException {
        Document document = openConfig(configFile);

        return getProcessor(document).getResourceFile(platform);
    }

    @Override
    public void add(String xml) throws IOException {
        Document document = openConfig(configFile);
        getProcessor(document).add(xml);

        saveConfig(configFile, document);
    }

    //
    // public 4 testing
    //

    public Document openConfig(Path configFile) throws IOException {
        try (InputStream stream = Files.newInputStream(configFile)){
            return documentUtil.newDocumentBuilder().parse(stream);
        }
        catch (SAXException e){
            throw new IOException(e);
        }
    }

    /**
     * Saves the config file
     *
     * @param configFile Config file path
     * @param newContent New content for the config file
     * @throws IOException if the file cant be saved or closed
     */
    public void saveConfig(Path configFile, Document newContent) throws IOException {
        try (OutputStream out = Files.newOutputStream(configFile, StandardOpenOption.TRUNCATE_EXISTING)) {
            out.write(documentUtil.serializer().writeToString(newContent).getBytes());
        }
    }

    //
    public ConfigProcessorDocument getProcessor(Document document) {
        return new ConfigProcessorDocument(document);
    }
}
