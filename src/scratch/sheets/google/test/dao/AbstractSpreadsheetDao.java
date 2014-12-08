package scratch.sheets.google.test.dao;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import scratch.sheets.google.test.OAuthUtil;
import scratch.sheets.google.test.model.AbstractResource;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

public abstract class AbstractSpreadsheetDao<T extends AbstractResource> implements AthleticsDao<T> {
	
	private static final String SPREADSHEET_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	private static final String SPREADSHEET_TITLE = "TestData";
	
	private SpreadsheetService spreadsheetService;
	
	protected AbstractSpreadsheetDao() throws SpreadsheetDaoException {
		try {
			spreadsheetService = new SpreadsheetService("Test");
			spreadsheetService.setOAuth2Credentials(OAuthUtil.authorize());
		} catch (Throwable t) {
			throw new SpreadsheetDaoException(t);
		}
	}

	@Override
	public T get(long id) throws AthleticsDaoException {
		try {
			WorksheetEntry worksheet = getWorksheet(getWorksheetName());
			ListEntry row = getRowById(worksheet, id);
			
			T resource = null;
			if (row != null) {
				resource = parseResourceFromRow(row);
			}
			
			return resource;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}

	@Override
	public List<T> getAll() throws AthleticsDaoException {
		try {
			WorksheetEntry worksheet = getWorksheet(getWorksheetName());
			List<ListEntry> rows = spreadsheetService.getFeed(worksheet.getListFeedUrl(), ListFeed.class).getEntries();
			List<T> resources = new ArrayList<>();
			for (ListEntry row : rows) {
				resources.add(parseResourceFromRow(row));
			}
			
			return resources;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}

	@Override
	public long add(T resource) throws AthleticsDaoException {
		try {
			WorksheetEntry worksheet = getWorksheet(getWorksheetName());
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?reverse=true&orderby=column:id").toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			long id = 1;
			if (rows != null && !rows.isEmpty()) {
				id = Long.parseLong(rows.get(0).getTitle().getPlainText()) + 1;
			}
			
			resource.setId(id);
			ListEntry newRow = createRowFromResource(resource);
			spreadsheetService.insert(listFeedUrl, newRow);
			
			return id;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}

	@Override
	public void update(long id, T resource) throws AthleticsDaoException {
		try {
			WorksheetEntry worksheet = getWorksheet(getWorksheetName());
			ListEntry row = getRowById(worksheet, id);
			
			if (row != null) {
				row = updateRowWithResource(row, resource);
				row.update();
				
			} else {
				throw new IllegalArgumentException("Resource with id [" + id + "] does not exist");
			}
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}

	@Override
	public void remove(long id) throws AthleticsDaoException {
		try {
			WorksheetEntry worksheet = getWorksheet(getWorksheetName());
			ListEntry row = getRowById(worksheet, id);
			
			// Delete the row with the given id if it exists
			if (row != null) {
				row.delete();
			}
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	protected SpreadsheetEntry getSpreadsheet() throws SpreadsheetDaoException {
		try {
			SpreadsheetFeed spreadsheetFeed = spreadsheetService.getFeed(new URL(SPREADSHEET_FEED_URL), SpreadsheetFeed.class);
			for (SpreadsheetEntry spreadsheet : spreadsheetFeed.getEntries()) {
				if (spreadsheet.getTitle().getPlainText().equals(SPREADSHEET_TITLE)) {
					return spreadsheet;
				}
			}
			
			return null;
			
		} catch (Throwable t) {
			throw new SpreadsheetDaoException(t);
		}
	}
	
	protected WorksheetEntry getWorksheet(String name) throws SpreadsheetDaoException {
		try {
			WorksheetFeed worksheetFeed = spreadsheetService.getFeed(getSpreadsheet().getWorksheetFeedUrl(), WorksheetFeed.class);
			for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
				if (worksheet.getTitle().getPlainText().equals(name)) {
					return worksheet;
				}
			}
			
			return null;
			
		} catch (Throwable t) {
			throw new SpreadsheetDaoException(t);
		}
	}
	
	protected ListEntry getRowById(WorksheetEntry worksheet, long id) throws AthleticsDaoException {
		try {
			String query = URLEncoder.encode("id = " + id, "UTF-8");
			URL listFeedUrl = new URI(worksheet.getListFeedUrl().toString() + "?sq=" + query).toURL();
			List<ListEntry> rows = spreadsheetService.getFeed(listFeedUrl, ListFeed.class).getEntries();
			
			ListEntry row = null;
			if (rows != null && !rows.isEmpty()) {
				row = rows.get(0);
			}
			
			return row;
			
		} catch (Throwable t) {
			throw new AthleticsDaoException(t);
		}
	}
	
	protected abstract T parseResourceFromRow(ListEntry row);
	
	protected abstract ListEntry createRowFromResource(T resource);
	
	protected abstract ListEntry updateRowWithResource(ListEntry row, T resource);
	
	protected abstract String getWorksheetName();
	
	//protected abstract Class<T> getType();
}
