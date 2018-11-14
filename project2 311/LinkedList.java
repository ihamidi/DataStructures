public class LinkedList<T> {

    private Node head;
    private int size;
    //null constructor
    public LinkedList() {
    }
    //code to add to the end of index
    public void addToEnd(Pair data) {
        addIn(data, size);
    }

    //code to add at any point
    //takes index adn data(node types
    public void addIn(Pair data, int index) {
    	//create new node from data
        Node temp = new Node(data);
        //code to catch OOB
        if (index <0|| index > size) {
            throw new IndexOutOfBoundsException();
            //code to add at beggining
            
        } else if (index == 0) {
        	//setting new head
            temp.setNextNode(head);
            head = temp;
            size++;
        } else 
        {
        	//loops through array until index
            Node current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNextNode();
            }
            // adds node to the list at that index
            temp.setNextNode(current.getNextNode());
            current.setNextNode(temp);
            size++;
        
        }
    }

  //calls method to delete at last index
    public void deleteFromEnd() {
    
    	deleteFromIndex(size - 1);
    
    }
    //code for deleteing a node given index
    public void deleteFromIndex(int index) 
    {
    	//handling IOB
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException();
            //code to delete first node
        } else if (index == 0) 
        {
            head = head.getNextNode();
            size--;
            //code to delete node given index
        } else {
            Node current = head;
            for (int i = 1; i < index; i++) 
            {
                current = current.getNextNode();
            }
            current.setNextNode(current.getNextNode().getNextNode());
            size--;
        }
    }
    //returns first node in list
    public int getFirst() {
        return  returnIndex(0);
    }
    //returns last node in list
    public int getLast() {
        return  returnIndex(size - 1);
    }
    //return value given index
    public int returnIndex(int index) {
        if (index<0||index>=size) {
            
        	//code to handle IOB
        	throw new IndexOutOfBoundsException();
        	//loops through list to find corresponding node
        } else {
            Node current = head;
            
            for (int i = 0; i < index && current != null; i++)
            {
                current = current.getNextNode();
            }
            //returns nodes key
            return current.getData();
        }
    }
    
    //returns address in Pair object
    public long getPosition(int index) {
    	//handling OOB
        if (index<0||index>=size) {
            throw new IndexOutOfBoundsException();
            //loops through list to fnd corresponding node
        } else {
            Node current = head;
            for (int i = 0; i < index && current != null; i++) {
                current = current.getNextNode();
            }
            //returns the node's address
            return current.getAddress();
        }
    }

    //returns size of list
    public int getSize() {
        return size;
    }
    //returns head node
    public Node getHead() {
        return head;
    }
    //inner node class
    public class Node<T> {
    	//data members for node
        private Pair data;
        private Node next;
        
        //node constuctor
        public Node(Pair data) {
            this.data = data;
        }

        //geteer for key in data
        public int getData() {
            return data.key;
        }
        //returns address in Piar
        public long getAddress() {
            return data.address;
        }
        
        //returns the nexyt node
        public Node getNextNode() {
            return next;
        }

        //csetter for node.next
        public void setNextNode(Node next) {
            this.next = next;
        }
    }
}