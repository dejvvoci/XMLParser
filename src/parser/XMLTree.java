package parser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

public class XMLTree {

	private GeneralTree<XMLNode> tree;
	private String declaration;
	private String content;

	public XMLTree(String declaration, String content) {
		tree = new GeneralTree<XMLNode>();
		this.declaration = declaration;
		this.content = content;
		addNode(content);
	}

	
	public void addNode(String content) {
        if ((content = content.trim()).isEmpty()) {
            return;
        }

        Deque<XMLNode> stack = new ArrayDeque<>();
        XMLNode currentParent = null;

        while (!content.isEmpty()) {
            Object[] parseData = parse(content);
            if (parseData == null) {
                return;
            }

            XMLNode node = (XMLNode) parseData[0];
            content = (String) parseData[1];

            if (currentParent == null) {
                tree.add(node);
            } else {
                currentParent.getChildren().add(node);
            }

            // Check if there are more sibling nodes to process
            if (node.getContent().contains("<")) {  // Contains nested elements
                if (currentParent != null) {
                    stack.push(currentParent);
                }
                currentParent = node;
            } else {
                // Handle sibling nodes or end of parent
                while (currentParent != null && content.startsWith("</" + currentParent.getTag() + ">")) {
                    content = content.substring(content.indexOf("</" + currentParent.getTag() + ">") + currentParent.getTag().length() + 3).trim();
                    currentParent = stack.poll();
                }
            }
        }
    }

    private Object[] parse(String content) {
        int startIndex = content.indexOf("<");
        int endIndex = content.indexOf(">");
        if (startIndex == -1 || endIndex == -1) {
            return null;  // No valid start tag found
        }

        String startTag = content.substring(startIndex + 1, endIndex);
        // Separate attributes
        String[] startTagContent = startTag.split(" ");
        String tag = startTagContent[0];
        Map<String, String> attributes = new TreeMap<>();
        for (int i = 1; i < startTagContent.length; i++) {
            String attributePair = startTagContent[i];
            int indexOfSeparator = attributePair.indexOf("=");
            String attributeKey = attributePair.substring(0, indexOfSeparator);
            String attributeValue = attributePair.substring(indexOfSeparator + 1).replaceAll("\"", "");
            attributes.put(attributeKey, attributeValue);
        }
        content = content.substring(endIndex + 1);
        String endTag = "</".concat(tag).concat(">");
        int startIndexEndTag = content.indexOf(endTag);
        if (startIndexEndTag == -1) {
            return null;  // No matching end tag found
        }
        int endIndexEndTag = startIndexEndTag + endTag.length();
        String nodeContent = content.substring(0, startIndexEndTag);
        String toBeParsedContent = content.substring(endIndexEndTag).trim();
        return new Object[]{new XMLNode(tag, nodeContent, attributes), toBeParsedContent};
    }


    // Assuming you have XMLNode and GeneralTree classes with appropriate methods
//}
	
	/// metode qe shton: root ose femijet e saj
//	public void addNode(String content) {
//		if ((content = content.trim()).isEmpty()) {
//			return;
//		}
//		while (!content.isEmpty()) {
//			Object[] parseData = parse(content);
//			XMLNode node = (XMLNode) parseData[0];
//			content = (String) parseData[1];
//			tree.add(node);
//		}
//	}
//
//	private Object[] parse(String content) {
//		int startIndex = content.indexOf("<");
//		int endIndex = content.indexOf(">");
//		String startTag = content.substring(startIndex + 1, endIndex);
//		/// duhet te vecohen atributet
//		String[] startTagConent = startTag.split(" ");
//		String tag = startTagConent[0];
//		Map<String, String> attributes = new TreeMap<String, String>();
//		for (int i = 1; i < startTagConent.length; i++) {
//			String attributePair = startTagConent[i];
//			int indexOfSeperator = attributePair.indexOf("=");
//			String attributeKey = attributePair.substring(0, indexOfSeperator);
//			String attributeValue = attributePair.substring(indexOfSeperator + 1).replaceAll("\"", "");
//			attributes.put(attributeKey, attributeValue);
//		}
//		content = content.substring(endIndex + 1);
//		String endTag = "</".concat(tag).concat(">");
//		int startIndexEndTag = content.indexOf(endTag);
//		int endIndexEndTag = startIndexEndTag + endTag.length();
//		String nodeContent = content.substring(0, startIndexEndTag);
//		String toBeParsedContent = content.substring(endIndexEndTag);
//		return new Object[] { new XMLNode(tag, nodeContent, attributes), toBeParsedContent };
//	}

	public void addNode(String content, String parentPath) {
		XMLNode parentNode = getNode(parentPath);
		if (parentNode == null) {
			return;
		}
		if ((content = content.trim()).isEmpty()) {
			return;
		}
		while (!content.isEmpty()) {
			Object[] parseData = parse(content);
			XMLNode node = (XMLNode) parseData[0];
			content = (String) parseData[1];
			parentNode.getChildren().add(node);
		}
	}

	/// metode qe jep path dhe te jep content: String dhe node
	public String getContent() {
		return content;
	}

	public String getContent(String path) {
		XMLNode result = getNode(path);
		if (result != null) {
			return result.getContent();
		}
		return null;
	}

	public XMLNode getNode(String path) {
		String[] tags = path.split("/");
		XMLNode current = tree.get(0);
		if (tags.length == 1 && tags[0].equals(current.getTag())) {
			return current;
		}
		for (String tag : tags) {
			if (current.getTag().equals(tag)) {
				current = current.getChildren(tag);
			} else {
				return null;
			}
		}
		return current;
	}

	public String getDeclaration() {
		return declaration;
	}

	public GeneralTree<XMLNode> getTree() {
		return tree;
	}

	@Override
	public String toString() {
		return tree.toString();
	}

}