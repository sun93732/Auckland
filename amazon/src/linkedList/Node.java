package linkedList;

public class Node {

	Node next = null;
	int data;
	public Node(int k)
	{
		this.data = k;
	}
	void appendToTail(int k){
		Node end = new Node(k);
		Node n = this;
		while(n.next != null)
		{
			n = n.next;
		}
		n.next = end;
	}
	Node delteNode(Node head, int d){
		Node n = head;
		if(n.data == d){
			return n.next;
		}
		while(n.next!=null)
		{
			if(n.next.data == d){
			    n.next = n.next.next;
			    return head;
			}
			n = n.next;
		}
		return n;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
