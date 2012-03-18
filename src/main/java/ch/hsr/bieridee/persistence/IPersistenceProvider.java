package ch.hsr.bieridee.persistence;

import ch.hsr.bieridee.domain.Beer;

public interface IPersistenceProvider {
	public Beer getBierById(int id);
	public void saveBier(Beer b);
	public Beer getBierByName(String name);

}
