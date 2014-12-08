package scratch.sheets.google.test.dao;

import com.google.gdata.data.spreadsheet.ListEntry;

import scratch.sheets.google.test.model.Player;

public class SpreadsheetPlayersImpl extends AbstractSpreadsheetDao<Player> {

	protected SpreadsheetPlayersImpl() throws SpreadsheetDaoException {
		super();
	}

	@Override
	protected Player parseResourceFromRow(ListEntry row) {
		Player player = new Player();
		player.setId(Long.parseLong(row.getCustomElements().getValue("id")));
		player.setName(row.getCustomElements().getValue("name"));
		
		return player;
	}

	@Override
	protected ListEntry createRowFromResource(Player resource) {
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("id", "" + resource.getId());
		row.getCustomElements().setValueLocal("name", resource.getName());
		
		return row;
	}

	@Override
	protected ListEntry updateRowWithResource(ListEntry row, Player resource) {
		row.getCustomElements().setValueLocal("name", resource.getName());
		
		return row;
	}

	@Override
	protected String getWorksheetName() {
		return "Players";
	}
	
}
