package com.upplication.cordova.util;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
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
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class ConfigProcessorTest {

    private FileSystem fs;
    private DocumentGetter document;
    private Path configFile;
    private ConfigProcessor processor;
    private ConfigProcessorDocument processorDocument;

    @Before
    public void setup() throws Exception {
        this.fs = MemoryFileSystemBuilder.newLinux().build(UUID.randomUUID().toString());

        final Document[] doc = new Document[1];
        this.document = new DocumentGetter(doc);
        this.processorDocument = mock(ConfigProcessorDocument.class);
        this.configFile = createConfigXmlFile();
        this.processor = spy(new ConfigProcessor(configFile));
        doAnswer(new Answer<ConfigProcessorDocument>() {
            @Override
            public ConfigProcessorDocument answer(InvocationOnMock invocationOnMock) throws Throwable {
                doc[0] = (Document)invocationOnMock.getArguments()[0];
                return processorDocument;
            }
        }).when(processor).getProcessor(any(Document.class));
    }



    @Test
    public void when_set_name_then_open_the_file_and_call_ConfigProcessorDocument_and_close() throws Exception {

        String name = "app name cool";
        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setName(name);

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setName(eq(name));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_version_then_open_the_file_and_call_ConfigProcessorDocument_and_close() throws Exception {

        String version = "2.0.0";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setVersion(version, null, null);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setVersion(eq(version), isNull(String.class), isNull(Integer.class));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_version_ios_then_attr_version_ios_is_changed() throws Exception {

        String version = "2.0.0";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setVersion(version, version, null);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setVersion(eq(version), eq(version), isNull(Integer.class));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_version_android_then_attr_version_android_is_changed() throws Exception {

        String version = "2.0.0";
        Integer versionAndroid = 2;

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setVersion(version, version, versionAndroid);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setVersion(eq(version), eq(version), eq(versionAndroid));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_author_email_then_author_email_is_changed() throws Exception {

        String email = "email@email.com";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setAuthorEmail(email);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setAuthorEmail(eq(email));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_author_name_then_author_content_is_changed() throws Exception {

        String authorName = "Upplication Software";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setAuthorName(authorName);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setAuthorName(eq(authorName));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_author_href_then_href_attr_is_changed() throws Exception {

        String authorHref = "upplication.com";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setAuthorHref(authorHref);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setAuthorHref(eq(authorHref));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_description_then_description_node_content_is_changed() throws Exception {

        String description = "description";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.setDescription(description);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).setDescription(eq(description));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    @Test
    public void when_set_allowNavigation_then_allowNavigation_node_is_added() throws Exception {

        String href = "*";

        InOrder inOrder = inOrder(processor, processorDocument);

        processor.addAllowNavigation(href);

        // assert

        inOrder.verify(processor).openConfig(eq(configFile));
        inOrder.verify(processor).getProcessor(eq(document.get()));
        inOrder.verify(processorDocument).addAllowNavigation(eq(href));
        inOrder.verify(processor).saveConfig(eq(configFile), eq(document.get()));
    }

    // helpers

    private Path createConfigXmlFile() throws Exception {
        // write the content into xml file
        Path file = fs.getPath("/" + UUID.randomUUID().toString(), "config.xml");
        Files.createDirectories(file.getParent());
        Files.write(file, xmlDefault.getBytes(), StandardOpenOption.CREATE_NEW);
        return file;
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


    private static class DocumentGetter {
        private Document[] document;
        public DocumentGetter(Document[] document) {
            this.document = document;
        }

        public Document get() {
            return this.document[0];
        }
    }
}