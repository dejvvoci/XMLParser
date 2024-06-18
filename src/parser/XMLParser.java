//package parser;
//
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import beans.Order;
//  
//public class XMLParser {
//  
//     public void parseWithDOM(String filePath) throws ParserConfigurationException, SAXException, IOException {
//  
//        //Get Document Builder
//          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//          DocumentBuilder builder = factory.newDocumentBuilder();
//  
//          // Load the input XML document, parse it and return an instance of the
//          // Document class.
//          Document document = builder.parse(new File(filePath));
//  
//          List<Order> orders = new ArrayList<Order>();
//          NodeList nodeList = document.getDocumentElement().getChildNodes();
//          for (int i = 0; i < nodeList.getLength(); i++) {
//               Node node = nodeList.item(i);
//  
//               if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element elem = (Element) node;
//  
//                    // Marrim vleren e atributut id
//                    String ID = node.getAttributes().getNamedItem("id").getNodeValue();
//  
//                    // Marrim vleren e nen-elementit amount.
//                    int amount = Integer.parseInt(elem.getElementsByTagName("amount")
//                    				.item(0).getChildNodes().item(0).getNodeValue());
//  
//                    orders.add(new Order(ID, amount));
//               }
//          }
//  
//          // .
//          for (Order o : orders) {
//        	  if(o.getAmount() > 100) {
//                  System.out.println(o.toString());
//        	  }
//          }
//     }
//     
//     
//     public void parse(String filePath) {
//    	 File file = new File(filePath);
//    	 Scanner sc = null;
//		try {
//			sc = new Scanner(file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		boolean insideElement;
//		boolean insideComment = false;
//		
//		String xmlContent = "";
//		
//		while(sc.hasNextLine()) {
//			xmlContent.concat(sc.nextLine());
//		}
//        System.out.println(sb);
//		
//        
//         while (sc.hasNextLine()) {
//        	 sb.append(sc.nextLine().trim());
////             String rowContent = sc.nextLine().trim();
////             System.out.println("Row: " + rowContent);
////             if(rowContent.startsWith("!--")) {
////            	 insideComment = true;
////             }
////             if(insideComment && rowContent.endsWith("-->")) {
////            	 insideComment = false;
////             }
////             if(rowContent.startsWith("</")) {
////            	 /// end of element
////            	 insideElement= false;
////            	 continue;
////             }
////             else if(rowContent.startsWith("<")) {
////            	 rowContent = rowContent.substring(rowContent.indexOf('<') + 1);
////            	 insideElement  = true;
////            	 if(rowContent.startsWith("root")){
////            		 // logic
////            		 continue;
////            	 }
////            	 else {
////                	 String header = rowContent.substring(0, rowContent.indexOf(" ") >= 0 ? rowContent.indexOf(" ") : rowContent.indexOf(">"));
////                	 rowContent = removeSubstring(rowContent, header);
////                	 System.out.println("Header: " + header);
////                	 if(rowContent.startsWith(">") && insideElement) {
//////                		 continue;
////                		 String content = rowContent.substring(1, rowContent.indexOf("<"));
////                		 System.out.println("Content of the element " + header + ": " + content);
////                	 }else {
////                		 do {
////                			 String attributeKey = rowContent.substring(0, rowContent.indexOf("="));
////                			 System.out.println("Attribute Key: " + attributeKey);
////                			 rowContent = rowContent.substring(rowContent.indexOf("=") + 2);
////                			 String attributeValue = rowContent.substring(0, rowContent.indexOf("\""));
////                			 System.out.println("Attribute Value: " + attributeValue);
////                			 rowContent = rowContent.substring(rowContent.indexOf("\"") + 1);
////                		 }while(rowContent.contains("="));
////                	 }
////                	 
////            	 }
//////            	 StringBuilder sb = new StringBuilder(str.substring(str.indexOf('<') + 1));
////             }
//         }
//         sc.close();
//     }
//     
//     
//     public static String removeSubstring(String str, String specificString) {
//         int index = str.indexOf(specificString);
//         if (index == -1) {
//             return str;
//         }
//        
//         int startIndex = index + specificString.length();
//         return str.substring(startIndex).trim();
//     }
//     
//}
//
//
////
//////1. XMLElement 
////	Header -> attributes