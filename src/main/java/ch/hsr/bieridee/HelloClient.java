package ch.hsr.bieridee;

import java.io.IOException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class HelloClient {
	
	public static void main(String[] args) throws ResourceException, IOException{
		ClientResource client = new ClientResource("http://localhost:8000/Bier/Duff");
		client.get().write(System.out);
	}

}
