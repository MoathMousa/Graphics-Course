package secondproject;

public class LinkList {
	static Node first, last;
        static   int count=0;
	
	public Node getFirst() {
		return first;
	}

	public static void addFirst(Character x) {

		if (first == null)
			first = last = new Node(x);
		else {
			Node z = new Node(x);
			z.next = first;
			first = z;
		}
                count++;

	}
	
	public static void addLast(Character x) {
		if (first == null)
			first = last = new Node(x);
		else {
			Node z = new Node(x);
			last.next = z;
			last = z;
		}
                count++;

	}
	
        public void toSstring(){
            System.out.println(first.element);
        }
}
