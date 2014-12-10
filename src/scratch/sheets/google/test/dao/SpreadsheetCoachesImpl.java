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

public class SpreadsheetCoachesImpl extends AbstractSpreadsheetDao<Coach> implements CoachesDao {
	
	protected SpreadsheetCoachesImpl() throws SpreadsheetDaoException {
		super();
	}
	
	@Override
	public Set<Team> getTeams(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("coach = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Team> teams = new HashSet<>();
			if (rows != null) {
				SpreadsheetTeamsImpl dao = new SpreadsheetTeamsImpl();
				for (ListEntry row : rows) {
					teams.add(dao.parseResourceFromRow(row));
				}
			}
			
			return teams;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	@Override
	public Set<Sport> getSports(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("coach = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Sport> sports = new HashSet<>();
			if (rows != null) {
				SportsDao dao = SpreadsheetDaoFactory.getSportsDaoInstance();
				for (ListEntry row : rows) {
					long sportId = Long.parseLong(row.getCustomElements().getValue("sport"));
					sports.add(dao.get(sportId));
				}
			}
			
			return sports;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	@Override
	public Set<Player> getPlayers(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("coach = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();

			Set<Player> players = new HashSet<>();
			if (rows != null) {
				TeamsDao dao = SpreadsheetDaoFactory.getTeamsDaoInstance();
				for (ListEntry row : rows) {
					long teamId = Long.parseLong(row.getTitle().getPlainText());
					players.addAll(dao.getPlayers(teamId));
				}
			}
			
			return players;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
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
		return COACHES_WORKSHEET_TITLE;
	}
	
}
