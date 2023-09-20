class DynamicStackInt extends DynamicStack {
	private int top;
	private int capacity;
	private int[] array;

	public DynamicStackInt(int cap) {
		capacity = cap;
		array = new int[capacity];
		top = -1;
	}

	public void push(int data) {
		if (isFull()) {
			expandArray(); // if array is full then increase its capacity
		}
		array[++top] = data; // insert the data
	}

	public void expandArray() {
		int curr_size = top + 1;
		int[] new_array = new int[curr_size * 2];
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

	public int pop() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return -1;
		} else {
			reduceSize(); // function to check if size can be reduced
			return array[top--];
		}
	}

	public void reduceSize() {
		int curr_length = top + 1;
		if (curr_length < capacity / 2) {
			int[] new_array = new int[capacity / 2];
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

	public int getNum(int index) {
		int num = array[index];
		for (int i = index; i <= top - 1; i++) {
			array[index] = array[index + 1];
		}
		top--;
		reduceSize();
		return num;
	}

	public int[] getArray() {
		int[] new_array = new int[top + 1];
		for (int i = 0; i <= top; i++) {
			new_array[i] = array[i];
		}
		return new_array;
	}
}