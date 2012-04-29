package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.models.UserModel;

/**
 * JSON serializer for the user object.
 * 
 */
public class UserSerializer extends JsonSerializer<UserModel> {

	@Override
	public void serialize(UserModel userModel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		final User user = userModel.getDomainObject();
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("username", user.getUsername());
		jsonGenerator.writeStringField("prename", user.getPrename());
		jsonGenerator.writeStringField("surname", user.getSurname());
		jsonGenerator.writeStringField("email", user.getEmail());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(user));
		jsonGenerator.writeEndObject();
	}

	@Override
	public Class<UserModel> handledType() {
		return UserModel.class;
	}

}
