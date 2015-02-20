package other;

public class TestCharMapping {
	public static String[] map = new String[26];
	static {
		for (int i = 0; i < map.length; i++) {
			map[i] = String.valueOf((char) ('a' + i));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		root.setCxt("");
		covert(root, 314251);

	}

	public static Node root = new Node();

	public static void covert(Node node, int number) {
		String str = new String(number + "");
		char[] array = str.toCharArray();
		int len = array.length;
		if (len == 1) {
			Node left = new Node();
			left.setCxt(node.getCxt() + map[array[0] - '0']);
			return;
		}

		if (array[0] == '1' || array[0] == '2') {// 这个时候需要特殊处理
			int nextLeftInt = Integer.valueOf(str.substring(1));
			Node left = new Node();
			left.setCxt(node.getCxt() + map[array[0] - '0']);
			node.setLeft(left);
			covert(left, nextLeftInt);

			if (len >= 2) {
				
				if (Integer.valueOf(str.substring(0, 2)) <= 25) {
					Node right = new Node();
					right.setCxt(node.getCxt() + map[array[0] + array[1] - '0' -'0']);
					node.setRight(right);
					if (len == 2) {
						return;
					}
					int nextrightInt = Integer.valueOf(str.substring(2));
					covert(right, nextrightInt);
				}
			}

		} else {
			int nextLeftInt = Integer.valueOf(str.substring(1));
			Node left = new Node();
			left.setCxt(node.getCxt() + map[array[0] - '0']);
			node.setLeft(left);
			covert(left, nextLeftInt);
		}

	}

}

class Node {
	public Node left = null;;
	public Node right = null;
	public String cxt = "";

	public void setCxt(String content) {
		this.cxt = content;

	}

	public boolean isLeaf() {
		return (left == null && right == null);
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String getCxt() {
		return this.cxt;
	}
}