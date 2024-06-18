package beans;

import java.util.List;
import java.util.TreeMap;

public class XMLElement {

	public String tagName;
	public TreeMap<String, String> attributes;
	public List<XMLElement> subelements; /// may be some subelements.....
	
	public XMLElement() {
		
	}
	
	protected void  setAttribute(String key, String value) {
		
	}
	
	public String getTagName() {
		return tagName;
	}
	
	
}
