package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLNode {

	private String tag;
	private Map<String, String> attributes;
	private String content;
	private List<XMLNode> children;
	
	public XMLNode(String tag) {
		this(tag, (String)null);
	}
	
	public XMLNode(String tag, String content) {
		this.tag = tag;
		this.content = content;
		children = new ArrayList<XMLNode>();
	}
	
	public XMLNode(String tag, String content, Map<String, String> attirubutes) {
		this(tag, content, attirubutes, new ArrayList<XMLNode>());
	}
	
	public XMLNode(String tag, String content, List<XMLNode> children) {
		this(tag, content, new HashMap<String, String>(), children);
	}
	
	public XMLNode(String tag, String content, Map<String, String> attributes, List<XMLNode> children) {
		this.tag = tag;
		this.content = content;
		this.attributes = attributes;
		this.children = children;
	}

	public String getTag() {
		return tag;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public String getContent() {
		return content;
	}

	public List<XMLNode> getChildren() {
		return children;
	}
	
	public XMLNode getChildren(String tag){
		for(XMLNode e : children) {
			if(e.getTag().equals(tag)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "XMLNode [tag=" + tag + ", attributes=" + attributes + ", content=" + content + ", children=" + children
				+ "]";
	}
	
	// metode qe kthen xmlNode qe ka ne content ose ne children nje specific value

	
	// merr si argument emrin e tag-ut dhe kthen femijet e tij, nqs e gjen
	
	
}
