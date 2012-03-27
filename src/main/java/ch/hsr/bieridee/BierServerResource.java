package ch.hsr.bieridee;

import org.restlet.resource.ServerResource;

public class BierServerResource extends ServerResource implements BierResource {

	@Override
	public Bier retrieve() {
		Bier bier = new Bier();
		/*bier.setName("Eve Litchi");
		bier.setId(13);*/
		return bier;
	}

}