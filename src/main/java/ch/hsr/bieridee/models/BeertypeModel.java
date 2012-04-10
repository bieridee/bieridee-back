package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Model to work and persist the beertype object.
 * 
 * @author jfurrer
 * 
 */
public class BeertypeModel {

	private Beertype domainBeertype;
	private Node nodeBeertype;

	/**
	 * Creates a <code>BeertypeModel</code>.
	 * 
	 * @param beertypeId
	 *            The id of the desired beertype
	 */
	public BeertypeModel(long beertypeId) {
		this(DBUtil.getNodeById(beertypeId));
	}

	/**
	 * Creates a <code>BeertypeModel</code>.
	 * 
	 * @param n
	 *            the node containing <code>Beertype</code> properties.
	 */
	public BeertypeModel(Node n) {
		this.nodeBeertype = n;
		
		final long id = this.nodeBeertype.getId();
		final String name = (String) this.nodeBeertype.getProperty("name");
		final String description = (String) this.nodeBeertype.getProperty("description");
		this.domainBeertype = new Beertype(id, name, description);

	}

	public Beertype getDomainObject() {
		return this.domainBeertype;
	}

	public Node getNode() {
		return this.nodeBeertype;
	}

	public String getDescription() {
		return this.domainBeertype.getDescription();
	}

	public String getName() {
		return this.domainBeertype.getName();
	}

	public long getId() {
		return this.domainBeertype.getId();
	}

	/**
	 * Setter id.
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(long id) {
		this.domainBeertype.setId(id);
	}

	/**
	 * Setter name.
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.domainBeertype.setName(name);
		this.nodeBeertype.setProperty("name", name);
	}

	/**
	 * Setter description.
	 * 
	 * @param description
	 *            The description
	 */
	public void setDescription(String description) {
		this.domainBeertype.setDescription(description);
		this.nodeBeertype.setProperty("description", description);
	}

}
