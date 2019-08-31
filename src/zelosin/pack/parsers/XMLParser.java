package zelosin.pack.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import zelosin.pack.objects.DNValue;
import zelosin.pack.utils.GlobalData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class XMLParser {
    static private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    static private DocumentBuilder mDocumentBuilder;
    static private Document mDocument;
    static private final String XML_PATH = "zelosin/pack/xml/data.xml";
    static private final String CODE_XML_PATH = "zelosin/pack/xml/codex.xml";

    private static void createParser(String path) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        mDocumentBuilder = documentBuilderFactory.newDocumentBuilder();
        mDocument = mDocumentBuilder.parse(XMLParser.class.getClassLoader().getResource(path).toString());
    }
    public static void loadCodes(){
        getDocument(CODE_XML_PATH);
        NodeList mElements = mDocument.getElementsByTagName("CODE");
        for(int index = 0; index<mElements.getLength();index++) {
            Node mTempNode = mElements.item(index);
            if (mTempNode.getNodeType() == Node.ELEMENT_NODE) {
                Element mTempElement = (Element) mTempNode;
                GlobalData.mMessageCodes.put(Integer.valueOf(mTempElement.getElementsByTagName("VALUE").item(0).getTextContent()),
                        mTempElement.getElementsByTagName("TEXT").item(0).getTextContent());
                GlobalData.mMessageLevel.put(Integer.valueOf(mTempElement.getElementsByTagName("VALUE").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("LEVEL").item(0).getTextContent()));
            }
        }
    }
    public static void loadData(){
        getDocument(XML_PATH);
        NodeList mElements = mDocument.getElementsByTagName("DATA");
        for(int index = 0; index<mElements.getLength();index++){
            Node mTempNode = mElements.item(index);
            if(mTempNode.getNodeType() == Node.ELEMENT_NODE){
                Element mTempElement = (Element) mTempNode;
                new DNValue(mTempElement.getElementsByTagName("DENOMINATION").item(0).getTextContent(),
                        Integer.valueOf(mTempElement.getElementsByTagName("SLICE").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("WINDOW").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("POSITION").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("CAPACITY").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("MAX_VALUE").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("MIN_VALUE").item(0).getTextContent()),
                        Float.valueOf(mTempElement.getElementsByTagName("K_RATION").item(0).getTextContent()),
                        Float.valueOf(mTempElement.getElementsByTagName("B_RATION").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("MIN_WARNING").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("MAX_WARNING").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("MIN_ALARM").item(0).getTextContent()),
                        Integer.valueOf(mTempElement.getElementsByTagName("MAX_ALARM").item(0).getTextContent())
                );
            }
        }
    }

    private static void getDocument(String xmlPath) {
        try {
            createParser(xmlPath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        mDocument.getDocumentElement();
    }
}
















