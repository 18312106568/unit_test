package com.creditcloud.jpa.unit_test.dom;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDocument {
    String COMMENT = "#comment";
    String TEXT = "#text";

    String EMPTY_STR = "";
    @Test
    public void testDocumentBuilderFactory() throws ParserConfigurationException, IOException, SAXException {
         File f = new File("E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata\\form-config.xml");
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(f);
         NodeList nodeList = doc.getChildNodes();
         Node rootNode = null;
         for(int i=0;i<nodeList.getLength();i++){
             if(COMMENT.equals(nodeList.item(i).getNodeName())){
                 continue;
             }

             //System.out.println(nodeList.item(i).getNodeName());
             rootNode = nodeList.item(i);
             break;
         }
         //System.out.println(printNode(rootNode,0));
         System.out.println(rootNode.getFirstChild().getTextContent());
    }

    @Test
    public void testJdom() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        org.jdom2.Document doc = builder.build( new File("E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata\\form-config.xml"));
        Element root = doc.getRootElement();
        System.out.println(printNode(root,0));

    }

    private String printNode(Node node,int indentNum){
        StringBuilder sb = new StringBuilder();
        if(node.getNodeName().startsWith("#")){
            return sb.toString();
        }
        String nodeName = node.getNodeName();
        NamedNodeMap namedNodeMap = node.getAttributes();
        NodeList childNodeList = node.getChildNodes();
        sb.append(printIndent(indentNum));
        sb.append(String.format("<%s%s>\r\n",nodeName,printAttributes(namedNodeMap)));
        if(childNodeList.getLength()==0){
            sb.append(printIndent(indentNum+1));
            sb.append(node.getTextContent()).append("\r\n");
        }
        for(int i=0;i<childNodeList.getLength();i++){
            String childNodeValue = printNode(childNodeList.item(i),indentNum+1);
            if(!StringUtils.isEmpty(childNodeValue)){
               sb.append(printNode(childNodeList.item(i),indentNum+1));
            }
        }
        sb.append(printIndent(indentNum));
        sb.append(String.format("</%s>\r\n",nodeName));
        return sb.toString();
    }

    private String printAttributes(NamedNodeMap namedNodeMap){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<namedNodeMap.getLength();i++){
            sb.append(String.format(" %s=\"%s\"",namedNodeMap.item(i).getNodeName(),namedNodeMap.item(i).getNodeValue()));
        }
        return sb.toString();
    }

    private String printIndent(int indentNum){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<indentNum;i++){
            sb.append('\t');
        }
        return sb.toString();
    }

    private Boolean isText( NodeList childNodeList){
        for(int i=0;i<childNodeList.getLength();i++){
            if(TEXT.equals(childNodeList.item(i).getNodeName())){
                return true;
            }
        }
        return false;
    }


    private String printNode(Element node,int indentNum){
        StringBuilder sb = new StringBuilder();
        if(node.getName().startsWith("#")){
            return sb.toString();
        }
        String nodeName = node.getName();
        String text = node.getText().trim();
        List<Attribute> attributes =node.getAttributes();
        List<Element> childNodeList =node.getChildren();
        sb.append(printIndent(indentNum));
        sb.append(String.format("<%s%s>\r\n",nodeName,printAttributes(attributes)));
        if(!StringUtils.isEmpty(text)){
            sb.append(printIndent(indentNum+1));
            sb.append(text).append("\r\n");
        }
        for(Element childNode : childNodeList){
            String childNodeValue = printNode(childNode,indentNum+1);
            if(!StringUtils.isEmpty(childNodeValue)){
                sb.append(printNode(childNode,indentNum+1));
            }
        }
        sb.append(printIndent(indentNum));
        sb.append(String.format("</%s>\r\n",nodeName));
        return sb.toString();
    }

    private String printAttributes(List<Attribute> attributeList){
        StringBuilder sb = new StringBuilder();
        for(Attribute attribute : attributeList){
            String namespace = attribute.getNamespace().getPrefix();
            String attributeName = attribute.getName();
            if(!StringUtils.isEmpty(namespace)){
                attributeName = String.format("%s:%s",namespace,attributeName);
            }
            sb.append(String.format(" %s=\"%s\"",attributeName,attribute.getValue()));
        }
        return sb.toString();
    }

}
