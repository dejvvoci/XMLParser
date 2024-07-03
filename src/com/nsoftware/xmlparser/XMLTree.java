package com.nsoftware.xmlparser;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/***
 * The XMLTree class represents an XML document. This class provides methods to
 * parse the XML content, retrieve nodes, add nodes, get content from nodes
 * based on specified paths, get content from nodes based on specified path and
 * index, and also filter the nodes from the tree based on specific conditions.
 */

public class XMLTree {

	private XMLNode root;
	private String declaration;

	/**
	 * Constructor for an XMLTree with the given XML declaration and content.
	 *
	 * @param declaration the XML declaration (e.g., "<?xml version='1.0'?>")
	 * @param content     the XML content to be parsed into the tree
	 * @throws XMLParseException if there is an error in parsing the XML content
	 */

	public XMLTree(StringBuilder content) throws XMLParseException {
		setDeclarationAndRemoveFromContent(content);
		root = parse(content);
	}

	private void setDeclarationAndRemoveFromContent(StringBuilder content) {
		content.trimToSize();
		if (content.substring(0, 2).equals("<?")) {
			int endDeclaration = content.indexOf("?>");
			declaration = content.substring(0, endDeclaration + 2);
			content.delete(0, endDeclaration + 2).trimToSize();
		}
	}
	
	private enum State {
        CONTENT, TAG_NAME, ATTRIBUTE_KEY, ATTRIBUTE_VALUE, ATTRIBUTE_EQUAL, END_TAG, SELF_CLOSING
    }

	/**
	 * Method to parse the given XML content and return the XML root Node.
	 *
	 * @param content: the XML content to be parsed
	 * @return the root Node of the parsed XML content
	 * @throws XMLParseException if there is an error in parsing the XML content
	 */

		
	public XMLNode parse(StringBuilder xmlContent) throws XMLParseException {
	    xmlContent.trimToSize();
	    if (xmlContent.length() == 0) {
	        throw new XMLParseException("Empty content");
	    }

	    Deque<XMLNode> stack = new ArrayDeque<>();
	    XMLNode root = null;
	    StringBuilder content = new StringBuilder();
	    StringBuilder tagName = new StringBuilder();
	    StringBuilder attributeKey = new StringBuilder();
	    StringBuilder attributeValue = new StringBuilder();
	    Map<String, String> attributes = null;
	    State state = State.CONTENT;

	    for (int i = 0; i < xmlContent.length(); i++) {
	        char ch = xmlContent.charAt(i);

	        switch (state) {
	            case CONTENT:
	                if (ch == '<') {
	                    state = State.TAG_NAME;
	                    if (content.length() > 0) {
	                        if (!stack.isEmpty()) {
	                            stack.peek().setContent(content.toString().trim());
	                        }
	                        content.setLength(0);
	                    }
	                } else {
	                    content.append(ch);
	                }
	                break;

	            case TAG_NAME:
	                if (ch == ' ') {
	                    state = State.ATTRIBUTE_KEY;
	                    attributes = new HashMap<>();
	                } else if (ch == '>') {
	                    state = State.CONTENT;
	                    XMLNode node = new XMLNode(tagName.toString(), attributes != null ? attributes : new HashMap<>());
	                    if (root == null) {
	                        root = node;
	                    } else {
	                        stack.peek().getChildren().add(node);
	                   }
	                    stack.push(node);
	                    tagName.setLength(0);
	                } 
	                else if(ch == '/') {
	                	state = State.END_TAG;
	                }
	                else {
	                    tagName.append(ch);
	                }
	                break;

	            case ATTRIBUTE_KEY:
	                if (ch == '=') {
	                    state = State.ATTRIBUTE_EQUAL;
	                } else if (ch == '>') {
	                    state = State.CONTENT;
	                    XMLNode node = new XMLNode(tagName.toString(), attributes);
	                    if (root == null) {
	                        root = node;
	                    } else {
	                        stack.peek().getChildren().add(node);	
	                    }
	                    stack.push(node);
	                    tagName.setLength(0);
	                    attributes = null;
	                } else if (ch == '/') {
	                	state = State.SELF_CLOSING;
	                } else {
	                    attributeKey.append(ch);
	                }
	                break;

	            case ATTRIBUTE_EQUAL:
	                if (ch == '"') {
	                    state = State.ATTRIBUTE_VALUE;
	                    attributeValue.setLength(0);
	                }
	                break;

	            case ATTRIBUTE_VALUE:
	                if (ch == '"') {
	                    attributes.put(attributeKey.toString(), attributeValue.toString());
	                    attributeKey.setLength(0);
	                    attributeValue.setLength(0);
	                    state = State.ATTRIBUTE_KEY;
	                } else {
	                    attributeValue.append(ch);
	                }
	                break;

	            case END_TAG:
	                if (ch == '>') {
	                    state = State.CONTENT;
	                    
	                    if(stack.peekFirst().getTag().equals(tagName.toString())) {
	            			stack.removeFirst();
	            		}
	                    
	                    tagName.setLength(0);
	                } else {
	                    tagName.append(ch);
	                }
	                break;
	                
	            case SELF_CLOSING:
	            	if (ch == '>') {
	                    state = State.CONTENT;
	                    XMLNode selfClosingNode = new XMLNode(tagName.toString(), attributes != null ? attributes : new HashMap<>());
	                    if (root == null) {
	                        root = selfClosingNode;
	                    } else {
	                        stack.peek().getChildren().add(selfClosingNode);
	                    }
	                    tagName.setLength(0);
	                }
	        }
	    }

	    return root;
	}


