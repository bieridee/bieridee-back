package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Abstract class to provide an unified interface to the models.
 * 
 * @author jfurrer
 *
 */
public abstract class AbstractModel {
	private IDomain domainObject;
	private Node node;
	
	public IDomain getDomainObject() {
		return this.domainObject;
	}
	
	public Node getNode() {
		return this.node;
	}
	
}
