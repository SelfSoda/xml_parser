/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_parser;

/**
 *
 * @author Lbd
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Xml_parser {

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    //Load and parse XML file into DOM   

    public Document parse(String filePath) {
        Document document = null;
        try {
            //DOM parser instance   
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //parse an XML file into a DOM tree   
            document = builder.parse(new File(filePath));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public void showCategory(String filepath) {
        List<Category> categoryList = new ArrayList<Category>();
        Document document = this.parse(filepath);
        //get root element   
        Element rootElement = document.getDocumentElement();
        NodeList nodeList = rootElement.getElementsByTagName("Category");
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String now = new String();
                if (element.getFirstChild() == null) {
                    continue;
                }
                now = element.getFirstChild().getNodeValue();
                if (now == null) {
                    continue;
                }
                Boolean flag = false;
                for (Category category: categoryList) {
                    if (!now.equals(category.getName())) {
                        continue;
                    } else {
                        category.addCount();
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    categoryList.add(new Category(now));
                }
            }
            System.out.println("共计" + categoryList.size() + "个category");
            Collections.sort(categoryList, new SortByCount());
            try {
                FileWriter writer = new FileWriter("./category.txt");
                writer.write("共计" + categoryList.size() + "个category\n");
                for (Category category: categoryList){
                    writer.write(category.getName() + " --- " + category.getCount()+"\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void getContentByCategory(String filepath, String c)
    {
        String content = new String();
        Document document = this.parse(filepath);
        //get root element   
        Element rootElement = document.getDocumentElement();
        NodeList nodeList = rootElement.getElementsByTagName("ContentGroup");
        if (nodeList == null) return;
        for (int i = 0; i < nodeList.getLength(); i++) {
            
            Element groupElement = (Element) nodeList.item(i);
            //get category from contentgroup
            String category = new String();
            NodeList categoryNode = groupElement.getElementsByTagName("Category");
            if (categoryNode == null) continue;
            Element categoryElement = (Element) categoryNode.item(0);
            if (categoryElement == null) continue;
            if (categoryElement.getFirstChild() == null) continue;
            category = categoryElement.getFirstChild().getNodeValue();
            if (category == null) continue;
            
            if (category.equals(c))
            {
                //get question, questioindetail, answer from contentgroup
                String question = new String();
                NodeList questionNode = groupElement.getElementsByTagName("Question");
                if (questionNode == null) continue;
                Element questionElement = (Element) questionNode.item(0);
                if (questionElement.getFirstChild() == null) continue;
                question = questionElement.getFirstChild().getNodeValue();
                
                String questionDetail = new String();
                NodeList questionDetailNode = groupElement.getElementsByTagName("QuestionDetail");
                if (questionDetailNode == null) continue;
                Element questionDetailElement = (Element) questionDetailNode.item(0);
                if (questionDetailElement.getFirstChild() == null) continue;
                questionDetail = questionDetailElement.getFirstChild().getNodeValue();
                
                String answer = new String();
                NodeList answerNode = groupElement.getElementsByTagName("Answer");
                if (answerNode == null) continue;
                Element answerElement = (Element) answerNode.item(0);
                if (answerElement.getFirstChild() == null) continue;
                answer = answerElement.getFirstChild().getNodeValue();
                
                content += question + questionDetail + answer + "\n";
            }
        }
        try {
            FileWriter writer = new FileWriter("./content.txt");
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        Xml_parser parser = new Xml_parser();
        //parser.showCategory("C:\\Users\\Lbd\\Documents\\BUAA\\实验室\\Data\\前100行_QuestionAnswerAligned.txt");
        parser.getContentByCategory("C:\\Users\\Lbd\\Documents\\BUAA\\实验室\\Data\\前100行_QuestionAnswerAligned.txt","电脑/网络");
    }
}
