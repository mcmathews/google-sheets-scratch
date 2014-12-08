package scratch.sheets.google.test.model;

public class Coach extends AbstractResource {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Coach [name=" + name + ", id=" + id + "]";
	}
}
