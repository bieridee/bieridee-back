package ch.hsr.bieridee;

import org.restlet.resource.Get;   
import org.restlet.resource.ServerResource;   
  
/**  
 * Resource which has only one representation.  
 *   
 */  
public class HelloWorldResource extends ServerResource {   
  
    @Get  
    public String represent() {   
        return "hello, world";   
    }   
  
} 
