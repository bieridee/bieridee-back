package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.models.BeerModel;

/**
 * Json Serializer for the beer domain class.
 */
public class BeerSerializer extends JsonSerializer<BeerModel> {

	@Override
	public void serialize(BeerModel beerModel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		final Beer beer = beerModel.getDomainObject();
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", beer.getId());
		jsonGenerator.writeStringField("name", beer.getName());
		jsonGenerator.writeStringField("image", Res.PUBLIC_API_URL + Res.IMAGE_COLLECTION + "/" + beer.getPicture());
		jsonGenerator.writeStringField("brand", beer.getBrand());

		jsonGenerator.writeObjectFieldStart("brewery");
		jsonGenerator.writeStringField("name", beer.getBrewery().getName());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(beer.getBrewery()));
		jsonGenerator.writeEndObject();

		jsonGenerator.writeObjectFieldStart("beertype");
		jsonGenerator.writeStringField("name", beer.getBeertype().getName());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(beer.getBeertype()));
		jsonGenerator.writeEndObject();

		jsonGenerator.writeArrayFieldStart("tags");
		for (Tag tag : beer.getTags()) {
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
	public Class<BeerModel> handledType() {
		return BeerModel.class;
	}

}
