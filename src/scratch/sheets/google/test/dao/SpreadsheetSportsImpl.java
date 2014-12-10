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

public class SpreadsheetSportsImpl extends AbstractSpreadsheetDao<Sport> implements SportsDao {

	protected SpreadsheetSportsImpl() throws SpreadsheetDaoException {
		super();
	}

	@Override
	public Set<Team> getTeams(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("sport = " + id, "UTF-8");
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
	public Set<Coach> getCoaches(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("sport = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			Set<Coach> coaches = new HashSet<>();
			if (rows != null) {
				CoachesDao dao = SpreadsheetDaoFactory.getCoachesDaoInstance();
				for (ListEntry row : rows) {
					long coachId = Long.parseLong(row.getCustomElements().getValue("coach"));
					coaches.add(dao.get(coachId));
				}
			}
			
			return coaches;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}

	@Override
	public Set<Player> getPlayers(long id) throws AthleticsDaoException {
		WorksheetEntry worksheet = getWorksheet(TEAMS_WORKSHEET_TITLE);
		
		try {
			String query = URLEncoder.encode("sport = " + id, "UTF-8");
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
		return SPORTS_WORKSHEET_TITLE;
	}

}
