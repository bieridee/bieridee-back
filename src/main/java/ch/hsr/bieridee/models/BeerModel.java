package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import ch.hsr.bieridee.config.RelType;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Model to work and persist the beer object.
 * 
 * @author jfurrer, cfaessle
 * 
 */
public class BeerModel extends AbstractModel {

	private Beer domainObject;
	private Node node;

	/**
	 * Creates a BeerModel for the desired beer.
	 * 
	 * @param beerId
	 *            The node id of the desired beer
	 */
	public BeerModel(long beerId) {
		this(DBUtil.getNodeById(beerId));
	}
	
	/**
	 * @param node
	 * Node containing Properties of the Beer.
	 */
	public BeerModel(Node node) {
		this.node = node;
		final String name = (String) this.node.getProperty("name");
		final String brand = (String) this.node.getProperty("brand");
		final String picture = (String) this.node.getProperty("image");
		final List<Tag> tags = new LinkedList<Tag>();

		for (Relationship r : this.node.getRelationships(RelType.HAS_TAG)) {
			final Node nodeTag = r.getEndNode();
			final Tag domainTag = new Tag((String) nodeTag.getProperty("name"));
			tags.add(domainTag);
		}

		final Relationship beertypeRel = this.node.getSingleRelationship(RelType.HAS_BEERTYPE, Direction.OUTGOING);
		final Node beertypeNode = beertypeRel.getEndNode();
		
		final BeertypeModel beertypeModel = new BeertypeModel(beertypeNode);
		
		final Beertype type = beertypeModel.getDomainObject();

		this.domainObject = new Beer(name, brand, picture, tags, type);
	}

	public Node getNode() {
		return this.node;
	}

	public Beer getDomainObject() {
		return this.domainObject;
	}

	public Beertype getBeertype() {
		return this.domainObject.getBeertype();
	}

	public String getBrand() {
		return this.domainObject.getBrand();
	}

	public String getName() {
		return this.domainObject.getName();
	}

	public String getPicture() {
		return this.domainObject.getPicture();
	}

	public List<Tag> getTags() {
		return this.domainObject.getTags();
	}

	public void setBeertype(Beertype beertype) {

	}

	public void setBrand(String brand) {
		this.domainObject.setBrand(brand);
		this.node.setProperty("brand", brand);
	}

	public void setName(String name) {
		this.domainObject.setName(name);
		this.node.setProperty("name", name);

	}

	public void setPicture(String path) {
		this.domainObject.setPicture(path);
		this.node.setProperty("image", path);

	}

	public void setTags(List<Tag> tags) {

	}

}