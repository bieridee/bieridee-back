package ch.hsr.bieridee.models;

import java.util.ArrayList;
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
 * @author jfurrer
 * 
 */
public class BeerModel {

	private Beer domainBeer;
	private Node nodeBeer;

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
		this.nodeBeer = node;
		final String name = (String) this.nodeBeer.getProperty("name");
		final String brand = (String) this.nodeBeer.getProperty("brand");
		final String picture = (String) this.nodeBeer.getProperty("image");
		final List<Tag> tags = new ArrayList<Tag>();

		for (Relationship r : this.nodeBeer.getRelationships(RelType.HAS_TAG)) {
			final Node nodeTag = r.getEndNode();
			final Tag domainTag = new Tag((String) nodeTag.getProperty("name"));
			tags.add(domainTag);
		}

		final Relationship beertypeRel = this.nodeBeer.getSingleRelationship(RelType.HAS_BEERTYPE, Direction.OUTGOING);
		final Node beertypeNode = beertypeRel.getEndNode();

		final String beertypeName = (String) beertypeNode.getProperty("name");
		final String beertypeDesc = (String) beertypeNode.getProperty("description");
		final Beertype type = new Beertype(beertypeName, beertypeDesc);

		this.domainBeer = new Beer(name, brand, picture, tags, type);
	}

	public Node getNode() {
		return this.nodeBeer;
	}

	public Beer getDomainObject() {
		return this.domainBeer;
	}

	public Beertype getBeertype() {
		return this.domainBeer.getBeertype();
	}

	public String getBrand() {
		return this.domainBeer.getBrand();
	}

	public String getName() {
		return this.domainBeer.getName();
	}

	public String getPicture() {
		return this.domainBeer.getPicture();
	}

	public List<Tag> getTags() {
		return this.domainBeer.getTags();
	}

	public void setBeertype(Beertype beertype) {

	}

	public void setBrand(String brand) {
		this.domainBeer.setBrand(brand);
		this.nodeBeer.setProperty("brand", brand);
	}

	public void setName(String name) {
		this.domainBeer.setName(name);
		this.nodeBeer.setProperty("name", name);

	}

	public void setPicture(String path) {
		this.domainBeer.setPicture(path);
		this.nodeBeer.setProperty("image", path);

	}

	public void setTags(List<Tag> tags) {

	}

}