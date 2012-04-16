package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.domain.User;

/**
 * JSON serializer for the user array.
 *
 */
public class UserListSerializer extends JsonSerializer<User[]> {

	@Override
	public void serialize(User[] users, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartArray();
		for (User user : users) {
			new UserSerializer().serialize(user, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}
	
	@Override
	public Class<User[]> handledType() {
		return User[].class;
	}

}
