import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLReaderDOM {

    public static void main(String[] args) {
        String filePath = "emps.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
        	//create an instance of the DocumentBuilder
            dBuilder = dbFactory.newDocumentBuilder();
            //Parse the content of the given InputStream as an XML document and return a new DOM Document object.
            Document doc = dBuilder.parse(xmlFile);
            //access to the child node that is the document element of the document
            //and "validate" parameter to verify if the value matches the validity constraint for standalone document declaration as defined in [XML 1.0]
            doc.getDocumentElement().normalize();
            //get the Root element node name
            System.out.println("Root element is : " + doc.getDocumentElement().getNodeName());
            //returns a collection of all elements in the document with the specified tag name, as a NodeList object
            NodeList nodeList = doc.getElementsByTagName("Employee");
            //now XML is loaded as Document in memory, convert it to Object List
            List<Employee> empList = new ArrayList<Employee>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                empList.add(getEmployee(nodeList.item(i)));
            }
            //print Employee list information
            for (Employee emp : empList) {
                System.out.println(emp.toString());
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

    }


    private static Employee getEmployee(Node node) {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        Employee emp = new Employee();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            emp.setName(getTagValue("name", element));
            emp.setAge(Integer.parseInt(getTagValue("age", element)));
            emp.setGender(getTagValue("gender", element));
            emp.setRole(getTagValue("role", element));
        }

        return emp;
    }


    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}
