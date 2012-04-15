package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Tag;

/**
 * Json Serializer for the beer domain class.
 * 
 * @author jfurrer
 *
 */
public class BeerSerializer extends JsonSerializer<Beer> {

	@Override
	public void serialize(Beer beer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", beer.getName());
		jsonGenerator.writeStringField("image", beer.getImage());
		jsonGenerator.writeStringField("brand", beer.getBrand());
		jsonGenerator.writeStringField("beertype", Res.getResourceUri(beer.getBeertype()));
		
		jsonGenerator.writeArrayFieldStart("tags");
		for(Tag tag : beer.getTags()) {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStringField("name", tag.getName());
			jsonGenerator.writeStringField("uri", Res.getResourceUri(tag));
			jsonGenerator.writeEndObject();
		}
		jsonGenerator.writeEndArray();
		
		jsonGenerator.writeStringField("uri", Res.getResourceUri(beer));
		jsonGenerator.writeEndObject();
	}
	
	@Override
	public Class<Beer> handledType() {
		return Beer.class;
	}

}
