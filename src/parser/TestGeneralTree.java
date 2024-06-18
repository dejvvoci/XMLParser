package parser;

public class TestGeneralTree {
	
	public static void main(String[] args) {
		
		GeneralTree<XMLNode> xmlTree = new GeneralTree<>();
		
		xmlTree.add(new XMLNode("college"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().add(new XMLNode("student"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().add(new XMLNode("firstname", "Tamanna"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().add(new XMLNode("lastname", "Bhatia"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().add(new XMLNode("contact", "21521561"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().add(new XMLNode("email", "tammanabhatia@yaho.com"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().add(new XMLNode("address"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().get(4).getChildren().add(new XMLNode("city", "Ghaziabad"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().get(4).getChildren().add(new XMLNode("state", "Uttar Pradesh"));
		xmlTree.getOnCondition(e -> e.getTag().equals("college")).getChildren().get(0).getChildren().get(4).getChildren().add(new XMLNode("pin", "201007"));
		  
//		generalTree.add(10.);
//		generalTree.add(7.);
//		generalTree.add(5.);
//		generalTree.add(4.);
//		generalTree.add(1., 5.);
//		generalTree.add(2., 5.);
//		generalTree.add(3., 5.);
//		generalTree.add(41., 5.);
//		generalTree.add(31., 7.);
//		generalTree.add(-19., 7.);
//		generalTree.add(6., 4.);
//		generalTree.add(55., 4.);
//		generalTree.add(-1., 31.);
//		generalTree.add(-2., 31.);
//		generalTree.add(-3., 31.);
//		/****
//		 *							10
//		 *			7				5				4
//		 *		31	   -19	 	1 2 3 41		6		55
//		 *	-1 -2 -3
//		 **/ 
//		System.out.println(generalTree.size());  
//		System.out.println(generalTree);
//		System.out.println(generalTree.removeAllIncludingChildrenOnCondition(e -> e % 4 == 0)); 
//		System.out.println(generalTree.size());
		System.out.println(xmlTree);
//		
	}

}
