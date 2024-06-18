package parser;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class GeneralTree<T> {

	private class GeneralTreeNode {

		public T value;
		public List<GeneralTreeNode> listOfChildren;

		public GeneralTreeNode(T value) {
			this.value = value;
			listOfChildren = new ArrayList<>();
		}

		@Override
		public int hashCode() {
			return Objects.hash(listOfChildren, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			GeneralTreeNode other = (GeneralTreeNode) obj;
			return Objects.equals(value, other.value) && Objects.equals(listOfChildren, other.listOfChildren);
		}

		@Override
		public String toString() {
			return "GeneralTreeNode [value=" + value + ", listOfChildren=" + listOfChildren + "]";
		}

	}

	private GeneralTreeNode rootElementReference;
	private int size;

	public boolean add(T value) {
		if (isEmpty()) {
			rootElementReference = new GeneralTreeNode(value);
		} else {
			rootElementReference.listOfChildren.add(new GeneralTreeNode(value));
		}
		size++;
		return true;
	}

	/**
	 * shton tek femijet e nyjes se perzgjedhur me indeksin e dhene indeksi
	 * perzgjedh njerin nga femijet e rrenjes kryesore
	 **/

	public boolean add(T value, int index) {
		return add(value, new int[] { index });
	}

	/**
	 * shton tek femijet e nyjes e perzgjedhur me indekset e dhene indeksi i pare
	 * perzgjedh njerin nga femijet e rrenjes kryesore
	 **/
	public boolean add(T value, int... index) {
		if (isEmpty() || index == null || index.length == 0) {
			return false;
		}
		GeneralTreeNode node = rootElementReference;
		int nodeIndex = 0;
		while (nodeIndex < index.length && node != null) {
			if (index[nodeIndex] < node.listOfChildren.size()) {
				node = node.listOfChildren.get(index[nodeIndex]);
			} else {
				node = null;
			}
			nodeIndex++;
		}
		if (node != null) {
			node.listOfChildren.add(new GeneralTreeNode(value));
			size++;
			return true;
		}
		return false;
	}

	public boolean add(T value, T valueOfParent) {
		return addAfterOnCondition(value, v -> Objects.equals(v, valueOfParent));
	}

	public boolean addAfterOnCondition(T value, Predicate<T> condition) {
		if (isEmpty() || condition == null) {
			return false;
		}
		Deque<GeneralTreeNode> stackOfNodes = new LinkedList<>();
		stackOfNodes.add(rootElementReference);
		while (!stackOfNodes.isEmpty()) {
			GeneralTreeNode node = stackOfNodes.getFirst();
			stackOfNodes.remove();
			if (condition.test(node.value)) {
				node.listOfChildren.add(new GeneralTreeNode(value));
				size++;
				return true;
			}
			for (int i = 0; i < node.listOfChildren.size(); i++) {
				stackOfNodes.addFirst(node.listOfChildren.get(i));
			}
		}
		return false;
	}


	public boolean contains(T value) {
		if (isEmpty()) {
			return false;
		}
		Deque<GeneralTreeNode> stackOfNodes = new LinkedList<>();
		stackOfNodes.add(rootElementReference);
		while (!stackOfNodes.isEmpty()) {
			GeneralTreeNode node = stackOfNodes.getFirst();
			stackOfNodes.remove();
			if (Objects.equals(value, node.value)) {
				return true;
			}
			for (int i = 0; i < node.listOfChildren.size(); i++) {
				stackOfNodes.add(node.listOfChildren.get(i));
			}
		}
		return false;
	}

	public T get(int index) {
		if (isEmpty() || index < 0 || index >= size) {
			return null;
		}
		Deque<GeneralTreeNode> stackOfNodes = new LinkedList<>();
		stackOfNodes.add(rootElementReference);
		while (!stackOfNodes.isEmpty()) {
			GeneralTreeNode node = stackOfNodes.getFirst();
			stackOfNodes.remove();
			if (index == 0) {
				return node.value;
			}
			index--;
			Deque<GeneralTreeNode> stackOfActualNodes = new LinkedList<>();
			while (!stackOfNodes.isEmpty()) {
				stackOfActualNodes.addFirst(stackOfNodes.getFirst());
				stackOfNodes.remove();
			}
			for (int i = 0; i < node.listOfChildren.size(); i++) {
				stackOfNodes.add(node.listOfChildren.get(i));
			}
			while (!stackOfActualNodes.isEmpty()) {
				stackOfNodes.addFirst(stackOfActualNodes.getFirst());
				stackOfActualNodes.remove();
			}
		}
		return null;
	}

	public T get(int... index) {
		if (index == null || index.length == 0) {
			return null;
		}
		GeneralTreeNode node = rootElementReference;
		int indexOfArray = 0;
		while (indexOfArray < index.length && node != null) {
			if (index[indexOfArray] < node.listOfChildren.size()) {
				node = node.listOfChildren.get(index[indexOfArray]);
			} else {
				node = null;
			}
			indexOfArray++;
		}
		if (node != null) {
			return node.value;
		}
		return null;
	}
	
	public T getOnCondition(Predicate<T> v) {
		if (isEmpty()) {
			return null;
		}
		Deque<GeneralTreeNode> stackOfNodes = new LinkedList<>();
		stackOfNodes.add(rootElementReference);
		while (!stackOfNodes.isEmpty()) {
			GeneralTreeNode node = stackOfNodes.getFirst();
			stackOfNodes.remove();
			if (v.test(node.value)) {
				return node.value;
			}
			Deque<GeneralTreeNode> stackOfActualNodes = new LinkedList<>();
			while (!stackOfNodes.isEmpty()) {
				stackOfActualNodes.addFirst(stackOfNodes.getFirst());
				stackOfNodes.remove();
			}
			for (int i = 0; i < node.listOfChildren.size(); i++) {
				stackOfNodes.add(node.listOfChildren.get(i));
			}
			while (!stackOfActualNodes.isEmpty()) {
				stackOfNodes.addFirst(stackOfActualNodes.getFirst());
				stackOfActualNodes.remove();
			}
		}
		return null;
	}

	public void clear() {
		rootElementReference = null;
		size = 0;
	}

	public boolean isEmpty() {
		return rootElementReference == null;
	}

	public int size() {
		return size;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rootElementReference, size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GeneralTree)) {
			return false;
		}
		GeneralTree<T> other = (GeneralTree<T>) obj;
		return Objects.equals(rootElementReference, other.rootElementReference) && size == other.size;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Deque<GeneralTreeNode> stackOfNodes = new LinkedList<>();
		stackOfNodes.add(rootElementReference);
		while (!stackOfNodes.isEmpty()) {
			GeneralTreeNode node = stackOfNodes.getFirst();
			stackOfNodes.remove();
			sb.append(node.value);
			sb.append(", ");
			Deque<GeneralTreeNode> stackOfActualNodes = new LinkedList<>();
			while (!stackOfNodes.isEmpty()) {
				stackOfActualNodes.addFirst(stackOfNodes.getFirst());
				stackOfNodes.remove();
			}
			for (int i = 0; i < node.listOfChildren.size(); i++) {
				stackOfNodes.add(node.listOfChildren.get(i));
			}
			while (!stackOfActualNodes.isEmpty()) {
				stackOfNodes.addFirst(stackOfActualNodes.getFirst());
				stackOfActualNodes.remove();
			}
		}
		return sb.substring(0, sb.length() - 2).concat("]");
	}

}
