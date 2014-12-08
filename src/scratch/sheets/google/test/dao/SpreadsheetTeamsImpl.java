package scratch.sheets.google.test.dao;

import com.google.gdata.data.spreadsheet.ListEntry;

import scratch.sheets.google.test.model.Team;

public class SpreadsheetTeamsImpl extends AbstractSpreadsheetDao<Team> {

	protected SpreadsheetTeamsImpl() throws SpreadsheetDaoException {
		super();
	}

	@Override
	protected Team parseResourceFromRow(ListEntry row) {
		Team team = new Team();
		team.setId(Long.parseLong(row.getCustomElements().getValue("id")));
		team.setName(row.getCustomElements().getValue("name"));
		team.setSportId(Long.parseLong(row.getCustomElements().getValue("sport")));
		team.setCoachId(Long.parseLong(row.getCustomElements().getValue("coach")));
		
		return team;
	}

	@Override
	protected ListEntry createRowFromResource(Team resource) {
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("id", "" + resource.getId());
		row.getCustomElements().setValueLocal("name", resource.getName());
		row.getCustomElements().setValueLocal("sport", "" + resource.getSportId());
		row.getCustomElements().setValueLocal("coach", "" + resource.getCoachId());
		
		return row;
	}

	@Override
	protected ListEntry updateRowWithResource(ListEntry row, Team resource) {
		row.getCustomElements().setValueLocal("name", resource.getName());
		row.getCustomElements().setValueLocal("sport", "" + resource.getSportId());
		row.getCustomElements().setValueLocal("coach", "" + resource.getCoachId());
		
		return row;
	}

	@Override
	protected String getWorksheetName() {
		return "Teams";
	}
	
}
