package ch.hsr.bieridee;

import org.restlet.resource.Get;

public interface BierResource {

	@Get
	public Bier retrieve();
	
}
