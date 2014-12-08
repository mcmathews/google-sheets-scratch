package scratch.sheets.google.test.dao;

import com.google.gdata.data.spreadsheet.ListEntry;

import scratch.sheets.google.test.model.Sport;

public class SpreadsheetSportsImpl extends AbstractSpreadsheetDao<Sport> {

	protected SpreadsheetSportsImpl() throws SpreadsheetDaoException {
		super();
	}

	@Override
	protected Sport parseResourceFromRow(ListEntry row) {
		Sport sport = new Sport();
		sport.setId(Long.parseLong(row.getCustomElements().getValue("id")));
		sport.setName(row.getCustomElements().getValue("name"));
		
		return sport;
	}

	@Override
	protected ListEntry createRowFromResource(Sport resource) {
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("id", "" + resource.getId());
		row.getCustomElements().setValueLocal("name", resource.getName());
		
		return row;
	}

	@Override
	protected ListEntry updateRowWithResource(ListEntry row, Sport resource) {
		row.getCustomElements().setValueLocal("name", resource.getName());
		
		return row;
	}

	@Override
	protected String getWorksheetName() {
		return "Sports";
	}
	
}
