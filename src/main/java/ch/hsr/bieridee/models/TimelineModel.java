package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Timeline helper model.
 * 
 */
public class TimelineModel extends AbstractModel {

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
		return createModelsFromNodes(DBUtil.getTimeLine(0));
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
		return createModelsFromNodes(DBUtil.getTimeLine(maxNumber));
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

}
