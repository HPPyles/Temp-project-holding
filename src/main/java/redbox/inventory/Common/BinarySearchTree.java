package redbox.inventory.Common;

import java.util.ArrayList;
import java.util.Stack;

/*
Binary Search Tree Class
	Can insert data points into the tree and check if a data point is in the tree
Hosanna Pyles
hpp220001
*/

public class BinarySearchTree<T extends Comparable<T>> {
	
	private class Node {
		T data;
		Node left, right;
		
		Node(T data) {
			this.data = data;
			left = right = null;
		}
	}
	
	private Node root;
	private int length = 0;

	// Constructor
	public BinarySearchTree() {
		root = null;
	}

	// Get recorded number of nodes in tree
	public int getLength() {
		return length;
	}

	// Inserting nodes
	public void insert(T data) {
		root = insertRec(root, data);
		length++; // Update length
	}
	
	private Node insertRec(Node root, T data) {
		if (root == null) { //If empty, make root node
			root = new Node(data);
			return root;
		}
		
		if (data.compareTo(root.data) < 0) { // If inserted data is smaller than root
			root.left = insertRec(root.left,data); // Call recursive function, place it to the left of compared node until no longer less than compared node
		}
		else if (data.compareTo(root.data) > 0) { // If inserted data is greater than root
			root.right = insertRec(root.right,data); // Call recursive function, place it to the right of compared node until no longer more than compared node
		}
		
		return root;
	}

	// Deleting nodes
    public void delete(T data) {
        root = deleteRec(root, data);
		if (root != null) {
			length--; // update length
		}
    }

    private Node deleteRec(Node root, T data) {
        if (root == null) { // If there's no nodes it wont work
            return root;
        }

        if (data.compareTo(root.data) < 0) { // If data to delete is smaller than root
			root.left = deleteRec(root.left, data);
        }
        else if (data.compareTo(root.data) > 0) { // If data to delete is greater than root
			root.right = deleteRec(root.right, data);
        }
        else {
            if (root.left == null) {
                return root.right;
            }
            else if (root.right == null) {
                return root.left;
            }

            // Get minimum data value and put in root
            root.data = minValue(root.right);

            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

	T minValue(Node root) {
		T minimum = root.data; 
        while (root.left != null) {
            minimum = root.left.data;
            root = root.left;
        }
		return minimum;
	}
	
	// Search for data in specific node
	public boolean search(T data) {
		return searchRec(root, data);
	}
	
	private boolean searchRec(Node root, T data) {
		if (root == null) { // If empty/reached end of tree nodes return data not found
			return false;
		}
		else if (data.compareTo(root.data) < 0) { // If the data you're looking for is less than root
			return searchRec(root.left,data); // Call recursive function, compare to left node until node found or reached end
		}
		else if (data.compareTo(root.data) > 0) { // If the data you're looking for is greater than root
			return searchRec(root.right,data); // Call recursive function, compare to right node until node found or reached end
		}
		
		return true;
	}

	// Return node object using search format
	// !!! MIGHT NOT BE NEEDED
	// public T getData(T data) {
	// 	root = getDataRec(root, data);
	// 	return root.data;
	// }

	// private Node getDataRec(Node root, T data) {
		
	// 	if (root == null) { // If empty/reached end of tree nodes return data not found
	// 		return null;
	// 	}
	// 	else if (data.compareTo(root.data) < 0) { // If the data you're looking for is less than root
	// 		return getDataRec(root.left,data); // Call recursive function, compare to left node until node found or reached end
	// 	}
	// 	else if (data.compareTo(root.data) > 0) { // If the data you're looking for is greater than root
	// 		return getDataRec(root.right,data); // Call recursive function, compare to right node until node found or reached end
	// 	}
			
	// 	return root; 
	// }

	// Edit node data
	// Rather than directly editing contents of the movie objects, movie object should be edited externally and then overwrite the original node 
	public void update(T oldData, T newData) {
		root = updateRec(root, oldData, newData);
	}

	private Node updateRec(Node root, T oldData, T newData) {
		
		deleteRec(root, oldData);

		insertRec(root, newData);

		return root;
	}

	// Need to be able to grab all the data points and return them in order
	// Return as list of objects
	public ArrayList<T> inOrderTraversal() {
		ArrayList<T> sorted = inOrderRec(root);
		return sorted;
	}
	
	private ArrayList<T> inOrderRec(Node root) {
		ArrayList<T> organize = new ArrayList<>();
		Stack<Node> s = new Stack<>();
		Node curr = root;

		while (curr != null || !s.isEmpty()) {

            // Reach the left most Node of the curr Node
            while (curr != null) {

                // pointer to tree node on stack before traversing the node's left subtree
                s.push(curr);
                curr = curr.left;
            }

            // If/when current is null
            curr = s.pop();
            organize.add(curr.data);
			
            // Visit right subtree
            curr = curr.right;
        }

        return organize;
	}
}