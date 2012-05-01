package ch.hsr.bieridee.config;

import org.neo4j.graphdb.RelationshipType;

/**
 * Graph relationships.
 */
public enum RelType implements RelationshipType {
	INDEX_BEER, INDEX_USER, INDEX_TIMELINE, INDEX_TAG, INDEX_BREWERY, INDEX_BEERTYPE, INDEXES, HAS_BEERTYPE, DOES, CONTAINS, HAS_TAG, INDEX_TIMELINESTART, NEXT, BREWN_BY, INDEX_ACTIVERATINGINDEX, INDEXES_ACTIVE
}
