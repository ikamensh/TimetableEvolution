package Classes_resource;

public class Room extends Resource {

	public int size;

	public Room(int size) {

		this.size = size;
	}

	public Room(int size, String name) {

		this.size = size;
		this.name = name;

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
