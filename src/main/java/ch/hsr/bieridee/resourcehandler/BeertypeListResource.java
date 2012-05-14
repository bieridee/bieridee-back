package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * Beertype list resource.
 *
 */
public class BeertypeListResource extends ServerResource implements ICollectionResource {
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		final List<BeertypeModel> beertypeModelList = BeertypeModel.getAll();
		
		final BeertypeModel[] beertypeModelArray = beertypeModelList.toArray(new BeertypeModel[beertypeModelList.size()]);
		
		final JacksonRepresentation<BeertypeModel[]> beertypesJacksonRep = new JacksonRepresentation<BeertypeModel[]>(beertypeModelArray);
		beertypesJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beertypesJacksonRep;
	}

	@Override
	public Representation store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}
