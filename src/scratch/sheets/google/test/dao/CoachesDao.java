package scratch.sheets.google.test.dao;

import java.util.Set;

import scratch.sheets.google.test.model.Coach;
import scratch.sheets.google.test.model.Player;
import scratch.sheets.google.test.model.Sport;
import scratch.sheets.google.test.model.Team;

public interface CoachesDao extends AthleticsDao<Coach> {
	public Set<Team> getTeams(long id) throws AthleticsDaoException;
	public Set<Sport> getSports(long id) throws AthleticsDaoException;
	public Set<Player> getPlayers(long id) throws AthleticsDaoException;
}
