package main;

import java.util.Comparator;
import java.util.List;

public class App {
	public static void main(String[] args){
		BinaryTree<String> myTree =  new BinaryTree<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		myTree.addNode("B");
		myTree.addNode("C");
		myTree.addNode("A");
		myTree.addNode("D");
		myTree.addNode("Z");
		myTree.addNode("F");
		myTree.addNode("K");
		myTree.InOrderPrint();
		System.out.println("Min is: " + myTree.findMin());
		System.out.println("Max is: " + myTree.findMax());
		//myTree.printTreeToFile();
		myTree.serializeTree();
		BinaryTree<String> t = new BinaryTree<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		t.deserializeTree();
		t.InOrderPrint();
		//List<Node<String>> myList = myTree.readFromFile();
		//System.out.println("File content is: ");
		//for(Node<String> n : myList)
			//System.out.println(n.getValue());
		//myTree.parseToXML(myList);
		
	}
}
