package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.utils.FileUtils;
import org.htmlcleaner.*;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestXpath {

    public static String UPBIT_XPATH = "//library[@name='lib']//archive";

    public static String DEPENDENCE_XPATH = "//dependency";

    public static String XML_FILE_1 = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\_validation.xml";

    public static String POM_XML = "E:\\project\\unit_test\\pom.xml";

    @Test
    public void testPom() throws FileNotFoundException, XPatherException {
        String contents = readFile(POM_XML);
        HtmlCleaner hc = new HtmlCleaner();
        TagNode tn = hc.clean(contents);
        Object[] objects = tn.evaluateXPath(DEPENDENCE_XPATH);

        for(Object obj : objects){
            TagNode tagNode = (TagNode)obj;
            List<TagNode> childTagList= tagNode.getChildTagList();
            TagNode[] grouIds = tagNode.getElementsByName("groupId",true);
            if(grouIds.length!=0){
                System.out.println(grouIds[0].getName()+"-"+grouIds[0].getText());
            }
        }
    }

    @Test
    public void testScanPath(){
        String metadataPath = "E:\\project\\est\\lms\\wms\\wms\\WEB-INF\\conf\\metadata";
        List<String> metadataFileNames = FileUtils.deepFiles(metadataPath);
        for(String metadataFileName : metadataFileNames){
            if(metadataFileName.endsWith(".xml") && metadataFileName.indexOf("复件")==-1){
                System.out.println(metadataFileName);
            }
        }
    }

    @Test
    public void testReadXml() throws IOException, XPatherException, ParserConfigurationException {


        String contents = readFile(XML_FILE_1);
        HtmlCleaner hc = new HtmlCleaner();
        TagNode tn = hc.clean(contents);
        String xpath = "//form";
        Object[] objects = tn.evaluateXPath(xpath);
        CleanerProperties cleanerProperties = new CleanerProperties();
        cleanerProperties.setKeepWhitespaceAndCommentsInHead(true);


    //    OutputStream os = new FileOutputStream(new File("E:\\tmp\\1.xml"),true);
        System.out.println(objects.length);
        for(Object obj : objects){
//            System.out.println(obj.getClass());
            SimpleXmlSerializer serializer = new SimpleXmlSerializer(cleanerProperties);
            TagNode tagNode = (TagNode)obj;
            Map<String,String> attributes = tagNode.getAttributes();
            for(String key : attributes.keySet()){
                System.out.println(key);
            }
          //  tagNode.serialize(serializer,new PrintWriter(os));
        }

        //Element root = document.createElement("");

    }

    @Test
    public void testXpath() throws XPatherException, FileNotFoundException {
        String contents = readFile("E:\\project\\est\\lms\\helixcore3\\uses-this.userlibraries");
        //System.out.println(contents);
        long start = System.currentTimeMillis();
        HtmlCleaner hc = new HtmlCleaner();
        TagNode tn = hc.clean(contents);
        String xpath = UPBIT_XPATH;//"//div[@id='lcontentnews']//div[@class='latnewslist']//div[@class='entry']//a";//"//a[@class='article-list-link']";
        Object[] objects = tn.evaluateXPath(xpath);
        for(Object obj : objects){
            TagNode tagNode = (TagNode)obj;
            Map<String,String> atrMap = tagNode.getAttributes();
            StringBuilder sb = new StringBuilder();
            for(String key : atrMap.keySet()){
                sb.append(String.format("%s:%s ",key,atrMap.get(key)));
            }
            System.out.println(sb.toString());
//            String[] texts =tagNode.getText().toString().trim().split("\r\n");
//            if(texts.length!=0){
//                //tagNode.getName()+texts[0]+
//                System.out.println(tagNode.getAttributeByName("path"));
//            }

            // annMap.put()
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }

    @Test
    public void testReadFile() throws FileNotFoundException {
        String content = readFile("E:\\project\\est\\lms\\helix-job\\helix-job.jpx");
        System.out.println(content);
    }

    private String readFile(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        File xFile = new File(fileName);
        Scanner scanner = new Scanner(new FileInputStream(xFile));
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            sb.append(line);
        }
        return sb.toString();
    }

}
