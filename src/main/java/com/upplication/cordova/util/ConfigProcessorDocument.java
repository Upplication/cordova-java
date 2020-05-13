package com.upplication.cordova.util;

import com.upplication.cordova.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private static final String featureNodeName = "feature";
    private static final String editConfigNodeName = "edit-config";
    private static final String configFileNodeName = "config-file";
    private static final String resourceFileNodeName = "resource-file";

    private static final String authorEmailAttrName = "email";
    private static final String authorHrefAttrName = "href";
    private static final String versionAttrName = "version";


    private Document document;
    private DocumentUtil documentUtil;

    public ConfigProcessorDocument(Document document) {
        this.document = document;
        this.documentUtil = new DocumentUtil();
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
     */
    public String getDescription() {
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
     * @param platform String platform: ios, android ... can be null
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
     * @param platform String platform to find
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
     * Add a new feature element with a name and the params values in the concrete platform
     * or in the widget element if the platform is null
     *
     * @param platform String platform: ios, android ... can be null
     * @param name String attr name, mandatory
     * @param values Feature.Param parameters, mandatory
     */
    public void addFeature(String platform, String name, Feature.Param ... values) {
        Element parent = getPlatformElement(platform);

        Element featureElement = document.createElement(featureNodeName);
        featureElement.setAttribute("name", name);
        parent.appendChild(featureElement);

        for (Feature.Param param : values) {
            Element paramElement = document.createElement("param");

            paramElement.setAttribute("name", param.getName());
            paramElement.setAttribute("value", param.getValue());

            featureElement.appendChild(paramElement);
        }
    }

    /**
     * Get the list of feature allowed in the config.xml for a concrete platform or for the widget
     * if the platform is null
     *
     * @param platform String platform to find, can be null.
     * @return List Preference never null
     */
    public List<Feature> getFeatures(String platform) {
        List<Feature> result = new ArrayList<>();
        Element parent = getPlatformElement(platform);

        if (parent == null) {
            return result;
        }

        NodeList nodeList = parent.getElementsByTagName(featureNodeName);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);

            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;

                List<Feature.Param> params = new ArrayList<>();
                NodeList paramNodeList = element.getElementsByTagName("param");
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Element paramElement = (Element)paramNodeList.item(j);
                    params.add(Feature.Param.create(paramElement.getAttribute("name"), paramElement.getAttribute("value")));
                }

                Feature access = Feature.create(element.getAttribute("name"), params);
                result.add(access);
            }
        }

        return result;
    }

    /**
     * Add a new edit-config element in the concrete platform (or general if null)
     *
     * @param platform String platform: ios, android ...
     * @param file String The file to be modified, and the path relative to the root of the Cordova project.
     * @param target String An XPath selector referencing the target element to make attribute modifications to
     * @param mode String The mode that determines what type of attribute modifications will be made.
     * @param content String The XML to edit
     */
    public void addEditConfig(String platform, String file, String target, String mode, String content) {
        Element parent = getPlatformElement(platform);

        Element editConfigElement = document.createElement(editConfigNodeName);
        editConfigElement.setAttribute("file", file);
        editConfigElement.setAttribute("target", target);
        editConfigElement.setAttribute("mode", mode);
        // add content, must be a valid xml content
        add(content, editConfigElement);

        parent.appendChild(editConfigElement);
    }

    /**
     * Get the list of edit-config allowed in the config.xml for a concrete platform
     *
     * @param platform String platform to find
     * @return List EditConfig never null
     */
    public List<EditConfig> getEditConfig(String platform) {
        List<EditConfig> result = new ArrayList<>();
        Element parent = getPlatformElement(platform);

        if (parent == null) {
            return result;
        }

        NodeList nodeList = parent.getElementsByTagName(editConfigNodeName);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;
                EditConfig editConfig = EditConfig.create()
                        .file(element.getAttribute("file"))
                        .mode(element.getAttribute("mode"))
                        .target(element.getAttribute("target"))
                        .content(innerXml(element));
                result.add(editConfig);
            }
        }

        return result;
    }

    /**
     * Add a new config-file element in the concrete platform (or general if null)
     *
     * @param platform String platform: ios, android ...
     * @param target String The file to be modified, and the path relative to the root of the Cordova project. If the specified file does not exist, the tool ignores the configuration change and continues installation.
     * @param parent String An XPath selector referencing the parent of the elements to be added to the config file. If you use absolute selectors, you can use a wildcard (*) to specify the root element
     * @param after String A prioritized list of accepted siblings after which to add the XML snippet.
     * @param content String The XML to add
     */
    public void addConfigFile(String platform, String target, String parent, String after, String content) {
        Element parentElement = getPlatformElement(platform);

        Element configFileElement = document.createElement(configFileNodeName);
        configFileElement.setAttribute("target", target);
        configFileElement.setAttribute("parent", parent);
        if (after != null)
            configFileElement.setAttribute("after", after);
        // add content, must be a valid xml content
        add(content, configFileElement);

        parentElement.appendChild(configFileElement);
    }

    /**
     * Get the list of config-file allowed in the config.xml for a concrete platform
     *
     * @param platform String platform to find
     * @return List EditConfig never null
     */
    public List<ConfigFile> getConfigFile(String platform) {
        List<ConfigFile> result = new ArrayList<>();
        Element parent = getPlatformElement(platform);

        if (parent == null) {
            return result;
        }

        NodeList nodeList = parent.getElementsByTagName(configFileNodeName);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;
                ConfigFile configFile = ConfigFile.create()
                        .target(element.getAttribute("target"))
                        .parent(element.getAttribute("parent"))
                        .after(getAttribute(element, "after"))
                        .content(innerXml(element));
                result.add(configFile);
            }
        }

        return result;
    }

    /**
     * Add a new resource-file element in the concrete platform (or general if null)
     *
     * @param platform String platform: ios, android ...
     * @param src String The file to be added with the Path relative to the cordova project
     * @param target String, optional, the relative path to the cordova project to add the file
     */
    public void addResourceFile(String platform, String src, String target) {
        Element parent = getPlatformElement(platform);

        Element editConfigElement = document.createElement(resourceFileNodeName);
        editConfigElement.setAttribute("src", src);
        if (target != null) {
            editConfigElement.setAttribute("target", target);
        }
        parent.appendChild(editConfigElement);
    }

    /**
     * Get the list of resource-file allowed in the config.xml for a concrete platform
     *
     * @param platform String platform to find
     * @return List ResourceFile never null
     */
    public List<ResourceFile> getResourceFile(String platform) {
        List<ResourceFile> result = new ArrayList<>();
        Element parent = getPlatformElement(platform);

        if (parent == null) {
            return result;
        }

        NodeList nodeList = parent.getElementsByTagName(resourceFileNodeName);

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getParentNode().equals(parent)){
                Element element = (Element)node;
                ResourceFile editConfig = ResourceFile
                        .create(element.getAttribute("src"))
                        .target(element.getAttribute("target"));

                result.add(editConfig);
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

        Element parent = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);

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
     * Add a custom valid XML
     *
     * @param tag String never null.
     * @throws IllegalArgumentException if the tag is not valid tag
     */
    public void add(String tag) {
        Element widget = (Element) this.document.getElementsByTagName(widgetNodeName).item(0);
        add(tag, widget);
    }

    /**
     * Add a custom valid XML to the parent Element
     * @param tag String never null
     * @param parent Element to append never null
     *
     * @throws IllegalArgumentException if the tag is not valid tag
     */
    private void add(String tag, Element parent) {
        Element elem;
        try (java.io.InputStream sbis = new java.io.ByteArrayInputStream(tag.getBytes(StandardCharsets.UTF_8))) {
            elem = documentUtil.newDocumentBuilder().parse(sbis).getDocumentElement();
        }
        catch (IOException | SAXException e) {
            throw new IllegalArgumentException("Xml element: " + tag + " is not a valid xml fragment", e);
        }

        Node node = this.document.importNode(elem, true);
        parent.appendChild(node);
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
     * Try to find the platform node of the document.
     * If platform is null, then return the widget element.
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

    /**
     * Get the innerXML of a node without the Node itself.
     *
     * @param node Node with child nodes
     * @return if no child nodes then return empty string
     */
    private String innerXml(Node node) {
        LSSerializer lsSerializer = documentUtil.serializer();
        NodeList nodes = node.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < nodes.getLength(); i++) {
            Node nodeChild = nodes.item(i);
            sb.append(lsSerializer.writeToString(nodeChild));
        }
        return sb.toString();
    }

    /**
     * Like Element#getAttribute(String) but return null if empty
     * @see Element#getAttribute(String)
     * @param node
     * @param attrName
     * @return
     */
    private String getAttribute(Element node, String attrName) {
        if (!node.hasAttribute(attrName)) {
            return null;
        }
        return node.getAttribute(attrName);
    }
}
