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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (coachId ^ (coachId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (sportId ^ (sportId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Team))
			return false;
		Team other = (Team) obj;
		if (coachId != other.coachId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sportId != other.sportId)
			return false;
		return true;
	}
}
