package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.User;

/**
 * JSON serializer for the user object.
 *
 */
public class UserSerializer extends JsonSerializer<User> {

	@Override
	public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		  jsonGenerator.writeStartObject();
		  jsonGenerator.writeStringField("username", user.getUsername());
		  jsonGenerator.writeStringField("prename", user.getPrename());
		  jsonGenerator.writeStringField("surname", user.getSurname());
		  jsonGenerator.writeStringField("email", user.getEmail());
		  jsonGenerator.writeStringField("uri", Res.getResourceUri(user));
		  jsonGenerator.writeEndObject();
	}
	
	@Override 
	public Class<User> handledType() {
		return User.class;
	}

}
