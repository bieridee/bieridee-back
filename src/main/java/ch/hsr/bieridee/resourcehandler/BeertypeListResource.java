package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.restlet.data.Status;
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
	public Representation retrieve() {
		
		final List<Node> beertypeNodeList = DBUtil.getBeertypeNodeList();
		List<BeertypeModel> beertypeModelList = null;
		try {
			beertypeModelList = DomainConverter.createBeertypeModelsFromList(beertypeNodeList);
		} catch (WrongNodeTypeException e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e, e.getMessage());
			return null;
		}
		
		final List<Beertype> beertypeList = DomainConverter.extractDomainObjectFromModel(beertypeModelList);
		final Beertype[] beertypes = beertypeList.toArray(new Beertype[beertypeList.size()]);
		
		final JacksonRepresentation<Beertype[]> beertypesJacksonRep = new JacksonRepresentation<Beertype[]>(beertypes);
		beertypesJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beertypesJacksonRep;
		
		
	}

}
