package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.models.RatingModel;

/**
 * JSON serializer for action models.
 * 
 */
public class RatingSerializer extends JsonSerializer<RatingModel> {
	@Override
	public void serialize(RatingModel ratingModel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("type", ratingModel.getType());
		jsonGenerator.writeStringField("date", ratingModel.getDate().toString());
		jsonGenerator.writeNumberField("timestamp", ratingModel.getDate().getTime());
		
		jsonGenerator.writeObjectFieldStart("beer");
		jsonGenerator.writeStringField("name", ratingModel.getBeer().getName());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(ratingModel.getBeer().getDomainObject()));
		jsonGenerator.writeEndObject();

		jsonGenerator.writeObjectFieldStart("user");
		jsonGenerator.writeStringField("user", ratingModel.getUser().getUsername());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(ratingModel.getUser().getDomainObject()));
		jsonGenerator.writeEndObject();

		jsonGenerator.writeNumberField("rating", ratingModel.getRating());

		jsonGenerator.writeEndObject();

	}

	@Override
	public Class<RatingModel> handledType() {
		return RatingModel.class;
	}
}
