package ch.hsr.bieridee.config;

import org.neo4j.graphdb.RelationshipType;

/**
 * Graph relationships.
 * 
 * @author jfurrer
 * 
 */
public enum RelType implements RelationshipType {
	INDEX_BEER, INDEXES, HAS_TAG, HAS_BEERTYPE,
}
