package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.utils.ReferenceUtil;

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
		jsonGenerator.writeStringField("image", beer.getPicture());
		jsonGenerator.writeStringField("brand", beer.getBrand());
		jsonGenerator.writeStringField("beertype", ReferenceUtil.getBeertypeURI(beer.getBeertype().getId()));
		
		jsonGenerator.writeArrayFieldStart("tags");
		for(Tag tag : beer.getTags()) {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStringField("name", tag.getValue());
			jsonGenerator.writeStringField("uri", "soon");
			jsonGenerator.writeEndObject();
		}
		jsonGenerator.writeEndArray();
		
		jsonGenerator.writeEndObject();
	}
	
	@Override
	public Class<Beer> handledType() {
		return Beer.class;
	}

}
