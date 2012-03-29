package ch.hsr.bieridee.resourcehandler;

import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.Bier;
import ch.hsr.bieridee.resourcehandler.interfaces.IBierResource;

public class BeerResource extends ServerResource implements IBierResource {

	@Override
	public Bier retrieve() {
		String id = (String) this.getRequestAttributes().get(
				new String("BeerID"));
		Bier bier = new Bier(Integer.parseInt(id));
		/*
		 * bier.setName("Eve Litchi"); bier.setId(13);
		 */
		return bier;
	}

}