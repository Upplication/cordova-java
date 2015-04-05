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

    public Version getVersion(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        String version = widget.getAttribute(versionAttrName);

        return Version.create().version(version)
                .iosCfBundleVersion(widget.getAttribute("ios-CFBundleVersion"))
                .androidVersionCode(getInteger(widget.getAttribute("android-versionCode")));
    }

    public void setName(Path configFile, String name) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(nameNodeName).item(0);

        nameTag.setTextContent(name);

        saveConfig(configFile, document);
    }

    public String getName(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(nameNodeName).item(0);

        return nameTag.getTextContent();
    }

    public void setDescription(Path configFile, String description) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(descriptionNodeName).item(0);

        nameTag.setTextContent(description);

        saveConfig(configFile, document);
    }

    public String getDescription(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element descriptionTag = (Element) document.getElementsByTagName(descriptionNodeName).item(0);

        return descriptionTag.getTextContent();
    }

    public void setAuthorName(Path configFile, String authorName) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setTextContent(authorName);

        saveConfig(configFile, document);
    }

    public String getAuthorName(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        return nameTag.getTextContent();
    }

    public void setAuthorEmail(Path configFile, String authorEmail) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setAttribute(authorEmailAttrName, authorEmail);

        saveConfig(configFile, document);
    }

    public String getAuthorEmail(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        return nameTag.getAttribute(authorEmailAttrName);
    }

    public void setAuthorHref(Path configFile, String authorHref) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setAttribute(authorHrefAttrName, authorHref);

        saveConfig(configFile, document);
    }

    public String getAuthorHref(Path configFile) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        return nameTag.getAttribute(authorHrefAttrName);
    }

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

    public void addPreference(Path configFile, String name, String value) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        Element accessElement = document.createElement(preferenceNodeName);
        accessElement.setAttribute("name", name);
        accessElement.setAttribute("value", value);

        widget.appendChild(accessElement);

        saveConfig(configFile, document);
    }

    public List<Preference> getPreferences(Path configFile) throws IOException {

        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        List<Preference> result = new ArrayList<>();

        NodeList nodeList = widget.getElementsByTagName(preferenceNodeName);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element)node;
            Preference access = Preference.create()
                    .name(element.getAttribute("name"))
                    .value(element.getAttribute("value"));
            result.add(access);
        }

        return result;
    }

    public void addIcon(Path configFile, String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

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

    public List<Icon> getIcons(Path configFile, String platform) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);
        Element parent = widget;

        List<Icon> result = new ArrayList<>();

        if (platform != null) {
            Node node = findNode(document, "platform", "name", platform);
            if (node == null) {
                return result;
            }

            parent = (Element) node;
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

    public void addSplash(Path configFile, String platform, String src, Integer width, Integer height, String density) throws IOException {
        Document document = openConfig(configFile);

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

    private Integer getInteger(String number){
        if (number == null || number.isEmpty())
            return null;
        else
            return new Integer(number);
    }

    private Boolean getBoolean(String affirmative){
        if (affirmative == null || affirmative.isEmpty())
            return null;
        else if (affirmative.equalsIgnoreCase("yes"))
            return true;
        else if (affirmative.equalsIgnoreCase("no"))
            return false;
        else throw new IllegalStateException("Unknown resulto to boolean: " + affirmative);
    }


}
