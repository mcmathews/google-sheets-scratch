package scratch.sheets.google.test.model;

public class Player extends AbstractResource {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", id=" + id + "]";
	}
}
