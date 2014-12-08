package scratch.sheets.google.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scratch.sheets.google.test.dao.AthleticsDao;
import scratch.sheets.google.test.dao.SpreadsheetDaoFactory;
import scratch.sheets.google.test.model.AbstractResource;
import scratch.sheets.google.test.model.Coach;
import scratch.sheets.google.test.model.Player;
import scratch.sheets.google.test.model.Sport;
import scratch.sheets.google.test.model.Team;

public class SheetsReadTestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		
		try {
			AthleticsDao<Player> playerDao = SpreadsheetDaoFactory.getPlayersDaoInstance();
			AthleticsDao<Coach> coachDao = SpreadsheetDaoFactory.getCoachesDaoInstance();
			AthleticsDao<Sport> sportDao = SpreadsheetDaoFactory.getSportsDaoInstance();
			AthleticsDao<Team> teamDao = SpreadsheetDaoFactory.getTeamsDaoInstance();
			
			List<AthleticsDao<? extends AbstractResource>> daos = Arrays.asList(playerDao, coachDao, sportDao, teamDao);
			
			for (AthleticsDao<? extends AbstractResource> dao : daos) {
				for (AbstractResource r : dao.getAll()) {
					out.println(r);
				}
				out.println();
			}
			
		} catch (Throwable t) {
			throw new ServletException(t);
		}
		
	}
}
