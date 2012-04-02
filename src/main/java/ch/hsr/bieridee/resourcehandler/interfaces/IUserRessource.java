package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.resource.Get;

import ch.hsr.bieridee.domain.User;

public interface IUserRessource {

	@Get("txt")
	public String retrieve();

}
