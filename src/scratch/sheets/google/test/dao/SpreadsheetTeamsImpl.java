package scratch.sheets.google.test.dao;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import scratch.sheets.google.test.model.Coach;
import scratch.sheets.google.test.model.Player;
import scratch.sheets.google.test.model.Sport;
import scratch.sheets.google.test.model.Team;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

public class SpreadsheetTeamsImpl extends AbstractSpreadsheetDao<Team> implements TeamsDao {

	protected SpreadsheetTeamsImpl() throws SpreadsheetDaoException {
		super();
	}
	
	@Override
	public Coach getCoach(long id) throws AthleticsDaoException {
		Team team = get(id);
		if (team != null) {
			CoachesDao dao = SpreadsheetDaoFactory.getCoachesDaoInstance();
			return dao.get(id);
		} else {
			throw new AthleticsDaoException("Team with id [" + id + "] does not exist");
		}
	}

	@Override
	public Sport getSport(long id) throws AthleticsDaoException {
		Team team = get(id);
		if (team != null) {
			SportsDao dao = SpreadsheetDaoFactory.getSportsDaoInstance();
			return dao.get(id);
		} else {
			throw new AthleticsDaoException("Team with id [" + id + "] does not exist");
		}
	}

	@Override
	public Set<Player> getPlayers(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet("PlayersToTeams");
		
		try {
			String query = URLEncoder.encode("team = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Player> players = new HashSet<>();
			if (rows != null) {
				PlayersDao dao = SpreadsheetDaoFactory.getPlayersDaoInstance();
				for (ListEntry row : rows) {
					long playerId = Long.parseLong(row.getCustomElements().getValue("player"));
					players.add(dao.get(playerId));
				}
			}
			
			return players;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
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
