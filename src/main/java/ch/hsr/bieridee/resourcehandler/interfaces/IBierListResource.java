package ch.hsr.bieridee.resourcehandler.interfaces;

import java.util.List;

import org.restlet.resource.Get;

import ch.hsr.bieridee.Bier;
import ch.hsr.bieridee.domain.Beer;

public interface IBierListResource {

	@Get("json")
	public List<Beer> retrieve();

}
