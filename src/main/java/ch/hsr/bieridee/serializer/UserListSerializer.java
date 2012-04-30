package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.models.UserModel;

/**
 * JSON serializer for the user array.
 *
 */
public class UserListSerializer extends JsonSerializer<UserModel[]> {

	@Override
	public void serialize(UserModel[] userModels, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartArray();
		for (UserModel userModel : userModels) {
			new UserSerializer().serialize(userModel, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}
	
	@Override
	public Class<UserModel[]> handledType() {
		return UserModel[].class;
	}

}
