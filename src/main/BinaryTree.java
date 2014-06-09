package main;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class BinaryTree<E> {
	private Node<E> root;
	private Comparator<E> cmp;

	public enum Direction {
		LEFT, RIGHT;
	}

	public Node<E> getRoot() {
		return root;
	}

	public void setRoot(Node<E> root) {
		this.root = root;
	}

	public BinaryTree(Comparator<E> cmp) {
		this.cmp = cmp;
	}

	public boolean addNode(E value) {

		if (this.root == null) {
			this.root = new Node<E>(value, null);
			System.out.println("New tree created");
			return true;
		}
		try {
			Node<E> posNode = findPlace(value, this.root);
			if (cmp.compare(value, posNode.getValue()) > 0) {
				posNode.setRight(new Node<E>(value, posNode));
			} else if (cmp.compare(value, posNode.getValue()) < 0) {
				posNode.setLeft(new Node<E>(value, posNode));
			}
		} catch (NullPointerException e) {
			System.out.println("Item already exists!");
		}
		return false;

	}

	public Node<E> findPlace(E value, Node<E> root) {
		Node<E> current = root;
		while (current != null) {
			int compareResult = cmp.compare(value, current.getValue());
			if (compareResult > 0) {
				if (current.getRight() == null
						|| cmp.compare(value, root.getValue()) == 0) {
					return current;
				}
				current = current.getRight();
			} else if (compareResult < 0) {
				if (current.getLeft() == null
						|| cmp.compare(value, current.getValue()) == 0) {
					return current;
				}
				current = current.getLeft();
			} else {
				return null;
			}

		}
		if (current == null) {
			System.out.println("Current is null;");
		}
		return null;
	}

	// -----------------------------------------------------------------
	public int size() {
		return size(this.root);
	}

	private int size(Node<E> node) {
		if (node == null)
			return 0;
		return size(node.getLeft()) + size(node.getRight());
	}

	// -----------------------------------------------------------------
	public void InOrderPrint() {
		InOrderPrint(this.root);
	}

	private void InOrderPrint(Node<E> node) {
		if (node == null) {
			return;
		}
		InOrderPrint(node.getLeft());
		System.out.println(node.getValue());
		InOrderPrint(node.getRight());

	}

	// -------------------------------------------------------------------

	public E findMin() {
		return find(Direction.LEFT);
	}

	public E findMax() {
		return find(Direction.RIGHT);
	}

	public E find(Direction dir) {
		Node<E> current = this.root;
		while (current != null) {
			if (dir.LEFT.equals(dir)) {
				if (current.getLeft() == null)
					return current.getValue();
				current = current.getLeft();
			} else {
				if (current.getRight() == null)
					return current.getValue();
				current = current.getRight();
			}
		}
		return null;
	}

	public void printTreeToFile() {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("/home/nazi/Desktop/Mytree.ser");
			ObjectOutputStream out;
			out = new ObjectOutputStream(fileOut);
			printTreeToFile(this.root, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void printTreeToFile(Node<E> node, ObjectOutputStream out) {
		if (node == null) {
			return;
		}
		printTreeToFile(node.getLeft(), out);
		try {
			out.writeObject(node);
			System.out.println(node.getValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
		printTreeToFile(node.getRight(), out);
	}

	@SuppressWarnings("unchecked")
	public List<Node<E>> readFromFile() {
		List<Node<E>> eList = new ArrayList<Node<E>>();
		try {
			FileInputStream fileIn = new FileInputStream("/home/nazi/Desktop/MytreeDeep.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while(true){
				try{
					eList.add((Node<E>) in.readObject());
				}catch(EOFException e){
					return eList;
				}
			}	
		} catch (IOException i) {
			i.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();

		}
		return null;
	}
	
	//------------------------------------------------------------------------------
	public void parseToXML(List<Node<E>> nodes){
		try{
			File file = new File("/home/nazi/Desktop/Mytree.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			for(Node<E> n : nodes){
				jaxbMarshaller.marshal(n, file);
				jaxbMarshaller.marshal(n, System.out);
			}
		}catch(JAXBException e){
			e.printStackTrace();
		}
		
	}
	//------------------------------------------------------------------------
	@SuppressWarnings("resource")
	public void serializeTree(){
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("/home/nazi/Desktop/MytreeDeep.ser");
			ObjectOutputStream out;
			out = new ObjectOutputStream(fileOut);
			out.writeObject(this.root);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void deserializeTree(){
		try {
			FileInputStream fileIn = new FileInputStream("/home/nazi/Desktop/MytreeDeep.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while(true){
				try{
					Node<E> n = (Node<E>) in.readObject();
					this.root = n;
				}catch(EOFException e){
					break;
				}
			}	
		} catch (IOException i) {
			i.printStackTrace();

		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();

		}
		
	}
	
}
