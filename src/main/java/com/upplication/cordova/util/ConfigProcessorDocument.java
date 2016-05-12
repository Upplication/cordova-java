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
import java.io.Closeable;
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
public class ConfigProcessorDocument {

    private static final String widgetNodeName = "widget";
    private static final String nameNodeName = "name";
    private static final String descriptionNodeName = "description";
    private static final String authorNodeName = "author";
    private static final String accessNodeName = "access";
    private static final String allowNavigationNode = "allow-navigation";
    private static final String preferenceNodeName = "preference";

    private static final String authorEmailAttrName = "email";
    private static final String authorHrefAttrName = "href";
    private static final String versionAttrName = "version";


    private Document document;

    public ConfigProcessorDocument(Document document) throws IOException {
        this.document = document;
    }

    /**
     * Sets the version of the application in the config.xml file
     *
     * @param version            Version
     * @param iosCfBundleVersion ios Version, String, optional can be null
     * @param androidVersionCode android Version, Integer, optional can be null
     */
    public void setVersion(String version, String iosCfBundleVersion, Integer androidVersionCode) {

        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

        widget.setAttribute(versionAttrName, version);

        if (iosCfBundleVersion != null)
            widget.setAttribute("ios-CFBundleVersion", iosCfBundleVersion);

        if (androidVersionCode != null)
            widget.setAttribute("android-versionCode", androidVersionCode.toString());
    }

    /**
     * Get the version of the application in the config.xml file
     *
     * @return Version never null
     */
    public Version getVersion() {

        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

        String version = widget.getAttribute(versionAttrName);

        return Version.create().version(version)
                .iosCfBundleVersion(widget.getAttribute("ios-CFBundleVersion"))
                .androidVersionCode(getInteger(widget.getAttribute("android-versionCode")));
    }

    /**
     * Sets the name of the application in the config.xml file
     *
     * @param name String, mandatory not null
     */
    public void setName(String name) {
        Element nameTag = (Element) this.document.getElementsByTagName(nameNodeName).item(0);
        nameTag.setTextContent(name);
    }

    /**
     * Get the name of the application in the config.xml file
     *
     * @return the name never null
     */
    public String getName() {
        Element nameTag = (Element) this.document.getElementsByTagName(nameNodeName).item(0);
        return nameTag.getTextContent();
    }

    /**
     * Sets the description of the application in the config.xml file
     *
     * @param description   String, mandatory not null
     */
    public void setDescription(String description) {
        Element nameTag = (Element) this.document.getElementsByTagName(descriptionNodeName).item(0);
        nameTag.setTextContent(description);
    }

    /**
     * Get the description of the application in the config.xml file
     *
     * @return the description never null
     * @throws IOException
     */
    public String getDescription() throws IOException {
        Element descriptionTag = (Element) this.document.getElementsByTagName(descriptionNodeName).item(0);
        return descriptionTag.getTextContent();
    }

    /**
     * Sets the author of the application in the config.xml file
     *
     * @param authorName String, the author name to set
     */
    public void setAuthorName(String authorName) {
        Element nameTag = (Element) this.document.getElementsByTagName(authorNodeName).item(0);
        nameTag.setTextContent(authorName);
    }

    /**
     * Get the author name of the application in the config.xml file
     *
     * @return the author name as String never null
     */
    public String getAuthorName() {
        Element nameTag = (Element) this.document.getElementsByTagName(authorNodeName).item(0);
        return nameTag.getTextContent();
    }

    /**
     * Sets the author email of the application in the config.xml file
     *
     * @param authorEmail String the author email
     */
    public void setAuthorEmail(String authorEmail) {
        Element nameTag = (Element) this.document.getElementsByTagName(authorNodeName).item(0);
        nameTag.setAttribute(authorEmailAttrName, authorEmail);
    }

    /**
     * Get the author email of the application in the config.xml file
     *
     * @return the author email as String never null
     */
    public String getAuthorEmail() {
        Element nameTag = (Element) this.document.getElementsByTagName(authorNodeName).item(0);
        return nameTag.getAttribute(authorEmailAttrName);
    }

    /**
     * Sets the author href of the application in the config.xml file
     *
     * @param authorHref String the author href
     */
    public void setAuthorHref(String authorHref) {
        Element nameTag = (Element) this.document.getElementsByTagName(authorNodeName).item(0);
        nameTag.setAttribute(authorHrefAttrName, authorHref);
    }

    /**
     * Get the author href of the application in the config.xml file
     *
     * @return the author href as String never null
     */
    public String getAuthorHref() {
        Element nameTag = (Element) this.document.getElementsByTagName(authorNodeName).item(0);
        return nameTag.getAttribute(authorHrefAttrName);
    }

