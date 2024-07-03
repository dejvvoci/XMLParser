package com.nsoftware.xmlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * The XMLNode class represents an XML node element.
 * For this node, the class provides methods to access the XML node attributes.
 * 
 * **/

public class XMLNode {

	private String tag;
	private Map<String, String> attributes;
	private String content;
	private List<XMLNode> children;

	/**
     * Constructor for an XMLNode with no tag specified.
     * 
     */
	
	public XMLNode() {
		this(null);
	}
	
	/**
     * Constructor for an XMLNode with tag.
     * 
     * @param tag: tag of the XMLNode
     * 
     */
	
	public XMLNode(String tag) {
		this(tag, (String)null);
	}
	
	/**
     * Constructor for an XMLNode with tag and content.
     * 
     * @param tag: tag of the XMLNode
     * @param content: content of the XMLNode
     * 
     */
	
	public XMLNode(String tag, String content) {
		this.tag = tag;
		this.content = content;
		children = new ArrayList<XMLNode>();
		attributes = new TreeMap<String, String>();
	}
	
	/**
     * Constructor for an XMLNode with tag and attributes.
     * 
     * @param tag: tag of the XMLNode
     * @param attributes: attributes of the XMLNode
     * 
     */
	
	public XMLNode(String tag, Map<String, String> attributes) {
		this(tag, null, attributes, new ArrayList<>());
	}
	
	/**
     * Constructor for an XMLNode with tag, content and attributes.
     * 
     * @param tag: tag of the XMLNode
     * @param content: content of the XMLNode
     * @param attributes: attributes of the XMLNode
     * 
     */
	
	public XMLNode(String tag, String content, Map<String, String> attirubutes) {
		this(tag, content, attirubutes, new ArrayList<XMLNode>());
	}
	
	/**
     * Constructor for an XMLNode with tag, content, and children.
     * 
     * @param tag: tag of the XMLNode
     * @param content: content of the XMLNode
     * @param children: children of the XMLNode
     * 
     */
	
	public XMLNode(String tag, String content, List<XMLNode> children) {
		this(tag, content, new HashMap<String, String>(), children);
	}
	
	/**
     * Constructor for an XMLNode with tag, content, attributes and children.
     * 
     * @param tag: tag of the XMLNode
     * @param content: content of the XMLNode
     * @param attributes: attributes of the XMLNode
     * @param children: children of the XMLNode
     * 
     */
	
	public XMLNode(String tag, String content, Map<String, String> attributes, List<XMLNode> children) {
		this.tag = tag;
		this.content = content;
		this.attributes = attributes;
		this.children = children;
	}
	
	/**
	 * Method to return the tag of the XMLNode
	 * 
	 * @return the XMLNode tag
	 * 
	 * */

	public String getTag() {
		return tag;
	}
	
	/**
	 * Method to return the attributes of the XMLNode
	 * 
	 * @return the XMLNode attributes
	 * 
	 * */

	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	/**
	 * Method to return the content of the XMLNode
	 * 
	 * if there is no content for the XMLNode, 
	 * returns as content is returned the whole XML content of the children
	 * 
	 * @return the XMLNode attributes
	 * 
	 * */

	public String getContent() {
		return content;
	}

	/**
	 * Method to return the children of the XMLNode
	 * 
	 * @return the XMLNode children
	 * 
	 * */
	
	public List<XMLNode> getChildren() {
		return children;
	}
	
	/**
	 * Method to set the value of the tag
	 * 
	 * @param tag: tag of the XMLNode 
	 * 
	 * */
	
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Method to set the attributes
	 * 
	 * @param attributes: attributes of the XMLNode 
	 * 
	 * */
	
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * Method to set the value of the content
	 * 
	 * @param content: content of the XMLNode 
	 * 
	 * */

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Method to set the value of the children
	 * 
	 * @param children: children of the XMLNode 
	 * 
	 * */
	
	public void setChildren(List<XMLNode> children) {
		this.children = children;
	}

	
	/**
	 * Method to get a child from the Node based on a tag specified
	 * 
	 * @param tag: tag of the child that will be searched for
	 * 
	 * @return child: child with specific tag of the XMLNode | or null if the child does not exist for the node
	 * 
	 * */
	public XMLNode getChild(String tag){
		for(XMLNode e : children) {
			if(e.getTag().equals(tag)) {
				return e;
			}
		}
		return null;
	}
	
	public void addChild(XMLNode child) {
		children.add(child);
	}

	
	/**
	 * Method that returns a String value of the XML content of a node
	 * 
	 * 
	 * @return String content of the XML node: 
	 * 
	 * */
	public String getXMLContent() {
		StringBuilder xmlContent = new StringBuilder();
		for(XMLNode node : children) {
			xmlContent = xmlContent.append("<" + node.getTag());
			for (String key : node.getAttributes().keySet()) {
				xmlContent = xmlContent.append(" " + key + "=" + node.getAttributes().get(key));
			}
			xmlContent = xmlContent.append(">");
			for(XMLNode e : node.getChildren()) {
				xmlContent = xmlContent.append("<" + e.getTag());
				for (String key : e.getAttributes().keySet()) {
					xmlContent = xmlContent.append(" " + key + "=" + e.getAttributes().get(key));
				}	
				xmlContent = xmlContent.append(">");
				xmlContent = xmlContent.append(e.getContent());
				xmlContent = xmlContent.append("</" + e.getTag() + ">");
			}
			xmlContent = xmlContent.append(node.getContent());
			xmlContent = xmlContent.append("</" + node.getTag() + ">");
		}
		return xmlContent.toString();
	}
	
	@Override
	public String toString() {
		if(content == null || content.equals("")) {
			content = getXMLContent();
		}
		return "XMLNode [tag=" + tag + ", attributes=" + attributes + ", content=" + content + ", children=" + children + "]";
	}
	
}
