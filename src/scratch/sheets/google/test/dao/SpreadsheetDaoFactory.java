package scratch.sheets.google.test.dao;

public class SpreadsheetDaoFactory {
	
	public static PlayersDao getPlayersDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetPlayersImpl();
	}
	
	public static CoachesDao getCoachesDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetCoachesImpl();
	}
	
	public static SportsDao getSportsDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetSportsImpl();
	}
	
	public static TeamsDao getTeamsDaoInstance() throws AthleticsDaoException {
		return new SpreadsheetTeamsImpl();
	}
}
