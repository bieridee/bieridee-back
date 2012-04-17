package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IBeertypeListResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * Beertype list resource.
 *
 */
public class BeertypeListResource extends ServerResource implements IBeertypeListResource {
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		final List<Node> beertypeNodeList = DBUtil.getBeertypeNodeList();
		final List<BeertypeModel> beertypeModelList = DomainConverter.createBeertypeModelsFromList(beertypeNodeList);
		
		final List<Beertype> beertypeList = DomainConverter.extractDomainObjectFromModel(beertypeModelList);
		final Beertype[] beertypes = beertypeList.toArray(new Beertype[beertypeList.size()]);
		
		final JacksonRepresentation<Beertype[]> beertypesJacksonRep = new JacksonRepresentation<Beertype[]>(beertypes);
		beertypesJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beertypesJacksonRep;
		
		
	}

}
