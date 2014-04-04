package com.kuloud.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析常规数学表达式，进行运算。
 * @author 肖中中
 */
public class CalculateString {
	public double calculateString(String str, double x) {
		str = str.replaceAll("PI", String.valueOf(Math.PI))
				.replaceAll("x", String.valueOf(x)).replaceAll("sinh", "i")
				.replaceAll("cosh", "o").replaceAll("tanh", "a")
				.replaceAll("asin", "n").replaceAll("acos", "m")
				.replaceAll("atan", "q").replaceAll("exp", "e")
				.replaceAll("sin", "s").replaceAll("cos", "c")
				.replaceAll("tan", "t");
		char[] strs = str.toCharArray();
		Stack<String> stack = new Stack<String>();
		// 依次提取表达式内部子表达式进行运算；
		for (int i = 0; i < strs.length; i++) {
			String stackStr = String.valueOf(strs[i]);
			stack.push(stackStr);
			if (")".equals(stack.peek())) {
				String subStr = null;
				while (!"(".equals(stack.peek())) {
					stack.pop();
					if (!"(".equals(stack.peek())) {
						subStr = addEnd(subStr, stack.peek());
					}
				}
				String pushStr = CalculateReversePolishExpression(subStr);
				stack.pop();
				stack.push(pushStr);
			}
		}
		String resultStr = null;
		while (stack.count != 0) {
			resultStr = CalculateReversePolishExpression(stack.toString());
		}
		return Double.valueOf(resultStr);
	}

	public String[] matcher(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		String[] result = new String[list.size()];
		return list.toArray(result);
	}

	public List<String> createReversePolishExpression(String subStr) {
		// 正实数的正则表达式；
		String regex = "\\d+\\.{0,1}\\d*";
		// 将匹配的正实数保存在字符串数组numbers中；
		String[] numbers = matcher(regex, subStr);
		String changeStr = subStr.replaceAll(regex, "0")
				.replaceAll("E\\-0", "E1").replaceAll("\\^\\-0", "^1")
				.replaceAll("\\s\\-0", "s1").replaceAll("\\c\\-0", "c1")
				.replaceAll("\\t\\-0", "t1").replaceAll("i\\-0", "i1")
				.replaceAll("o\\-0", "o1").replaceAll("\\a\\-0", "a1")
				.replaceAll("\\e\\-0", "e1").replaceAll("\\n\\-0", "-1")
				.replaceAll("m\\-0", "-1").replaceAll("q\\-0", "-1")
				.replaceAll("\\-\\-0", "-1").replaceAll("\\+\\-0", "+1")
				.replaceAll("\\*\\-0", "*1").replaceAll("\\/\\-0", "/1");
		char[] chars = changeStr.toCharArray();
		int index = 0;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < chars.length; i++) {
			String str = String.valueOf(chars[i]);
			if ("0".equals(str)) {
				list.add(numbers[index++]);
			} else if ("1".equals(str)) {
				list.add("-" + numbers[index++]);
			} else {
				list.add(str);
			}
		}
		List<String> suffix = new ArrayList<String>();
		Stack<String> operator = new Stack<String>();

		for (int i = 0; i < list.size(); i++) {
			if (!isOperatorType(list.get(i))) {
				suffix.add(list.get(i));
			} else {
				if (operator.count == 0) {
					operator.push(list.get(i));
				} else {
					while (operator.count != 0
							&& compare(operator.peek(), list.get(i)) >= 0) {
						String top = operator.pop();
						suffix.add(top);
					}
					operator.push(list.get(i));
				}
			}
		}
		while (operator.count != 0) {
			suffix.add(operator.pop());
		}
		return suffix;
	}

	// 运算逆波兰表达式；
	public String CalculateReversePolishExpression(String subStr) {
		List<String> suffix = createReversePolishExpression(subStr);
		Stack<Double> stack = new Stack<Double>();
		for (int i = 0; i < suffix.size(); i++) {
			if (!isOperatorType(suffix.get(i))) {
				stack.push(Double.valueOf(suffix.get(i)));
			} else if (isOneOperatorType(suffix.get(i))) {
				Double current = stack.pop();
				Double result = calculate(suffix.get(i), current);
				stack.push(result);
			} else {
				Double current = stack.pop();
				Double previous = null;
				if (stack.count != 0) {
					previous = stack.pop();
				} else {
					previous = new Double(0);
				}
				Double result = calculate(suffix.get(i), previous, current);
				stack.push(result);
			}
		}
		return stack.peek().toString();
	}

	public String addEnd(String str, String a) {
		StringBuilder buf = new StringBuilder();
		buf.append(a);
		if (str != null) {
			buf.append(str);
		}
		return buf.toString();
	}

	public boolean isOperatorType(String str) {
		if (isOneOperatorType(str) || isTwoOperatorType(str)) {
			return true;
		}
		return false;
	}

	public boolean isOneOperatorType(String str) {
		if (str.equals("s") || str.equals("c") || str.equals("t")
				|| str.equals("i") || str.equals("o") || str.equals("a")
				|| str.equals("e") || str.equals("n") || str.equals("m")
				|| str.equals("q")) {
			return true;
		}
		return false;
	}

	public boolean isTwoOperatorType(String str) {
		if (str.equals("+") || str.equals("-") || str.equals("*")
				|| str.equals("/") || str.equals("^") || str.equals("E")) {
			return true;
		}
		return false;
	}

	// 比较运算符优先级；
	public int compare(String op1, String op2) {
		Integer iop1 = getOperator(op1);
		Integer iop2 = getOperator(op2);
		return iop1.compareTo(iop2);
	}

	// 解析运算符；
	public Integer getOperator(String op) {
		if ("+".equals(op) || "-".equals(op)) {
			return new Integer(0);
		}
		if ("*".equals(op) || "/".equals(op)) {
			return new Integer(1);
		}
		if ("s".equals(op) || "c".equals(op) || "t".equals(op)
				|| "i".equals(op) || "o".equals(op) || "a".equals(op)
				|| "e".equals(op) || "n".equals(op) || "m".equals(op)
				|| "q".equals(op) || "^".equals(op) || "E".equals(op)) {
			return new Integer(2);
		}

		return null;
	}

	// 二元运算符的与运算；
	public Double calculate(String op, Double previous, Double current) {
		if ("+".equals(op)) {
			return previous + current;
		}
		if ("-".equals(op)) {
			return previous - current;
		}
		if ("*".equals(op)) {
			return previous * current;
		}
		if ("/".equals(op)) {
			return previous / current;
		}
		if ("^".equals(op)) {
			return Math.pow(previous, current);
		}
		if ("E".equals(op)) {
			return previous * Math.pow(10, current);
		}
		return null;
	}

	// 一元运算符的运算；
	private Double calculate(String op, Double current) {
		if ("s".equals(op)) {
			return Math.sin(current);
		}
		if ("c".equals(op)) {
			return Math.cos(current);
		}
		if ("t".equals(op)) {
			return Math.tan(current);
		}
		if ("i".equals(op)) {
			return Math.sinh(current);
		}
		if ("o".equals(op)) {
			return Math.cosh(current);
		}
		if ("a".equals(op)) {
			return Math.tanh(current);
		}
		if ("e".equals(op)) {
			return Math.exp(current);
		}
		if ("n".equals(op)) {
			return Math.asin(current);
		}
		if ("m".equals(op)) {
			return Math.acos(current);
		}
		if ("q".equals(op)) {
			return Math.atan(current);
		}
		return null;
	}
}