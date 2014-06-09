package main;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Node<E> implements Serializable{
	
	private static final Long versionUID = 1L;
	
	private Node<E> left;
	private Node<E> right;
	private Node<E> parent;
	private E value;
	
	
	public Node(E value, Node<E> left, Node<E> right){
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public Node(E value, Node<E> parent){
		this.value = value;
		this.left = null;
		this.right = null;
		this.parent = parent;
	}
	public Node(){}
	public Node<E> getLeft() {
		return left;
	}
	@XmlElement
	public void setLeft(Node<E> left) {
		this.left = left;
	}
	public Node<E> getRight() {
		return right;
	}
	@XmlElement
	public void setRight(Node<E> right) {
		this.right = right;
	}
	public Node<E> getParent() {
		return parent;
	}
	@XmlElement
	public void setParent(Node<E> parent) {
		this.parent = parent;
	}
	public E getValue() {
		return value;
	}
	@XmlElement
	public void setValue(E value) {
		this.value = value;
	}
	

}
