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
import javax.xml.transform.TransformerConfigurationException;
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
public class ConfigProcessor {

    private static final String widgetNodeName = "widget";
    private static final String nameNodeName = "name";
    private static final String descriptionNodeName = "description";
    private static final String authorNodeName = "author";
    private static final String accessNodeName = "access";
    private static final String preferenceNodeName = "preference";

    private static final String authorEmailAttrName = "email";
    private static final String authorHrefAttrName = "href";
    private static final String versionAttrName = "version";

    /**
     * Sets the version of the application in the config.xml file
     *
     * @param configFile         Config file path
     * @param version            Version
     * @param iosCfBundleVersion ios Version
     * @param androidVersionCode android    Version
     * @throws IOException
     */
    public void setVersion(Path configFile, String version, String iosCfBundleVersion, Integer androidVersionCode) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        widget.setAttribute(versionAttrName, version);

        if (iosCfBundleVersion != null)
            widget.setAttribute("ios-CFBundleVersion", iosCfBundleVersion);

        if (androidVersionCode != null)
            widget.setAttribute("android-versionCode", androidVersionCode.toString());

        saveConfig(configFile, document);
    }

    /**
     * Get the version of the application in the config.xml file
     *
     * @param configFile Config file path
     * @return Version never null
     * @throws IOException
     */
    public Version getVersion(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        String version = widget.getAttribute(versionAttrName);

        return Version.create().version(version)
                .iosCfBundleVersion(widget.getAttribute("ios-CFBundleVersion"))
                .androidVersionCode(getInteger(widget.getAttribute("android-versionCode")));
    }

    /**
     * Sets the name of the application in the config.xml file
     *
     * @param configFile    Config file path
     * @param name          String, mandatory not null
     * @throws IOException
     */
    public void setName(Path configFile, String name) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(nameNodeName).item(0);

        nameTag.setTextContent(name);

        saveConfig(configFile, document);
    }

    /**
     * Get the name of the application in the config.xml file
     *
     * @param configFile Config file path
     * @return the name never null
     * @throws IOException
     */
    public String getName(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(nameNodeName).item(0);

        return nameTag.getTextContent();
    }

    /**
     * Sets the description of the application in the config.xml file
     *
     * @param configFile    Config file path
     * @param description   String, mandatory not null
     * @throws IOException
     */
    public void setDescription(Path configFile, String description) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(descriptionNodeName).item(0);

        nameTag.setTextContent(description);

        saveConfig(configFile, document);
    }

    /**
     * Get the description of the application in the config.xml file
     *
     * @param configFile Config file path
     * @return the description never null
     * @throws IOException
     */
    public String getDescription(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element descriptionTag = (Element) document.getElementsByTagName(descriptionNodeName).item(0);

        return descriptionTag.getTextContent();
    }

    /**
     * Sets the author of the application in the config.xml file
     *
     * @param configFile Config file path
     * @param authorName String, the author name to set
     * @throws IOException
     */
    public void setAuthorName(Path configFile, String authorName) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setTextContent(authorName);

        saveConfig(configFile, document);
    }

    /**
     * Get the author name of the application in the config.xml file
     *
     * @param configFile Config file path
     * @return the author name as String never null
     * @throws IOException
     */
    public String getAuthorName(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        return nameTag.getTextContent();
    }

    /**
     * Sets the author email of the application in the config.xml file
     *
     * @param configFile Config file path
     * @param authorEmail String the author email
     * @throws IOException
     */
    public void setAuthorEmail(Path configFile, String authorEmail) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setAttribute(authorEmailAttrName, authorEmail);

        saveConfig(configFile, document);
    }

    /**
     * Get the author email of the application in the config.xml file
     *
     * @param configFile Config file path
     * @return the author email as String never null
     * @throws IOException
     */
    public String getAuthorEmail(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        return nameTag.getAttribute(authorEmailAttrName);
    }

    /**
     * Sets the author href of the application in the config.xml file
     *
     * @param configFile Config file path
     * @param authorHref String the author href
     * @throws IOException
     */
    public void setAuthorHref(Path configFile, String authorHref) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setAttribute(authorHrefAttrName, authorHref);

        saveConfig(configFile, document);
    }

    /**
     * Get the author href of the application in the config.xml file
     *
     * @param configFile Config file path
     * @return the author href as String never null
     * @throws IOException
     */
    public String getAuthorHref(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        return nameTag.getAttribute(authorHrefAttrName);
    }

    /**
     * Add a new access element in the config.xml
     *
     * @param configFile Path Config file
     * @param accessOrigin String access origin to allow
     * @param launchExternal yes or no to allow or not the launch external of the url
     * @param subdomains String subdomains allowed
     * @throws IOException
     */
    public void addAccess(Path configFile, String accessOrigin, String launchExternal, String subdomains) throws IOException {

        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        Element accessElement = document.createElement(accessNodeName);
        accessElement.setAttribute("origin", accessOrigin);
        if (launchExternal != null) {
            accessElement.setAttribute("launch-external", launchExternal);
        }

        if (subdomains != null) {
            accessElement.setAttribute("subdomains", subdomains);
        }

        widget.appendChild(accessElement);

        saveConfig(configFile, document);
    }

    /**
     * Get the list of access allowed in the config.xml
     *
     * @param configFile Path config.xml file
     * @return List Access never null
     * @throws IOException
     */
    public List<Access> getAccess(Path configFile) throws IOException {

        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        List<Access> result = new ArrayList<>();

        NodeList nodeList = widget.getElementsByTagName(accessNodeName);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            Access access = Access.create()
                    .origin(element.getAttribute("origin"))
                    .subdomains(getBoolean(element.getAttribute("subdomains")))
                    .launchExternal(getBoolean(element.getAttribute("launch-external")));
            result.add(access);
        }

        return result;
    }

    /**
     * Add a new preference element in the concrete platform with a name and a value attrs
     *
     * @param configFile Path config.xml file
     * @param platform String platform: ios, android ...
     * @param name String attr name
     * @param value String attr value
     * @throws IOException
     */
    public void addPreference(Path configFile, String platform, String name, String value) throws IOException {
        Document document = openConfig(configFile);

        Element parent = getPlatformElement(platform, document);

        Element accessElement = document.createElement(preferenceNodeName);
        accessElement.setAttribute("name", name);
        accessElement.setAttribute("value", value);

        parent.appendChild(accessElement);

        saveConfig(configFile, document);
    }

    /**
     * Get the list of preferences allowed in the config.xml for a concrete platform
     *
     * @param configFile Path config.xml file
     * @param platform String platform to find
     * @return List Preference never null
     * @throws IOException
     */
    public List<Preference> getPreferences(Path configFile, String platform) throws IOException {
        List<Preference> result = new ArrayList<>();
        Document document = openConfig(configFile);
        Element parent = getPlatformElement(platform, document);

        if (parent == null) {
            return result;
        }


        NodeList nodeList = parent.getElementsByTagName(preferenceNodeName);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;
                Preference access = Preference.create()
                        .name(element.getAttribute("name"))
                        .value(element.getAttribute("value"));
                result.add(access);
            }
        }

        return result;
    }

    /**
     * Add a new icon element in the concrete platform with a src, width, height and a density
     *
     * @param configFile Path config.xml file
     * @param platform String platform: ios, android ...
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     * @throws IOException
     */
    public void addIcon(Path configFile, String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

        Element parent = getPlatformElement(platform, document);

        Element iconElement = document.createElement("icon");
        iconElement.setAttribute("src", src);
        if (width != null)
            iconElement.setAttribute("width", width.toString());
        if (height != null)
            iconElement.setAttribute("height", height.toString());
        if (density != null)
            iconElement.setAttribute("density", density);

        parent.appendChild(iconElement);

        saveConfig(configFile, document);
    }

    /**
     * Get the list of icons added in the config.xml for a concrete platform
     *
     * @param configFile Path the config.xml file
     * @param platform String platform to search: ios, android...
     * @return List Icon never null
     * @throws IOException
     */
    public List<Icon> getIcons(Path configFile, String platform) throws IOException {
        List<Icon> result = new ArrayList<>();

        Document document = openConfig(configFile);
        Element parent = getPlatformElement(platform, document);

        if (parent == null) {
            return result;
        }

        NodeList nodeList = parent.getElementsByTagName("icon");

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;
                Icon icon = Icon.create()
                        .src(element.getAttribute("src"))
                        .width(getInteger(element.getAttribute("width")))
                        .height(getInteger(element.getAttribute("height")))
                        .density(element.getAttribute("density"));
                result.add(icon);
            }
        }

        return result;
    }

    /**
     * Add a new Splash element in the concrete platform with a src, widht, height and density
     *
     * @param configFile Path config.xml file
     * @param platform String platform: ios, android ...
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     * @throws IOException
     */
    public void addSplash(Path configFile, String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

        Element parent = getPlatformElement(platform, document);

        Element iconElement = document.createElement("splash");
        iconElement.setAttribute("src", src);
        if (width != null)
            iconElement.setAttribute("width", width.toString());
        if (height != null)
            iconElement.setAttribute("height", height.toString());
        if (density != null)
            iconElement.setAttribute("density", density);

        parent.appendChild(iconElement);

        saveConfig(configFile, document);
    }

    /**
     * Get the list of splash element in the config.xml for a concrete platform
     *
     * @param configFile Path config.xml file
     * @param platform String platform
     * @return List Splash never null
     * @throws IOException
     */
    public List<Splash> getSplashs(Path configFile, String platform) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);
        Element parent = widget;

        List<Splash> result = new ArrayList<>();

        if (platform != null) {
            Node node = findNode(document, "platform", "name", platform);
            if (node == null) {
                return result;
            }

            parent = (Element) node;
        }

        NodeList nodeList = parent.getElementsByTagName("splash");

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;
                Splash splash = Splash.create()
                        .src(element.getAttribute("src"))
                        .width(getInteger(element.getAttribute("width")))
                        .height(getInteger(element.getAttribute("height")))
                        .density(element.getAttribute("density"));
                result.add(splash);
            }
        }

        return result;
    }

    /**
     * Opens the config file
     *
     * @param configFile Config file path
     * @return Config document
     * @throws IOException
     */
    private Document openConfig(Path configFile) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(Files.newInputStream(configFile));
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

    /**
     * Try to find a node in the document with the tag name and with a attribute like the attrValue
     *
     * @param document Document to search in it
     * @param tagName String tag name to find
     * @param attrName String the attribute name
     * @param attrValue String the attribute value to check against
     * @return Node or null
     */
    private Node findNode(Document document, String tagName, String attrName, String attrValue) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element e = (Element)node;
            String name = e.getAttribute(attrName);
            if (name.equals(attrValue)){
                return node;
            }
        }

        return null;
    }

    /**
     * Transform a String into number
     *
     * @param number String
     * @return Integer or null if the String is null or empty
     * @throws NumberFormatException if its a invalid string
     */
    private Integer getInteger(String number){
        if (number == null || number.isEmpty())
            return null;
        else
            return new Integer(number);
    }

    /**
     * Transform a String to boolean.
     * yes to true
     * no to false
     *
     * @param affirmative String
     * @return true or false or null if the string is empty or null
     * @throws IllegalStateException if the string is unknown
     */
    private Boolean getBoolean(String affirmative){
        if (affirmative == null || affirmative.isEmpty())
            return null;
        else if (affirmative.equalsIgnoreCase("yes"))
            return true;
        else if (affirmative.equalsIgnoreCase("no"))
            return false;
        else throw new IllegalStateException("Unknown result to boolean: " + affirmative);
    }

    /**
     * Try to find the platform node of the document
     *
     * @param platform String platform for ios, android....
     * @param document Document to search
     * @return Element or null if not exists
     */
    private Element getPlatformElement(String platform, Document document) {
        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);
        Element parent = widget;

        if (platform != null) {
            Node node = findNode(document, "platform", "name", platform);
            if (node == null) {
                // create platform
                Element platformElement = document.createElement("platform");
                platformElement.setAttribute("name", platform);
                node = widget.appendChild(platformElement);
            }

            parent = (Element) node;
        }
        return parent;
    }
}
