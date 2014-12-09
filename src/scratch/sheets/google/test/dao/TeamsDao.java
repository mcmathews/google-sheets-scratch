package scratch.sheets.google.test.dao;

import java.util.Set;

import scratch.sheets.google.test.model.Coach;
import scratch.sheets.google.test.model.Player;
import scratch.sheets.google.test.model.Sport;
import scratch.sheets.google.test.model.Team;

public interface TeamsDao extends AthleticsDao<Team> {
	public Coach getCoach(long id) throws AthleticsDaoException;
	public Sport getSport(long id) throws AthleticsDaoException;
	public Set<Player> getPlayers(long id) throws AthleticsDaoException;
}
