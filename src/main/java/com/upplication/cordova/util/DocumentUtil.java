package com.upplication.cordova.util;



import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DocumentUtil {

    public DocumentBuilder newDocumentBuilder() {

        try {
            return  factory().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("Unknown error creating document builder", e);
        }
    }

    public LSSerializer serializer() {

        DOMImplementationLS lsImpl = (DOMImplementationLS) newDocumentBuilder()
                .getDOMImplementation()
                .getFeature("LS", "3.0");
        LSSerializer lsSerializer = lsImpl.createLSSerializer();
        lsSerializer.getDomConfig().setParameter("xml-declaration", false);
        return lsSerializer;
    }

    private DocumentBuilderFactory factory() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setNamespaceAware(false);
        return documentBuilderFactory;
    }
}
