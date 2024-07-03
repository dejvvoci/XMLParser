package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.nsoftware.xmlparser.XMLParseException;
import com.nsoftware.xmlparser.XMLTree;

public class TestParser {
	
	public static void main(String[] args) throws XMLParseException {
		
    	StringBuilder content = new StringBuilder();
		
		String filePath = "large.xml";
		System.out.println("Reading from file");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Creating the XML Tree");
        long startTime = System.currentTimeMillis();
        XMLTree xmlTree = new XMLTree(content);
        long endTime = System.currentTimeMillis();
        System.out.println("Finished creating the tree in: " + (endTime - startTime));
        

        System.out.println(xmlTree);
        
	}
}