	/**
	 * Method to add a new node the XML tree structure in the specified parent path.
	 *
	 * @param content:    the XML content to be parsed
	 * @param parentPath: the path to the parent Node
	 * @throws XMLParseException if there is an error in adding a new Node
	 * 
	 */

	public void addNode(StringBuilder content, String parentPath) throws XMLParseException {
		XMLNode parentNode = getNode(parentPath);
		if (parentNode == null) {
			return;
		}
		parentNode.getChildren().add(parse(content));
	}

	/**
	 * Method to return the XML Content of the Node specified by the path
	 * 
	 * @param path: path of the Node
	 * 
	 * @return the XML content
	 * 
	 */

	public String getContent(String path) {
		XMLNode result = getNode(path);
		if (result != null) {
			return result.getContent();
		}
		return null;
	}

	/**
	 * Method to return the XML Content of the Node with a specific index, in the
	 * specified path
	 * 
	 * @param path:  path of the Node
	 * @param index: index of the Node, whose content will be returned
	 * 
	 * @return the XML content
	 * 
	 */

	public String getContent(String path, int index) {
		XMLNode result = getNode(path, index);
		if (result != null) {
			return result.getContent();
		}
		return null;
	}

	/**
	 * Method to return the XML node specified by the path.
	 *
	 * @param path: the path of the node
	 * @return the XML node specified by the path, or null if the node does not
	 *         exist
	 */

	public XMLNode getNode(String path) {
		String[] tags = path.split("/");
		XMLNode current = root;

		if (tags.length == 1 && tags[0].equals(current.getTag())) {
			return current;
		}
		for (int i = 1; i < tags.length - 1; i++) {
			String currentTag = tags[i];
			if (!current.getTag().equals(currentTag)) {
				return null;
			} else {
				String nextTag = tags[i + 1];
				current = current.getChild(nextTag);
			}
		}
		return current;
	}

	/**
	 * Method to return the XML node in a specific index, in a specific path.
	 *
	 * @param path:  the path of the node
	 * @param index: index of the Node to be returned
	 * @return the XML node specified by the path, or null if the node does not exist
	 */

	public XMLNode getNode(String path, int index) {
		XMLNode current = root;
		current = getNode(path);
		List<XMLNode> children = current.getChildren();

		if (children == null || index < 0 || index >= children.size()) {
			return null;
		}
		return children.get(index);
	}

	/**
	 * Method to filter the XML nodes based on a specific condition from a Predicate
	 *
	 * @param p: condition to be tested on xml nodes
	 * 
	 * @return the list with the XML nodes that complete the condition
	 */

	public List<XMLNode> filter(Predicate<XMLNode> p) {
		List<XMLNode> result = new ArrayList<XMLNode>();
		Deque<XMLNode> stack = new ArrayDeque<XMLNode>();
		stack.addFirst(root);
		while (!stack.isEmpty()) {
			XMLNode current = stack.removeFirst();
			if (p.test(current)) {
				result.add(current);
			}
			for (XMLNode e : current.getChildren()) {
				stack.addFirst(e);
			}
		}
		return result;
	}

	/**
	 * Method to return the XML Declaration of the document
	 * 
	 * @return the XML declaration
	 * 
	 */

	public String getDeclaration() {
		return declaration;
	}

	/**
	 * Method to return the XML root of the xml tree
	 * 
	 * @return the XML root
	 * 
	 */

	public XMLNode getRoot() {
		return root;
	}

	/**
	 * toString method of the tree
	 * 
	 * @return the XML content
	 * 
	 */

	@Override
	public String toString() {
		return root.toString();
	}

}