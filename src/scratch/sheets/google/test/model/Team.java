package scratch.sheets.google.test.model;

public class Team extends AbstractResource {
	
	private String name;
	private long sportId;
	private long coachId;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getSportId() {
		return sportId;
	}
	
	public void setSportId(long sportId) {
		this.sportId = sportId;
	}
	
	public long getCoachId() {
		return coachId;
	}
	
	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", sportId=" + sportId + ", coachId=" + coachId + ", id=" + id + "]";
	}
}
