package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import ch.hsr.bieridee.domain.Barcode;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work and persist the barcode object.
 */
public class BarcodeModel extends AbstractModel {

	private Barcode domainObject;
	private Node node;

	public BarcodeModel(String code) throws WrongNodeTypeException {
		this(DBUtil.getNodeByBarcode(code));
	}

	private BarcodeModel(String code, String format) {
		this.node = DBUtil.createNode(NodeType.BARCODE);
		this.domainObject = new Barcode(code, format);
		this.setId(this.node.getId());
		this.setCode(code);
		this.setFormat(format);
	}

	public BarcodeModel(Node node) throws WrongNodeTypeException, NotFoundException {

		NodeUtil.checkNode(node, NodeType.BARCODE);

		this.node = node;

		final String code = (String) this.node.getProperty(NodeProperty.Barcode.CODE);
		final String format = (String) this.node.getProperty(NodeProperty.Barcode.FORMAT);
		this.domainObject = new Barcode(code, format);
	}

	public Barcode getDomainObject() {
		return this.domainObject;
	}

	public Node getNode() {
		return this.node;
	}

	public long getId() {
		return this.domainObject.getId();
	}

	public String getCode() {
		return this.domainObject.getCode();
	}

	public String getFormat() {
		return this.domainObject.getFormat();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setId(long id) {
		this.domainObject.setId(id);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setCode(String code) {
		DBUtil.setProperty(this.node, NodeProperty.Barcode.CODE, code);
		this.domainObject.setCode(code);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setFormat(String format) {
		DBUtil.setProperty(this.node, NodeProperty.Barcode.FORMAT, format);
		this.domainObject.setFormat(format);
	}

	/**
	 * @param code
	 *            Barcode.
	 * @param format
	 *            Barcode format.
	 * @return a new <code>BarcodeModel</code> representing the barcode.
	 */
	public static BarcodeModel create(String code, String format) {
		return new BarcodeModel(code, format);
	}
}