    /**
     * Add a new access element in the config.xml
     *
     * @param accessOrigin String access origin to allow
     * @param launchExternal yes or no to allow or not the launch external of the url
     * @param subdomains String subdomains allowed
     */
    public void addAccess(String accessOrigin, String launchExternal, String subdomains) {

        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

        Element accessElement = this.document.createElement(accessNodeName);
        accessElement.setAttribute("origin", accessOrigin);
        if (launchExternal != null) {
            accessElement.setAttribute("launch-external", launchExternal);
        }

        if (subdomains != null) {
            accessElement.setAttribute("subdomains", subdomains);
        }

        widget.appendChild(accessElement);
    }

    /**
     * Get the list of access allowed in the config.xml
     *
     * @return List Access never null
     */
    public List<Access> getAccess() {

        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

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
     * Add a new allow-element element in the config.xml
     *
     * @param href String href to allow
     */
    public void addAllowNavigation(String href) {
        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

        Element allowElement = this.document.createElement(allowNavigationNode);
        allowElement.setAttribute("href", href);

        widget.appendChild(allowElement);
    }

    /**
     * Get the list of allow-navigation allowed in the config.xml
     *
     * @return List AllowNavigation never null
     */
    public List<AllowNavigation> getAllowNavigation() {

        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

        List<AllowNavigation> result = new ArrayList<>();

        NodeList nodeList = widget.getElementsByTagName(allowNavigationNode);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            AllowNavigation allowNavigation = AllowNavigation.create()
                    .href(element.getAttribute("href"));
            result.add(allowNavigation);
        }

        return result;
    }

    /**
     * Add a new preference element in the concrete platform with a name and a value attrs
     *
     * @param platform String platform: ios, android ...
     * @param name String attr name
     * @param value String attr value
     */
    public void addPreference(String platform, String name, String value) {
        Element parent = getPlatformElement(platform);

        Element accessElement = document.createElement(preferenceNodeName);
        accessElement.setAttribute("name", name);
        accessElement.setAttribute("value", value);

        parent.appendChild(accessElement);
    }

    /**
     * Get the list of preferences allowed in the config.xml for a concrete platform
     *
s     * @param platform String platform to find
     * @return List Preference never null
     */
    public List<Preference> getPreferences(String platform) {
        List<Preference> result = new ArrayList<>();
        Element parent = getPlatformElement(platform);

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
     * @param platform String platform: ios, android ...
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     */
    public void addIcon(String platform, String src, Integer width, Integer height, String density) {

        Element parent = getPlatformElement(platform);

        Element iconElement = this.document.createElement("icon");
        iconElement.setAttribute("src", src);
        if (width != null)
            iconElement.setAttribute("width", width.toString());
        if (height != null)
            iconElement.setAttribute("height", height.toString());
        if (density != null)
            iconElement.setAttribute("density", density);

        parent.appendChild(iconElement);
    }

    /**
     * Get the list of icons added in the config.xml for a concrete platform
     *
     * @param platform String platform to search: ios, android...
     * @return List Icon never null
     * @throws IOException
     */
    public List<Icon> getIcons(String platform) {
        List<Icon> result = new ArrayList<>();

        Element parent = getPlatformElement(platform);

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
     * @param platform String platform: ios, android ...
     * @param src String the icon relative path
     * @param width Integer, optional the width in pixels
     * @param height Integer, optional the width in pixels
     * @param density String, optional the density
     */
    public void addSplash(String platform, String src, Integer width, Integer height, String density) {

        Element parent = getPlatformElement(platform);

        Element iconElement = this.document.createElement("splash");
        iconElement.setAttribute("src", src);
        if (width != null)
            iconElement.setAttribute("width", width.toString());
        if (height != null)
            iconElement.setAttribute("height", height.toString());
        if (density != null)
            iconElement.setAttribute("density", density);

        parent.appendChild(iconElement);
    }

    /**
     * Get the list of splash element in the config.xml for a concrete platform
     *
     * @param platform String platform
     * @return List Splash never null
     */
    public List<Splash> getSplashs(String platform) {

        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);
        Element parent = widget;

        List<Splash> result = new ArrayList<>();

        if (platform != null) {
            Node node = findNode("platform", "name", platform);
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
     * Try to find a node in the document with the tag name and with a attribute like the attrValue
     *
     * @param tagName String tag name to find
     * @param attrName String the attribute name
     * @param attrValue String the attribute value to check against
     * @return Node or null
     */
    private Node findNode(String tagName, String attrName, String attrValue) {
        NodeList nodeList = this.document.getElementsByTagName(tagName);
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
     * @return Element or null if not exists
     */
    private Element getPlatformElement(String platform) {
        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);
        Element parent = widget;

        if (platform != null) {
            Node node = findNode("platform", "name", platform);
            if (node == null) {
                // create platform
                Element platformElement = this.document.createElement("platform");
                platformElement.setAttribute("name", platform);
                node = widget.appendChild(platformElement);
            }

            parent = (Element) node;
        }
        return parent;
    }

}
