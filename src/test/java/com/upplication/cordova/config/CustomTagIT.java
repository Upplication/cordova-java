package com.upplication.cordova.config;


import com.upplication.cordova.CordovaConfig;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.util.ConfigTransactionJob;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CustomTagIT {
    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaProject cordova;

    @Before
    public void setUp() throws IOException {
        cordova = cordovaCLIRule.get().create(folder.newFolder("cordova-path"));
    }

    @Test
    public void add_tag() throws IOException {
        String tag = "<universal-links></universal-links>";

        cordova.config().add(tag);

        assertTag("universal-links", cordova.config().getConfigXml().toPath());
    }

    @Test
    public void add_tag_with_inner_tags() throws IOException {
        String tag =
                "<universal-links>" +
                        "<host name=\"example.com\">" +
                            "<path url=\"/some/path\" />" +
                        "</host>" +
                "</universal-links>";

        cordova.config().add(tag);

        assertTag("universal-links", cordova.config().getConfigXml().toPath());
        assertTag("host", cordova.config().getConfigXml().toPath());
        assertTag("path", cordova.config().getConfigXml().toPath());
    }

    @Test
    public void add_tag_two_times() throws IOException {
        String tag =
                "<universal-links>" +
                    "<host name=\"example.com\">" +
                        "<path url=\"/some/path\" />" +
                    "</host>" +
                    "<host name=\"example2.com\">" +
                    "</host>" +
                "</universal-links>";

        cordova.config().add(tag);
        cordova.config().add(tag);

        assertTag("universal-links", 2, cordova.config().getConfigXml().toPath());
        assertTag("host", 4, cordova.config().getConfigXml().toPath());
        assertTag("path", 2, cordova.config().getConfigXml().toPath());
    }

    @Test
    public void add_tag_with_attributes() throws IOException {
        String tag =
                "<universal-links>" +
                    "<host name=\"example.com\">" +
                        "<path url=\"/some/path\" />" +
                    "</host>" +
                "</universal-links>";

        cordova.config().add(tag);

        assertTag("universal-links", cordova.config().getConfigXml().toPath());
        assertAttrTag("host", "name", "example.com", cordova.config().getConfigXml().toPath());
        assertAttrTag("path", "url", "/some/path", cordova.config().getConfigXml().toPath());
    }

    @Test
    public void add_multiple_tag_same_job() throws IOException {
        final String tag =
                "<universal-links>" +
                    "<host name=\"example.com\">" +
                        "<path url=\"/some/path\" />" +
                        "<path url=\"/some/path2\" />" +
                    "</host>" +
                "</universal-links>";

        cordova.config(new ConfigTransactionJob() {
            @Override
            public void exec(CordovaConfig config) throws IOException {
                config.add(tag);
                config.add(tag);
            }
        });

        assertTag("universal-links", 2, cordova.config().getConfigXml().toPath());
        assertTag("host", 2, cordova.config().getConfigXml().toPath());
        assertTag("path", 4, cordova.config().getConfigXml().toPath());
    }


    public void assertTag(String tag, Path file) {
        assertTag(tag, 1, file);
    }

    public void assertTag(String tag, int times, Path file) {
        try (java.io.InputStream sbis = Files.newInputStream(file)) {
            DocumentBuilderFactory b = DocumentBuilderFactory.newInstance();
            b.setNamespaceAware(false);
            Document doc = b.newDocumentBuilder().parse(sbis);
            NodeList nodeList = doc.getElementsByTagName(tag);

            assertEquals(times, nodeList.getLength());
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalArgumentException("Xml element: " + tag + " is not a valid xml fragment", e);
        }
    }

    public void assertAttrTag(String tag, String attr, String value, Path file) {
        try (java.io.InputStream sbis = Files.newInputStream(file)) {
            DocumentBuilderFactory b = DocumentBuilderFactory.newInstance();
            b.setNamespaceAware(false);
            Document doc = b.newDocumentBuilder().parse(sbis);
            NodeList nodeList = doc.getElementsByTagName(tag);
            Element element = (Element)nodeList.item(0);
            assertNotNull(element.getAttribute(attr));
            assertEquals(value, element.getAttribute(attr));
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            throw new IllegalArgumentException("Xml element: " + tag + " is not a valid xml fragment", e);
        }
    }
}
