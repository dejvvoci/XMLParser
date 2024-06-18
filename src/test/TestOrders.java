package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import parser.XMLTree;

public class TestOrders {
	
	public static void main(String[] args) {
		
    	StringBuilder content = new StringBuilder();
		
		String filePath = "orders.xml"; // replace with your file path
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        XMLTree xmlTree = new XMLTree("", content.toString());
        System.out.println(xmlTree);
        
//        System.out.println(xmlTree.getNode("root/order"));
		
	}

}
