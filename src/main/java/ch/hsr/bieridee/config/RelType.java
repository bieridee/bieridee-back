package ch.hsr.bieridee.config;

import org.neo4j.graphdb.RelationshipType;

/**
 * Graph relationships.
 * 
 * @author jfurrer
 * 
 */
public enum RelType implements RelationshipType {
	INDEX_BEER, INDEX_USER, INDEX_TIMELINE, INDEX_TAG, INDEX_BREWERY, INDEX_BEERTYPE, INDEXES, HAS_BEERTYPE, DOES, CONTAINS, HAS_TAG, INDEX_TIMELINESTART, NEXT, BREWN_BY,HAS_PROFILE,
}
