package scratch.sheets.google.test.model;

public class Sport extends AbstractResource {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Sport [name=" + name + ", id=" + id + "]";
	}
}
