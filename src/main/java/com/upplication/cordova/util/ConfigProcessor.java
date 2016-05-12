package com.upplication.cordova.util;

import com.upplication.cordova.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes the cordova config.xml file
 */
public class ConfigProcessor implements IConfigProcessor{

    private Path configFile;

    public ConfigProcessor(Path config) {
        this.configFile = config;
    }

    /**
     * Sets the version of the application in the config.xml file
     *
     * @param version            Version
     * @param iosCfBundleVersion ios Version, String, optional can be null
     * @param androidVersionCode android Version, Integer, optional can be null
     * @throws IOException
     */
    public void setVersion(String version, String iosCfBundleVersion, Integer androidVersionCode) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).setVersion(version, iosCfBundleVersion, androidVersionCode);

        saveConfig(configFile, document);
    }

    @Override
    public Version getVersion() throws IOException {
        Document document = openConfig(configFile);
        return new ConfigProcessorDocument(document).getVersion();
    }


    @Override
    public void setName(String name) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).setName(name);

        saveConfig(configFile, document);
    }


    @Override
    public String getName() throws IOException {
        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getName();
    }

    @Override
    public void setDescription(String description) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).setDescription(description);

        saveConfig(configFile, document);
    }

    @Override
    public String getDescription() throws IOException {
        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getDescription();
    }

    @Override
    public void setAuthorName(String authorName) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).setAuthorName(authorName);

        saveConfig(configFile, document);
    }

    @Override
    public String getAuthorName() throws IOException {
        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getAuthorName();
    }

    @Override
    public void setAuthorEmail(String authorEmail) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).setAuthorEmail(authorEmail);

        saveConfig(configFile, document);
    }

    @Override
    public String getAuthorEmail() throws IOException {
        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getAuthorEmail();
    }

    @Override
    public void setAuthorHref(String authorHref) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).setAuthorHref(authorHref);

        saveConfig(configFile, document);
    }

    @Override
    public String getAuthorHref() throws IOException {
        Document document = openConfig(configFile);
        return new ConfigProcessorDocument(document).getAuthorHref();
    }

    @Override
    public void addAccess(String accessOrigin, String launchExternal, String subdomains) throws IOException {

        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).addAccess(accessOrigin, launchExternal, subdomains);

        saveConfig(configFile, document);
    }

    @Override
    public List<Access> getAccess() throws IOException {

        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getAccess();
    }

    @Override
    public void addAllowNavigation(String href) throws IOException {

        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).addAllowNavigation(href);

        saveConfig(configFile, document);
    }

    @Override
    public List<AllowNavigation> getAllowNavigation() throws IOException {

        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getAllowNavigation();
    }

    @Override
    public void addPreference(String platform, String name, String value) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).addPreference(platform, name, value);

        saveConfig(configFile, document);
    }

    @Override
    public List<Preference> getPreferences(String platform) throws IOException {
        Document document = openConfig(configFile);
        return new ConfigProcessorDocument(document).getPreferences(platform);
    }

    @Override
    public void addIcon(String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).addIcon(platform, src, width, height, density);

        saveConfig(configFile, document);
    }

    @Override
    public List<Icon> getIcons(String platform) throws IOException {
        Document document = openConfig(configFile);
        return new ConfigProcessorDocument(document).getIcons(platform);
    }

    @Override
    public void addSplash(String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

        new ConfigProcessorDocument(document).addSplash(platform, src, width, height, density);

        saveConfig(configFile, document);
    }

    @Override
    public List<Splash> getSplashs(String platform) throws IOException {
        Document document = openConfig(configFile);

        return new ConfigProcessorDocument(document).getSplashs(platform);
    }

    private Document openConfig(Path configFile) throws IOException {
        try (InputStream stream = Files.newInputStream(configFile)){
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(stream);
        }
        catch (ParserConfigurationException | SAXException e){
            throw new IOException(e);
        }
    }

    /**
     * Saves the config file
     *
     * @param configFile Config file path
     * @param newContent New content for the config file
     * @throws IOException
     */
    private void saveConfig(Path configFile, Document newContent) throws IOException {
        try (OutputStream out = Files.newOutputStream(configFile, StandardOpenOption.TRUNCATE_EXISTING)) {
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(newContent);
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
        }
        catch (TransformerException e){
            throw new IOException(e);
        }
    }
}
