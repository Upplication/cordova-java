package com.upplication.cordova.util;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ConfigProcessorDocumentTest {

    private ConfigProcessorDocument processor;
    private Document document;

    @Before
    public void setUp() throws Exception {
        this.document = createConfigXmlDocument();
        this.processor = new ConfigProcessorDocument(this.document);
    }

    @Test
    public void when_set_name_then_name_node_is_changed() {

        String name = "app name cool";

        this.processor.setName(name);

        // assert

        NodeList nameNode = this.document.getElementsByTagName("name");
        assertNotNull(nameNode);
        assertEquals(1, nameNode.getLength());

        String nameNodeContent = nameNode.item(0).getTextContent();
        assertNotNull(nameNodeContent);
        assertEquals(name, nameNodeContent);
    }

    @Test
    public void when_set_version_then_attr_version_is_changed() {

        String version = "2.0.0";

        this.processor.setVersion(version, null, null);

        // assert

        NodeList widget = this.document.getElementsByTagName("widget");
        assertNotNull(widget);
        assertEquals(1, widget.getLength());

        String versionAttrValue = widget.item(0).getAttributes().getNamedItem("version").getTextContent();
        assertNotNull(versionAttrValue);
        assertEquals(version, versionAttrValue);
    }

    @Test
    public void when_set_version_ios_then_attr_version_ios_is_changed() {

        String version = "2.0.0";

        this.processor.setVersion(version, version, null);

        // assert

        NodeList widget = this.document.getElementsByTagName("widget");
        assertNotNull(widget);
        assertEquals(1, widget.getLength());

        String versionAttrValue = widget.item(0).getAttributes().getNamedItem("ios-CFBundleVersion").getTextContent();
        assertNotNull(versionAttrValue);
        assertEquals(version, versionAttrValue);
    }

    @Test
    public void when_set_version_android_then_attr_version_android_is_changed() {

        String version = "2.0.0";
        Integer versionAndroid = 2;

        this.processor.setVersion(version, version, versionAndroid);

        // assert

        NodeList widget = this.document.getElementsByTagName("widget");
        assertNotNull(widget);
        assertEquals(1, widget.getLength());

        String versionAttrValue = widget.item(0).getAttributes().getNamedItem("android-versionCode").getTextContent();
        assertNotNull(versionAttrValue);
        assertEquals(versionAndroid.toString(), versionAttrValue);
    }

    @Test
    public void when_set_author_email_then_author_email_is_changed() {

        String email = "email@email.com";

        this.processor.setAuthorEmail(email);

        // assert

        NodeList author = this.document.getElementsByTagName("author");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String emailAttrValue = author.item(0).getAttributes().getNamedItem("email").getTextContent();
        assertNotNull(emailAttrValue);
        assertEquals(email, emailAttrValue);
    }

    @Test
    public void when_set_author_name_then_author_content_is_changed() {

        String authorName = "Upplication Software";

        this.processor.setAuthorName(authorName);

        // assert

        NodeList author = this.document.getElementsByTagName("author");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String nameNodeContent = author.item(0).getTextContent();
        assertNotNull(nameNodeContent);
        assertEquals(authorName, nameNodeContent);
    }

    @Test
    public void when_set_author_href_then_href_attr_is_changed() {

        String authorHref = "upplication.com";

        this.processor.setAuthorHref(authorHref);

        // assert

        NodeList author = this.document.getElementsByTagName("author");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String hrefAttrValue = author.item(0).getAttributes().getNamedItem("href").getTextContent();
        assertNotNull(hrefAttrValue);
        assertEquals(authorHref, hrefAttrValue);
    }

    @Test
    public void when_set_description_then_description_node_content_is_changed() {

        String description = "description";

        this.processor.setDescription(description);

        // assert

        NodeList author = this.document.getElementsByTagName("description");
        assertNotNull(author);
        assertEquals(1, author.getLength());

        String descriptionNodeContent = author.item(0).getTextContent();
        assertNotNull(descriptionNodeContent);
        assertEquals(description, descriptionNodeContent);
    }

    @Test
    public void when_set_allowNavigation_then_allowNavigation_node_is_added() {

        String href = "*";

        this.processor.addAllowNavigation(href);

        // assert

        NodeList allowNavigationNode = this.document.getElementsByTagName("allow-navigation");
        assertNotNull(allowNavigationNode);
        assertEquals(1, allowNavigationNode.getLength());

        String hrefAttributeValue = allowNavigationNode.item(0).getAttributes().getNamedItem("href").getTextContent();
        assertNotNull(hrefAttributeValue);
        assertEquals(href, hrefAttributeValue);
    }

    // helpers

    private Document createConfigXmlDocument() throws Exception {

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlDefault));

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(is);
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