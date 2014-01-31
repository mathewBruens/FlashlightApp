/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spotlightppm;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author mbruens
 */
public class LocalUserSettings {

    private File file = null;
    private String uri = "";
    private String username = "";
    private String password = "";
    private String webcamName = "";
    private String screenName = "";
    private DocumentBuilder docBuilder = null;

    private LocalUserSettings() {
        try {
            uri = "UserSettings.xml";
            file = new File(uri);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            readXML();
        } catch (ParserConfigurationException ex) {
        }
    }

    public static LocalUserSettings getInstance() {
        return NewSingletonHolder.INSTANCE;

    }

    private static class NewSingletonHolder {

        private static final LocalUserSettings INSTANCE = new LocalUserSettings();
    }

    public String getUsername() {
        return username;
    }

    public String getWebcamName() {
        return webcamName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void writeWebcamName(final String newWebcam) {

        Thread t = new Thread() {
            public void run() {
                webcamName = newWebcam;

                try {
                    Document doc = docBuilder.parse(file);

                    Node webcamNode = doc.getDocumentElement().getElementsByTagName("webcam").item(0);

                    webcamNode.setTextContent(webcamName);

                    fileWrite(doc);
                } catch (SAXException | IOException ex) {
                    //Logger.getLogger(LoginXML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }

    public void writeScreenName(final String newScreen) {

        Thread t = new Thread() {

            public void run() {
                screenName = newScreen;
                try {
                    Document doc = docBuilder.parse(file);

                    Node webcamNode = doc.getDocumentElement().getElementsByTagName("screen").item(0);

                    webcamNode.setTextContent(screenName);

                    fileWrite(doc);
                } catch (SAXException | IOException ex) {
                    //Logger.getLogger(LoginXML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }

    public String getPassword() {
        return password;
    }

    private void readXML() {

        if (!file.exists()) {
            writeXMLSkeleton();
        } else {
            try {
                Document doc = docBuilder.parse(file);
                Node usernameNode = doc.getDocumentElement().getElementsByTagName("username").item(0);
                Node passwordNode = doc.getDocumentElement().getElementsByTagName("password").item(0);
                Node screenNode = doc.getDocumentElement().getElementsByTagName("screen").item(0);
                Node webcamNode = doc.getDocumentElement().getElementsByTagName("webcam").item(0);
                username = usernameNode.getTextContent();
                password = passwordNode.getTextContent();
                webcamName = webcamNode.getTextContent();
                screenName = screenNode.getTextContent();
                
            } catch (SAXException | IOException ex) {
                // Logger.getLogger(LocalUserSettingsXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeLoginCredentials(String newUsername, String newPassword) {
        try {
            username = newUsername;
            password = newPassword;

            Document doc = docBuilder.parse(file);

            Node usernameNode = doc.getDocumentElement().getElementsByTagName("username").item(0);
            Node passwordNode = doc.getDocumentElement().getElementsByTagName("password").item(0);
            usernameNode.setTextContent(username);
            passwordNode.setTextContent(password);

            fileWrite(doc);
        } catch (SAXException | IOException ex) {
            //Logger.getLogger(LoginXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void fileWrite(Document doc) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        } catch (TransformerException ex) {
            //Logger.getLogger(NewSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void writeXMLSkeleton() {

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("settings");
        Element usernameElement = doc.createElement("username");
        Element passwordElement = doc.createElement("password");
        Element webcamElement = doc.createElement("webcam");
        Element screenElement = doc.createElement("screen");
        doc.appendChild(rootElement);
        rootElement.appendChild(usernameElement);
        rootElement.appendChild(passwordElement);
        rootElement.appendChild(webcamElement);
        rootElement.appendChild(screenElement);

        fileWrite(doc);

    }
}
