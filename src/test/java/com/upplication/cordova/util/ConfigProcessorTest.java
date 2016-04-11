package com.upplication.cordova.util;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ConfigProcessorTest {

    private FileSystem fs;
    private ConfigProcessor processor;

    @Before
    public void setup() throws IOException {
        processor = new ConfigProcessor();
        fs = MemoryFileSystemBuilder.newLinux().build(UUID.randomUUID().toString());
    }

    @Test
    public void when_set_name_then_name_node_is_changed() throws Exception {

        String name = "app name cool";

        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setName(configFile, name);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList nameNode = resultFile.getElementsByTagName("name");
        assertNotNull(nameNode);
        assertEquals(1, nameNode.getLength());

        String nameNodeContent = nameNode.item(0).getTextContent();
        assertNotNull(nameNodeContent);
        assertEquals(name, nameNodeContent);
    }

    @Test
    public void when_set_version_then_attr_version_is_changed() throws Exception {

        String version = "2.0.0";
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setVersion(configFile, version, null, null);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList widget = resultFile.getElementsByTagName("widget");
        assertNotNull(widget);
        assertEquals(1, widget.getLength());

        String versionAttrValue = widget.item(0).getAttributes().getNamedItem("version").getTextContent();
        assertNotNull(versionAttrValue);
        assertEquals(version, versionAttrValue);
    }

    @Test
    public void when_set_version_ios_then_attr_version_ios_is_changed() throws Exception {

        String version = "2.0.0";
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setVersion(configFile, version, version, null);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList widget = resultFile.getElementsByTagName("widget");
        assertNotNull(widget);
        assertEquals(1, widget.getLength());

        String versionAttrValue = widget.item(0).getAttributes().getNamedItem("ios-CFBundleVersion").getTextContent();
        assertNotNull(versionAttrValue);
        assertEquals(version, versionAttrValue);
    }

    @Test
    public void when_set_version_android_then_attr_version_android_is_changed() throws Exception {

        String version = "2.0.0";
        Integer versionAndroid = 2;
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setVersion(configFile, version, version, versionAndroid);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList widget = resultFile.getElementsByTagName("widget");
        assertNotNull(widget);
        assertEquals(1, widget.getLength());

        String versionAttrValue = widget.item(0).getAttributes().getNamedItem("android-versionCode").getTextContent();
        assertNotNull(versionAttrValue);
        assertEquals(versionAndroid.toString(), versionAttrValue);
    }

    @Test
    public void when_set_author_email_then_author_email_is_changed() throws Exception {

        String email = "email@email.com";
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setAuthorEmail(configFile, email);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList author = resultFile.getElementsByTagName("author");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String emailAttrValue = author.item(0).getAttributes().getNamedItem("email").getTextContent();
        assertNotNull(emailAttrValue);
        assertEquals(email, emailAttrValue);
    }

    @Test
    public void when_set_author_name_then_author_content_is_changed() throws Exception {

        String authorName = "Upplication Software";
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setAuthorName(configFile, authorName);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList author = resultFile.getElementsByTagName("author");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String nameNodeContent = author.item(0).getTextContent();
        assertNotNull(nameNodeContent);
        assertEquals(authorName, nameNodeContent);
    }

    @Test
    public void when_set_author_href_then_href_attr_is_changed() throws Exception {

        String authorHref = "upplication.com";
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setAuthorHref(configFile, authorHref);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList author = resultFile.getElementsByTagName("author");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String hrefAttrValue = author.item(0).getAttributes().getNamedItem("href").getTextContent();
        assertNotNull(hrefAttrValue);
        assertEquals(authorHref, hrefAttrValue);
    }

    @Test
    public void when_set_description_then_description_node_content_is_changed() throws Exception {

        String description = "description";
        Path configFile = createFileFromDocument(createConfigXmlDocument());

        processor.setDescription(configFile, description);

        // assert

        Document resultFile = xmlToDocument(configFile);
        NodeList author = resultFile.getElementsByTagName("description");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String descriptionNodeContent = author.item(0).getTextContent();
        assertNotNull(descriptionNodeContent);
        assertEquals(description, descriptionNodeContent);
    }

    // helpers

    private Document createConfigXmlDocument() throws Exception {

        Path xml = Files.createTempFile(fs.getPath("/"), "config", ".xml");
        Files.write(xml, xmlDefault.getBytes());

        return xmlToDocument(xml);
    }

    private Path createFileFromDocument(Document doc) throws Exception {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        Path file = fs.getPath("/" + UUID.randomUUID().toString(), "config.xml");
        Files.createDirectories(file.getParent());
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
            Files.write(file, out.toByteArray(), StandardOpenOption.CREATE_NEW);
        }
        return file;
    }

    private Document xmlToDocument(Path file) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        try (InputStream in = Files.newInputStream(file)) {
            return docBuilder.parse(in);
        }
    }

    /**
     * version cordova 6.1.1 with the command: 'cordova create test'
     */
    private String xmlDefault = "<?xml version='1.0' encoding='utf-8'?>\n" +
            "<widget id=\"io.cordova.hellocordova\" version=\"0.0.1\" xmlns=\"http://www.w3.org/ns/widgets\" xmlns:cdv=\"http://cordova.apache.org/ns/1.0\">\n" +
            "    <name>HelloCordova</name>\n" +
            "    <description>\n" +
            "        A sample Apache Cordova application that responds to the deviceready event.\n" +
            "    </description>\n" +
            "    <author email=\"dev@cordova.apache.org\" href=\"http://cordova.io\">\n" +
            "        Apache Cordova Team\n" +
            "    </author>\n" +
            "    <content src=\"index.html\" />\n" +
            "    <plugin name=\"cordova-plugin-whitelist\" spec=\"1\" />\n" +
            "    <access origin=\"*\" />\n" +
            "    <allow-intent href=\"http://*/*\" />\n" +
            "    <allow-intent href=\"https://*/*\" />\n" +
            "    <allow-intent href=\"tel:*\" />\n" +
            "    <allow-intent href=\"sms:*\" />\n" +
            "    <allow-intent href=\"mailto:*\" />\n" +
            "    <allow-intent href=\"geo:*\" />\n" +
            "    <platform name=\"android\">\n" +
            "        <allow-intent href=\"market:*\" />\n" +
            "    </platform>\n" +
            "    <platform name=\"ios\">\n" +
            "        <allow-intent href=\"itms:*\" />\n" +
            "        <allow-intent href=\"itms-apps:*\" />\n" +
            "    </platform>\n" +
            "</widget>";
}