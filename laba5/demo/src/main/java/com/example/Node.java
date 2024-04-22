package com.example;
/**
 * @param Node - элемент дерева
 * @param info - значение элемента
 * @param right, left - указатели на правый и левый элемент
 */
public class Node {
	private String info;
	private Node left, right;
	
	//конструктор
	public Node(String info, Node left, Node right) {
		this.info = info;
		this.left = left;
		this.right = right;
	}
	//геттеры и сеттеры
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}
	
	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
}
