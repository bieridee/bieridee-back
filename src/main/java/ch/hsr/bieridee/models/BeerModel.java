package ch.hsr.bieridee.models;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.RelType;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.domain.Brewery;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.Cypher;
import ch.hsr.bieridee.utils.Cypherqueries;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work and persist the beer object.
 */
public class BeerModel extends AbstractModel {

	private Beer domainObject;
	private double averageRating;

	/**
	 * Creates a BeerModel for the desired beer.
	 * 
	 * @param beerId
	 *            The node id of the desired beer
	 * @throws WrongNodeTypeException
	 *             Thrown when the given id does not reference a beer node
	 * @throws NotFoundException
	 *             Thrown when the given ide does not reference an existing node
	 */
	public BeerModel(long beerId) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getNodeById(beerId));
	}

	/**
	 * @param node
	 *            Node containing Properties of the Beer.
	 * @throws WrongNodeTypeException
	 *             Thrown when the given node is not a beer node
	 * @throws NotFoundException
	 *             Thrown when the given node is not existing
	 */
	public BeerModel(Node node) throws WrongNodeTypeException, NotFoundException {

		NodeUtil.checkNode(node, NodeType.BEER);

		this.node = node;
		final long id = node.getId();
		final String name = (String) this.node.getProperty(NodeProperty.Beer.NAME);
		final String brand = (String) this.node.getProperty(NodeProperty.Beer.BRAND);
		final String image = (String) this.node.getProperty(NodeProperty.Beer.IMAGE);
		this.averageRating = (Double) this.node.getProperty(NodeProperty.Beer.AVERAGE_RATING, 0.0);
		final List<Tag> tags = new LinkedList<Tag>();

		for (Relationship r : this.node.getRelationships(RelType.HAS_TAG)) {
			final Node nodeTag = r.getEndNode();
			final Tag domainTag = new Tag(nodeTag.getId(), (String) nodeTag.getProperty(NodeProperty.Beer.NAME));
			tags.add(domainTag);
		}

		final Relationship beertypeRel = this.node.getSingleRelationship(RelType.HAS_BEERTYPE, Direction.OUTGOING);
		final Node beertypeNode = beertypeRel.getEndNode();
		final BeertypeModel beertypeModel = new BeertypeModel(beertypeNode);
		final Beertype type = beertypeModel.getDomainObject();

		final Relationship breweryRel = this.node.getSingleRelationship(RelType.BREWN_BY, Direction.OUTGOING);
		final Node breweryNode = breweryRel.getEndNode();
		final BreweryModel breweryModel = new BreweryModel(breweryNode);
		final Brewery brewery = breweryModel.getDomainObject();

		this.domainObject = new Beer(id, name, brand, image, tags, type, brewery);
	}

	private BeerModel(String name, String brand, BeertypeModel beertypeModel, BreweryModel breweryModel) {
		this.node = DBUtil.createNode(NodeType.BEER);
		this.domainObject = new Beer(this.node.getId(), name, brand, StringUtils.EMPTY, new LinkedList<Tag>(), beertypeModel.getDomainObject(), breweryModel.getDomainObject());
		this.setName(name);
		this.setBrand(brand);
		this.setImage(StringUtils.EMPTY);
		this.setBeertype(beertypeModel);
		this.setBrewery(breweryModel);
	}

	public Node getNode() {
		return this.node;
	}

	public Beer getDomainObject() {
		return this.domainObject;
	}

	public long getId() {
		return this.domainObject.getId();
	}

	/**
	 * calculates the average rating and saves the value in the database.
	 */
	public void calculateAndUpdateAverageRating() {
		final Double averageRating = Cypher.executeAndGetDouble(Cypherqueries.GET_AVERAGE_RATING_OF_BEER, "AverageRating", this.getId() + "", Long.toString(this.node.getId()));
		DBUtil.setProperty(this.getNode(), NodeProperty.Beer.AVERAGE_RATING, averageRating);
		this.averageRating = averageRating;
	}

	// SUPPRESS CHECKSTYLE: getter
	public BeertypeModel getBeertype() throws NotFoundException, WrongNodeTypeException {
		return new BeertypeModel(this.domainObject.getBeertype().getId());
	}

	public String getBrand() {
		return this.domainObject.getBrand();
	}

	// SUPPRESS CHECKSTYLE: getter
	public BreweryModel getBrewery() throws NotFoundException, WrongNodeTypeException {
		return new BreweryModel(this.domainObject.getBrewery().getId());
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

	// SUPPRESS CHECKSTYLE: getter
	public List<TagModel> getTagModels() throws NotFoundException, WrongNodeTypeException {
		final List<TagModel> tagModels = new LinkedList<TagModel>();
		for (Tag tag : this.getTags()) {
			tagModels.add(new TagModel(tag.getId()));
		}
		return tagModels;
	}

	public double getAverageRating() {
		return this.averageRating;
	}

	/**
	 * @return double value containing only one decimal place.
	 */
	public double getAverageRatingShortened() {
		final DecimalFormat df = new DecimalFormat("0.0");
		final String doubleString = df.format(this.getAverageRating());
		return new Double(doubleString);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setBeertype(BeertypeModel beertypeModel) {
		this.domainObject.setBeertype(beertypeModel.getDomainObject());
		DBUtil.deleteRelationship(this.node, RelType.HAS_BEERTYPE, beertypeModel.getNode(), Direction.OUTGOING);
		DBUtil.createRelationship(this.node, RelType.HAS_BEERTYPE, beertypeModel.getNode());
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setBrand(String brand) {
		this.domainObject.setBrand(brand);
		DBUtil.setProperty(this.node, NodeProperty.Beer.BRAND, brand);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setBrewery(BreweryModel breweryModel) {
		this.domainObject.setBrewery(breweryModel.getDomainObject());
		DBUtil.deleteRelationship(this.node, RelType.BREWN_BY, breweryModel.getNode(), Direction.OUTGOING);
		DBUtil.createRelationship(this.node, RelType.BREWN_BY, breweryModel.getNode());
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		this.domainObject.setName(name);
		DBUtil.setProperty(this.node, NodeProperty.Beer.NAME, name);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setImage(String path) {
		this.domainObject.setPicture(path);
		DBUtil.setProperty(this.node, NodeProperty.Beer.IMAGE, path);
	}

	/**
	 * Links this Beer to the desired TagModel.
	 * 
	 * @param t
	 *            TagModel to add.
	 */
	public void addTag(TagModel t) {
		final Iterable<Relationship> existingTagRelations = this.node.getRelationships(RelType.HAS_TAG);
		for (Relationship relationship : existingTagRelations) {
			if (relationship.getEndNode().getProperty(NodeProperty.Tag.NAME).equals(t.getName())) {
				return;
			}
		}
		DBUtil.createRelationship(this.node, RelType.HAS_TAG, t.getNode());
	}

	/**
	 * Add barcode to BeerModel.
	 * @param b BarcodeModel
	 */
	public void addBarcode(BarcodeModel b) {
		// Get or create a new barcode node
		final Node barcodeNode = DBUtil.getOrCreateBarcodeNode(b.getCode(), b.getFormat());

		// If the node already has a relationship, it already existed before.
		if (barcodeNode.hasRelationship(RelType.HAS_BARCODE)) {

			// If the beer connected to the node is the current beer,
			// everything is OK.
			final Node beerNode = barcodeNode.getRelationships(RelType.HAS_BARCODE, Direction.INCOMING).iterator().next().getEndNode();

			// Otherwise, it means that the barcode already exists and is connected
			// to another node. That shouldn't happen, because barcodes are unique.
			if (beerNode != this.node) {
				throw new RuntimeException("Barcode already exists for other Beer.");
			}

		// If the node doesn't have a relationship yet, it means that it was just created.
		// In that case, create a relationship with the current beer.
		} else {
			DBUtil.createRelationship(this.node, RelType.HAS_BARCODE, barcodeNode);
		}
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setTags(Iterable<TagModel> tags) {
		final List<Tag> tagDomainList = new LinkedList<Tag>();
		for (TagModel tagModel : tags) {
			tagDomainList.add(tagModel.getDomainObject());
		}
		this.domainObject.setTags(tagDomainList);

		for (TagModel t : tags) {
			this.addTag(t);
		}
	}

	@Override
	public int hashCode() {
		return this.node.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BeerModel)) {
			return false;
		}
		return this.node.getId() == ((BeerModel) o).getId();
	}

	/**
	 * Create a new Beer! Including a fresh node and a delicious domain object.
	 * 
	 * @param name
	 *            Name of the new beer
	 * @param brand
	 *            Brand of the new beer
	 * @param beertypeModel
	 *            The beertype of the new beer
	 * @param breweryModel
	 *            The the sacred place where this beer was brewn
	 * @return A new BeerModel representing the new beer
	 */
	public static BeerModel create(String name, String brand, BeertypeModel beertypeModel, BreweryModel breweryModel) {
		return new BeerModel(name, brand, beertypeModel, breweryModel);
	}

	/**
	 * Gets a list of all beers as <code>BeerModel</code>s.
	 * 
	 * @return List of <code>BeerModel</code>
	 * @throws NotFoundException
	 *             Thrown if a node is not existant.
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<BeerModel> getAll() throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getBeerNodeList());
	}

	/**
	 * Gets a list of beers as <code>BeerModel</code>s filtered by a tag.
	 * 
	 * @param filterTag
	 *            Tag to be filterd with
	 * @return Filtered list of BeerModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existant
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<BeerModel> getAll(long filterTag) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getBeerNodeList(filterTag));
	}

	private static List<BeerModel> createModelsFromNodes(Iterable<Node> beerNodes) throws NotFoundException, WrongNodeTypeException {
		final List<BeerModel> models = new LinkedList<BeerModel>();
		for (Node n : beerNodes) {
			models.add(new BeerModel(n));
		}
		return models;
	}
}
