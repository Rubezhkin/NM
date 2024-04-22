package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * FormulaTree - дерево - формула
 * @param head - начало дерева
 * @param i - переменная для проверки правильности составления примера
 */
public class FormulaTree {
	private Node head;
	private static int i = 0;
	/**
	 * конструктор
	 * @param str строка с примером
	 * @throws Exception при неправильном составлении примера выходит ошибка
	 */
	public FormulaTree(String[] str) throws Exception{
		this.head = buildTree(str);
		if(i != str.length){
			throw new Exception("Неправильно составлен пример!");
		}
	}
	//рекурсивное постоение дерева
	/**
	 * @param str строка с примером
	 * @return построенное дерево
	 */
	public Node buildTree(String[] strArr){
		String str = strArr[i];
		i++;
		Node root = new Node(" ", null, null);
		if(str.matches("-?\\d+(\\.\\d+)?")||str.equals("Pi")||str.equals("e")){
			root.setInfo(str);
		}
		else if(str.equals("sin")||str.equals("cos")||str.equals("tan")){
			root.setInfo(str);
			root.setRight(buildTree(strArr));
		}
		else if(Pattern.compile("[a-zA-Z]+").matcher(str).matches()){
			root.setInfo(str);
		}
		else{
			root.setLeft(buildTree(strArr));
			root.setInfo(strArr[i]);
			i++;
			root.setRight(buildTree(strArr));
			i++;
		}
		return root;
	}

	/**
	 * метод решения примера, которая доступна пользователю
	 * @return результат
	 * @throws Exception при делении на ноль вызывается исключение
	 */
	public double calculate(double x, double y) throws Exception{
		return calculate_rec(head, x, y);
	}

	/**
	 * рекурсивное решение примера приватное
	 * @param tree дерево для подсчета
	 * @return результат подсчета
	 * @throws Exception при делении на ноль вызывается исключение
	 */
	private double calculate_rec(Node tree, double x, double y) throws Exception{
		double result=0;
		if(tree.getLeft() == null && tree.getRight()==null){
			if(tree.getInfo().matches("-?\\d+(\\.\\d+)?"))
				result = Double.parseDouble(tree.getInfo());
			else if (tree.getInfo().equals("Pi"))
				result = Math.PI;
			else if (tree.getInfo().equals("e")) 
				result = Math.E;
			else if(tree.getInfo().equals("x"))
				result = x;
			else if(tree.getInfo().equals("y"))
				result = y;
		}
		else{
			double left = 0;
			if(!tree.getInfo().equals("sin")&&!tree.getInfo().equals("cos")&&!tree.getInfo().equals("tan"))
			 	left = calculate_rec(tree.getLeft(), x, y);
			double right = calculate_rec(tree.getRight(), x, y);
			switch (tree.getInfo()) {
				case "+":
					result = left+right;
					break;
				case "-":
					result = left-right;
					break;
				case "*":
					result = left*right;
					break;
				case "^":
					result = Math.pow(left, right);
					break;
				case "sin":
					result = Math.sin(right);
					break;
				case "cos":
					result = Math.cos(right);
					break;
				case "tan":
					if(Math.toDegrees(right) == 90 || Math.toDegrees(right) == 270)
						throw new Exception("Тангенса не существует");
					else
						result = Math.tan(right);
				case "log":
					if(left == 1 || left <= 0 || right <= 0)
						throw new Exception("Нарушение условий логарифма");
					else
						result = Math.log(right)/Math.log(left);
					break;
				case "/":
					if(right == 0)
						throw new Exception("Деление на 0");
					else
						result = left/right;
					break;
				default:
					break;
			}
		}
		return result;
	}
}
