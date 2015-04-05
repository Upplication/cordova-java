package com.upplication.cordova.util;

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
     * @param configFile Config file path
     * @param version    Version
     * @throws IOException
     */
    public void setVersion(Path configFile, String version) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        widget.setAttribute(versionAttrName, version);

        saveConfig(configFile, document);
    }

    public void setName(Path configFile, String name) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(nameNodeName).item(0);

        nameTag.setTextContent(name);

        saveConfig(configFile, document);
    }

    public void setDescription(Path configFile, String description) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(descriptionNodeName).item(0);

        nameTag.setTextContent(description);

        saveConfig(configFile, document);
    }

    public void setAuthorName(Path configFile, String authorName) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setTextContent(authorName);

        saveConfig(configFile, document);
    }

    public void setAuthorEmail(Path configFile, String authorEmail) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setAttribute(authorEmailAttrName, authorEmail);

        saveConfig(configFile, document);
    }

    public void setAuthorHref(Path configFile, String authorHref) throws IOException {
        Document document = openConfig(configFile);

        Element nameTag = (Element) document.getElementsByTagName(authorNodeName).item(0);

        nameTag.setAttribute(authorHrefAttrName, authorHref);

        saveConfig(configFile, document);
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

    public void addPreference(Path configFile, String name, String value) throws IOException {
        Document document = openConfig(configFile);

        Element widget = (Element) document.getElementsByTagName(widgetNodeName).item(0);

        Element accessElement = document.createElement(preferenceNodeName);
        accessElement.setAttribute("name", name);
        accessElement.setAttribute("value", value);

        widget.appendChild(accessElement);

        saveConfig(configFile, document);
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



}
