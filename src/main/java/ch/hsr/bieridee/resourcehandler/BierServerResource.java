package ch.hsr.bieridee.resourcehandler;

import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.Bier;
import ch.hsr.bieridee.resourcehandler.interfaces.BierResource;

public class BierServerResource extends ServerResource implements BierResource {

	@Override
	public Bier retrieve() {
		Bier bier = new Bier();
		/*bier.setName("Eve Litchi");
		bier.setId(13);*/
		return bier;
	}

}