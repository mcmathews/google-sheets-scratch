package scratch.sheets.google.test.dao;

import com.google.gdata.data.spreadsheet.ListEntry;

import scratch.sheets.google.test.model.Coach;

public class SpreadsheetCoachesImpl extends AbstractSpreadsheetDao<Coach> {

	protected SpreadsheetCoachesImpl() throws SpreadsheetDaoException {
		super();
	}

	@Override
	protected Coach parseResourceFromRow(ListEntry row) {
		Coach coach = new Coach();
		coach.setId(Long.parseLong(row.getCustomElements().getValue("id")));
		coach.setName(row.getCustomElements().getValue("name"));
		
		return coach;
	}

	@Override
	protected ListEntry createRowFromResource(Coach resource) {
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("id", "" + resource.getId());
		row.getCustomElements().setValueLocal("name", resource.getName());
		
		return row;
	}

	@Override
	protected ListEntry updateRowWithResource(ListEntry row, Coach resource) {
		row.getCustomElements().setValueLocal("name", resource.getName());
		
		return row;
	}

	@Override
	protected String getWorksheetName() {
		return "Coaches";
	}
	
}
