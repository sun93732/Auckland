package linkedList;
import linkedList.Node;
/**
 * 
 * 12.6 You have two numbers represented by a linked list, where each node contains a single
digit. Write a function that adds the two numbers and returns the sum as a linked list.
EXAMPLE:
input: (3 -> 1 -> 5), (5 -> 9 -> 2)
output: 9 -> 0 -> 7
 * 最简单的方法就是先分别把2个整数求出来，然后加，然后在按位转换成链表
 * */
public class PlusbyNode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node head1 = new Node(3);
		head1.appendToTail(1);
		head1.appendToTail(5);
		
		Node head2 = new Node(5);
		head2.appendToTail(9);
		head2.appendToTail(2);
		head2.appendToTail(5);
		Node x = linkedPlus(head1, head2);
		
        
        
	}
	public static Node plusSimple(Node headx, Node heady){
		int h1 = 0;
		int h2 = 0;
		if(headx!=null){
			h1 = headx.data;
			while(headx.next!=null){
				h1 = 10*h1 + headx.next.data;
				headx = headx.next;
			}
		}
		if(heady!=null){
			h2 = heady.data;
			while(heady.next!=null){
				h2 = 10*h2 + heady.next.data;
				heady = heady.next;
			}
		}
		h1 = h1 + h2;
		int div = 0;
		int mod = 0;
		if(h1 == 0){
			return new Node(0);
		}
		
		mod = h1%10;
		java.util.Stack<Integer> stack = new java.util.Stack<Integer>();
		stack.push(mod);
		div = h1/10;
		while(div != 0){
			h1 = div;
			div =  h1/10;
			mod = h1%10;
			stack.push(mod);
		}
		Node newHead = new Node(stack.pop());
		while(!stack.isEmpty()){
			newHead.appendToTail(stack.pop());
		}
			
		return newHead;
	}
	
	public static Node linkedPlus(Node headx, Node heady){
		Node result = null;
		headx =  reverse(headx);
        heady =  reverse(heady);
        int sum = 0;
        int jin = 0;
        while(headx != null && heady!= null){
        	
        	sum = headx.data + heady.data + jin;
        	if(sum >= 10){
        		jin = 1;
        		sum  =  sum % 10;
        	}
        	else{
        		jin = 0;
        	}
        	if(result == null){
        		result = new Node(sum);
        	}
        	else{
        		result.appendToTail(sum);
        	}
        	headx = headx.next;
        	heady = heady.next;
        }
        while(headx!= null){
        	sum =  headx.data + jin;
        	if(sum >= 10){
        		jin = 1;
        		sum  =  sum % 10;
        	}
        	else{
        		jin = 0;
        	}
        	if(result == null){
        		result = new Node(sum);
        	}
        	else{
        		result.appendToTail(sum);
        	}
        	headx = headx.next;
        }
        while(heady!= null){
        	sum =  heady.data + jin;
        	if(sum >= 10){
        		jin = 1;
        		sum  =  sum % 10;
        	}
        	else{
        		jin = 0;
        	}
        	if(result == null){
        		result = new Node(sum);
        	}
        	else{
        		result.appendToTail(sum);
        	}
        	heady = heady.next;
        }
        if(jin == 1){
        	result.appendToTail(jin);
        }
        result  = reverse(result);
        return result;
	}
	public static Node reverse(Node head){
		if(null == head || null == head.next){
			return head;
		}
		Node reverNode =  reverse(head.next);
		head.next.next = head;
		head.next = null;
		return reverNode;
		
		
	}
	public static Node reverse2(Node head){
		if(head == null){
			return head;
		}
		Node cur = head;
		Node begin = head;
		Node next =  begin.next;
		while(next != null){
			cur = next.next;//xiayige这一步最重要了！！！
			next.next = begin;
			begin = next;
			next = cur;
			
		}
		head.next = null;
		head = begin;
		return head;
	}

}
