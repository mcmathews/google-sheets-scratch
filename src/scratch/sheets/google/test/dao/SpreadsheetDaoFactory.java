package scratch.sheets.google.test.dao;

import scratch.sheets.google.test.model.Coach;
import scratch.sheets.google.test.model.Player;
import scratch.sheets.google.test.model.Sport;
import scratch.sheets.google.test.model.Team;

public class SpreadsheetDaoFactory {
	
	public static AthleticsDao<Player> getPlayersDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetPlayersImpl();
	}
	
	public static AthleticsDao<Coach> getCoachesDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetCoachesImpl();
	}
	
	public static AthleticsDao<Sport> getSportsDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetSportsImpl();
	}
	
	public static AthleticsDao<Team> getTeamsDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetTeamsImpl();
	}
}
