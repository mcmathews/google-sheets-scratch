package scratch.sheets.google.test.dao;

import java.util.Set;

import scratch.sheets.google.test.model.AbstractResource;

public interface AthleticsDao<T extends AbstractResource> {
	public T get(long id) throws AthleticsDaoException;
	public Set<T> getAll() throws AthleticsDaoException;
	public long add(T resource) throws AthleticsDaoException;
	public void update(long id, T resource) throws AthleticsDaoException;
	public void remove(long id) throws AthleticsDaoException;
}
