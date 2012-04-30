package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.models.TagModel;

/**
 * JSON serializer for an array of tags.
 *
 */
public class TagListSerializer extends JsonSerializer<TagModel[]> {

	@Override
	public void serialize(TagModel[] tagModels, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartArray();
		for(TagModel tagModel : tagModels) {
			new TagSerializer().serialize(tagModel, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}
	
	@Override
	public Class<TagModel[]> handledType() {
		return TagModel[].class;
	}

}
