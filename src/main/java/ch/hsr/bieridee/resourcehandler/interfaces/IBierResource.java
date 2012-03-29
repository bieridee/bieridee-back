package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.resource.Get;

import ch.hsr.bieridee.Bier;

public interface IBierResource {

	@Get
	public Bier retrieve();
	
}
