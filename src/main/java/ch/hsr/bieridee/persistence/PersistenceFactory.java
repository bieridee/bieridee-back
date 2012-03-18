package ch.hsr.bieridee.persistence;

public class PersistenceFactory {
	public static IPersistenceProvider getPersistenceProvider() {
		return new OneBeerPersistenceProvider();
	}

}
