package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.interfaces.IDomain;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Timeline helper model.
 * 
 */
public final class TimelineModel extends AbstractModel {

	/**
	 * Gets all actions.
	 * 
	 * @return List of AbstractActionModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existing
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<AbstractActionModel> getAll() throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getTimeLine(0, 0));
	}

	/**
	 * Gets the actions filtered by a username.
	 * 
	 * @param username
	 *            username the timeline to be filtered with.
	 * @return List of AbstracActionModels
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not existing.
	 * @throws NotFoundException
	 *             Thrown if a node is not of the desired type.
	 */
	public static List<AbstractActionModel> getAllForUser(String username) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getTimeLineForUser(username, 0, 0));
	}

	/**
	 * Gets a limited number of actions filtered by a username.
	 * 
	 * @param username
	 *            username the timeline to be filtered with.
	 * @param maxNumber
	 *            Count of actions to retrieve
	 * @return List of AbstracActionModels
	 * @throws NotFoundException
	 *             Thrown if a node is not of the desired type.
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not existing.
	 */
	public static List<AbstractActionModel> getAllForUser(String username, int maxNumber) throws NotFoundException, WrongNodeTypeException {
		return getAllForUser(username, maxNumber, 0);
	}

	/**
	 * Gets a limited number of actions filtered by a username.
	 * 
	 * @param username
	 *            username the timeline to be filtered with.
	 * @param maxNumber
	 *            Count of actions to retrieve
	 * @param skipCount
	 *            Count of results to skip
	 * @return List of AbstracActionModels
	 * @throws NotFoundException
	 *             Thrown if a node is not of the desired type.
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not existing.
	 */

	public static List<AbstractActionModel> getAllForUser(String username, int maxNumber, int skipCount) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getTimeLineForUser(username, maxNumber, skipCount));
	}

	/**
	 * Gets a limited number of actions.
	 * 
	 * @param maxNumber
	 *            Count of actions to retrieve
	 * @return Limited list of AbstractActionModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existing
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<AbstractActionModel> getAll(int maxNumber) throws NotFoundException, WrongNodeTypeException {
		return getAll(maxNumber, 0);
	}

	/**
	 * Gets a limited number of actions.
	 * 
	 * @param maxNumber
	 *            Count of actions to retrieve
	 * @param skipCount
	 *            Count of results to skip
	 * @return Limited list of AbstractActionModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existing
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<AbstractActionModel> getAll(int maxNumber, int skipCount) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getTimeLine(maxNumber, skipCount));
	}

	private static List<AbstractActionModel> createModelsFromNodes(List<Node> timelineNodes) throws NotFoundException, WrongNodeTypeException {
		final List<AbstractActionModel> models = new LinkedList<AbstractActionModel>();
		for (Node n : timelineNodes) {
			if (NodeType.CONSUMPTION.equals(n.getProperty(NodeProperty.TYPE))) {
				models.add(new ConsumptionModel(n));
			}
			if (NodeType.RATING.equals(n.getProperty(NodeProperty.TYPE))) {
				models.add(new RatingModel(n));
			}
		}
		return models;
	}

	@Override
	public IDomain getDomainObject() {
		return null;
	}
}
