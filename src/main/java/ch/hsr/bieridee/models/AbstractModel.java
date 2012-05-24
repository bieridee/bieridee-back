package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Abstract class to provide an unified interface to the models.
 */
public abstract class AbstractModel {
	protected Node node;
	
	public abstract IDomain getDomainObject();
	
	public Node getNode() {
		return this.node;
	}
	
}
