class DynamicStackBetterShape extends DynamicStack {
	private int top;
	private int capacity;
	private BetterShape[] array;

	public DynamicStackBetterShape(int cap) {
		capacity = cap;
		array = new BetterShape[capacity];
		top = -1;
	}

	public void push(BetterShape data) {
		if (isFull()) {
			expandArray(); // if array is full then increase its capacity
		}
		array[++top] = data; // insert the data
	}

	public void expandArray() {
		int curr_size = top + 1;
		BetterShape[] new_array = new BetterShape[curr_size * 2];
		for (int i = 0; i < curr_size; i++) {
			new_array[i] = array[i];
		}
		array = new_array; // refer to the new array
		capacity = new_array.length;
	}

	public int getSize() {
		return top + 1;
	}

	public boolean isFull() {
		if (capacity == top + 1)
			return true;
		else
			return false;
	}

	public BetterShape pop() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		} else {
			reduceSize(); // function to check if size can be reduced
			return array[top--];
		}
	}

	public void reduceSize() {
		int curr_length = top + 1;
		if (curr_length < capacity / 2) {
			BetterShape[] new_array = new BetterShape[capacity / 2];
			System.arraycopy(array, 0, new_array, 0, new_array.length);
			array = new_array;
			capacity = new_array.length;
		}
	}

	public boolean isEmpty() {
		if (top == -1)
			return true;
		else
			return false;
	}

	public void display() {
		for (int i = 0; i <= top; i++) {
			System.out.print(array[i] + "=>");
		}
		System.out.println();
		System.out.println("ARRAY SIZE:" + array.length);
	}

	public BetterShape[] getArray() {
		BetterShape[] new_array = new BetterShape[top + 1];
		for (int i = 0; i <= top; i++) {
			new_array[i] = array[i];
		}
		return new_array;
	}

	public int returnIndex(BetterShape bs) {
		for (int i = 0; i <= top; i++) {
			if (bs.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}

	public BetterShape popBetterShapeI(int index) {
		BetterShape bs = array[index];
		for (int i = index; i <= top - 1; i++) {
			array[i] = array[i + 1];
		}
		top--;
		reduceSize();
		return bs;
	}

	public void pushIndex(BetterShape bs, int index) {
		if (isFull()) {
			expandArray(); // if array is full then increase its capacity
		}
		top++;
		for (int i = top; i > index; i--) {
			array[i] = array[i - 1];
		}
		array[index] = bs;
	}

}