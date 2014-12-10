package scratch.sheets.google.test.dao;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

import scratch.sheets.google.test.model.Coach;
import scratch.sheets.google.test.model.Player;
import scratch.sheets.google.test.model.Sport;
import scratch.sheets.google.test.model.Team;

public class SpreadsheetPlayersImpl extends AbstractSpreadsheetDao<Player> implements PlayersDao {
	
	protected SpreadsheetPlayersImpl() throws SpreadsheetDaoException {
		super();
	}
	
	@Override
	public Set<Team> getTeams(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(PLAYERS_TO_TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("player = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Team> teams = new HashSet<>();
			if (rows != null) {
				TeamsDao dao = SpreadsheetDaoFactory.getTeamsDaoInstance();
				for (ListEntry row : rows) {
					long teamId = Long.parseLong(row.getCustomElements().getValue("team"));
					teams.add(dao.get(teamId));
				}
			}
			
			return teams;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	@Override
	public Set<Sport> getSports(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(PLAYERS_TO_TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("player = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Sport> sports = new HashSet<>();
			if (rows != null) {
				TeamsDao dao = SpreadsheetDaoFactory.getTeamsDaoInstance();
				for (ListEntry row : rows) {
					long teamId = Long.parseLong(row.getCustomElements().getValue("team"));
					sports.add(dao.getSport(teamId));
				}
			}
			
			return sports;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	@Override
	public Set<Coach> getCoaches(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(PLAYERS_TO_TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("player = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Coach> coaches = new HashSet<>();
			if (rows != null) {
				TeamsDao dao = SpreadsheetDaoFactory.getTeamsDaoInstance();
				for (ListEntry row : rows) {
					long teamId = Long.parseLong(row.getCustomElements().getValue("team"));
					coaches.add(dao.getCoach(teamId));
				}
			}
			
			return coaches;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	@Override
	protected String getWorksheetName() {
		return PLAYERS_WORKSHEET_TITLE;
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
	
}
