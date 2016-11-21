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
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes the cordova config.xml file
 */
public interface IConfigProcessor {

    /**
     * Sets the version of the application in the config.xml file
     *
     * @param version            Version
     * @param iosCfBundleVersion ios Version, String, optional can be null
     * @param androidVersionCode android Version, Integer, optional can be null
     * @throws IOException
     */
    void setVersion(String version, String iosCfBundleVersion, Integer androidVersionCode) throws IOException;

    /**
     * Get the version of the application in the config.xml file
     *
     * @return Version never null
     * @throws IOException
     */
    Version getVersion() throws IOException;

    /**
     * Sets the name of the application in the config.xml file
     *
     * @param name          String, mandatory not null
     * @throws IOException
     */
    void setName(String name) throws IOException;

    /**
     * Get the name of the application in the config.xml file
     *
     * @return the name never null
     * @throws IOException
     */
    String getName() throws IOException;

    /**
     * Sets the description of the application in the config.xml file
     *
     * @param description   String, mandatory not null
     * @throws IOException
     */
    void setDescription(String description) throws IOException;

    /**
     * Get the description of the application in the config.xml file
     *
     * @return the description never null
     * @throws IOException
     */
    String getDescription() throws IOException;

    /**
     * Sets the author of the application in the config.xml file
     *
     * @param authorName String, the author name to set
     * @throws IOException
     */
    void setAuthorName(String authorName) throws IOException;

    /**
     * Get the author name of the application in the config.xml file
     *
     * @return the author name as String never null
     * @throws IOException
     */
    String getAuthorName() throws IOException;

    /**
     * Sets the author email of the application in the config.xml file
     *
     * @param authorEmail String the author email
     * @throws IOException
     */
    void setAuthorEmail(String authorEmail) throws IOException;

    /**
     * Get the author email of the application in the config.xml file
     *
     * @return the author email as String never null
     * @throws IOException
     */
    String getAuthorEmail() throws IOException;

    /**
     * Sets the author href of the application in the config.xml file
     *
     * @param authorHref String the author href
     * @throws IOException
     */
    void setAuthorHref(String authorHref) throws IOException;

    /**
     * Get the author href of the application in the config.xml file
     *
     * @return the author href as String never null
     * @throws IOException
     */
    String getAuthorHref() throws IOException;

    /**
     * Add a new access element in the config.xml
     *
     * @param accessOrigin String access origin to allow
     * @param launchExternal yes or no to allow or not the launch external of the url
     * @param subdomains String subdomains allowed
     * @throws IOException
     */
    void addAccess(String accessOrigin, String launchExternal, String subdomains) throws IOException;

    /**
     * Get the list of access allowed in the config.xml
     *
     * @return List Access never null
     * @throws IOException
     */
    List<Access> getAccess() throws IOException;

    /**
     * Add a new allow-element element in the config.xml
     *
     * @param href String href to allow
     * @throws IOException
     */
    void addAllowNavigation(String href) throws IOException;

    /**
     * Get the list of allow-navigation allowed in the config.xml
     *
     * @return List AllowNavigation never null
     * @throws IOException
     */
    List<AllowNavigation> getAllowNavigation() throws IOException;

    /**
     * Add a new preference element in the concrete platform with a name and a value attrs
     *
     * @param platform String platform: ios, android ...
     * @param name String attr name
     * @param value String attr value
     * @throws IOException
     */
    void addPreference(String platform, String name, String value) throws IOException;
    /**
     * Get the list of preferences allowed in the config.xml for a concrete platform
     *
     * @return List Preference never null
     * @throws IOException
     */
    List<Preference> getPreferences(String platform) throws IOException;

    /**
     * Add a new icon element in the concrete platform with a src, width, height and a density
     *
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     * @throws IOException
     */
    void addIcon(String platform, String src, Integer width, Integer height, String density) throws IOException;

    /**
     * Get the list of icons added in the config.xml for a concrete platform
     *
     * @return List Icon never null
     * @throws IOException
     */
    List<Icon> getIcons(String platform) throws IOException;

    /**
     * Add a new Splash element in the concrete platform with a src, widht, height and density
     *
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     * @throws IOException
     */
    void addSplash(String platform, String src, Integer width, Integer height, String density) throws IOException;

    /**
     * Get the list of splash element in the config.xml for a concrete platform
     *
     * @return List Splash never null
     * @throws IOException
     */
    List<Splash> getSplashs(String platform) throws IOException;


    /**
     * Add some custom fragments of XML tag.
     * Some plugins add their custom tags to the config.xml so we need to support custom fragments.
     *
     * TODO: use jackson-dataformat-xml or jaxb to append custom Java Object to the config.xml and retrieve it
     *
     * @param xml String mandatory tag
     * @throws IOException if the xml is invalid and cant be parsed
     */
    void add(String xml) throws IOException;
}
