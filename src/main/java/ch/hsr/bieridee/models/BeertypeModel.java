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
public class BeertypeModel extends AbstractModel {

	private Beertype domainObject;
	private Node node;

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
		this.node = n;
		
		final long id = this.node.getId();
		final String name = (String) this.node.getProperty("name");
		final String description = (String) this.node.getProperty("description");
		this.domainObject = new Beertype(id, name, description);
	}

	public Beertype getDomainObject() {
		return this.domainObject;
	}

	public Node getNode() {
		return this.node;
	}

	public String getDescription() {
		return this.domainObject.getDescription();
	}

	public String getName() {
		return this.domainObject.getName();
	}

	public long getId() {
		return this.domainObject.getId();
	}

	//SUPPRESS CHECKSTYLE: setter
	public void setId(long id) {
		this.domainObject.setId(id);
	}

	//SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		this.domainObject.setName(name);
		this.node.setProperty("name", name);
	}

	//SUPPRESS CHECKSTYLE: setter
	public void setDescription(String description) {
		this.domainObject.setDescription(description);
		this.node.setProperty("description", description);
	}

}
