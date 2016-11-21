package com.upplication.cordova.util;

import com.upplication.cordova.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Processes the cordova config.xml file
 */
public class ConfigProcessorTransaction implements Closeable, IConfigProcessor {

    private Path configFile;
    private Document document;
    private ConfigProcessorDocument configProcessorDocument;

    /**
     * Open the configFile
     * @param configFile Path mandatory
     * @throws IOException
     */
    public ConfigProcessorTransaction(Path configFile) throws IOException {
        this.configFile = configFile;
        this.document = openConfig(configFile);
        this.configProcessorDocument = new ConfigProcessorDocument(document);
    }


    /**
     * Opens the config file
     *
     * @param configFile Config file path
     * @return Config document
     * @throws IOException
     */
    private Document openConfig(Path configFile) throws IOException {
        // TODO: http://stackoverflow.com/questions/128038/how-can-i-lock-a-file-using-java-if-possible
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
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        try (OutputStream out = Files.newOutputStream(this.configFile, StandardOpenOption.TRUNCATE_EXISTING)) {
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.document);
            StreamResult result = new StreamResult(out);

            transformer.transform(source, result);
        }
        catch (TransformerException e){
            throw new IOException(e);
        }
    }

    @Override
    public void setVersion(String version, String iosCfBundleVersion, Integer androidVersionCode) throws IOException {
        configProcessorDocument.setVersion(version, iosCfBundleVersion, androidVersionCode);
    }

    @Override
    public Version getVersion() throws IOException {
        return configProcessorDocument.getVersion();
    }

    @Override
    public void setName(String name) throws IOException {
        configProcessorDocument.setName(name);
    }

    @Override
    public String getName() throws IOException {
        return configProcessorDocument.getName();
    }

    @Override
    public void setDescription(String description) throws IOException {
        configProcessorDocument.setDescription(description);
    }

    @Override
    public String getDescription() throws IOException {
        return configProcessorDocument.getDescription();
    }

    @Override
    public void setAuthorName(String authorName) throws IOException {
        configProcessorDocument.setAuthorName(authorName);
    }

    @Override
    public String getAuthorName() throws IOException {
        return configProcessorDocument.getAuthorName();
    }

    @Override
    public void setAuthorEmail(String authorEmail) throws IOException {
        configProcessorDocument.setAuthorEmail(authorEmail);
    }

    @Override
    public String getAuthorEmail() throws IOException {
        return configProcessorDocument.getAuthorEmail();
    }

    @Override
    public void setAuthorHref(String authorHref) throws IOException {
        configProcessorDocument.setAuthorHref(authorHref);
    }

    @Override
    public String getAuthorHref() throws IOException {
        return configProcessorDocument.getAuthorHref();
    }

    @Override
    public void addAccess(String accessOrigin, String launchExternal, String subdomains) throws IOException {
        configProcessorDocument.addAccess(accessOrigin, launchExternal, subdomains);
    }

    @Override
    public List<Access> getAccess() throws IOException {
        return configProcessorDocument.getAccess();
    }

    @Override
    public void addAllowNavigation(String href) throws IOException {
        configProcessorDocument.addAllowNavigation(href);
    }

    @Override
    public List<AllowNavigation> getAllowNavigation() throws IOException {
        return configProcessorDocument.getAllowNavigation();
    }

    @Override
    public void addPreference(String platform, String name, String value) throws IOException {
        configProcessorDocument.addPreference(platform, name, value);
    }

    @Override
    public List<Preference> getPreferences(String platform) throws IOException {
        return configProcessorDocument.getPreferences(platform);
    }

    @Override
    public void addIcon(String platform, String src, Integer width, Integer height, String density) throws IOException {
        configProcessorDocument.addIcon(platform, src, width, height, density);
    }

    @Override
    public List<Icon> getIcons(String platform) throws IOException {
        return configProcessorDocument.getIcons(platform);
    }

    @Override
    public void addSplash(String platform, String src, Integer width, Integer height, String density) throws IOException {
        configProcessorDocument.addSplash(platform, src, width, height, density);
    }

    @Override
    public List<Splash> getSplashs(String platform) throws IOException {
        return configProcessorDocument.getSplashs(platform);
    }

    @Override
    public void add(String xml) throws IOException {
        configProcessorDocument.add(xml);
    }
}
