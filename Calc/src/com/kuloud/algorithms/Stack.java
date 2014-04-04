package com.kuloud.algorithms;

public class Stack<T> {
	public StackNode<T> stackTop;
	public int count;

	public void push(T info) {
		StackNode<T> node = new StackNode<T>();
		node.info = info;
		node.link = stackTop;
		stackTop = node;
		count++;
	}

	public T pop() {
		if (stackTop == null) {
			return null;
		}
		T temp = stackTop.info;
		stackTop = stackTop.link;
		count--;
		return temp;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public T peek() {
		if (stackTop == null) {
			return null;
		}
		return stackTop.info;
	}

	@Override
	public String toString() {
		Stack<T> other = new Stack<T>();
		while (count != 0) {
			T top = pop();
			other.push(top);
		}
		StringBuilder buf = new StringBuilder();
		while (other.count != 0) {
			buf.append(other.pop());
		}
		return buf.toString();
	}
}
