package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;

/**
 * Json Serializer for the beer domain class.
 */
public class BeerSerializer extends JsonSerializer<BeerModel> {
	
	private static final Logger LOG = Logger.getLogger(BeerSerializer.class);
	
	@Override
	public void serialize(BeerModel beer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", beer.getId());
		jsonGenerator.writeStringField("name", beer.getName());
		jsonGenerator.writeStringField("image", Res.PUBLIC_API_URL + Res.IMAGE_COLLECTION + "/" + beer.getPicture());
		jsonGenerator.writeStringField("brand", beer.getBrand());

		jsonGenerator.writeNumberField("rating", beer.getAverageRatingShortened());
	
		try {
			jsonGenerator.writeObjectFieldStart("brewery");
			if(beer.getBrewery().isUnknown()) {
				jsonGenerator.writeBooleanField("unknown", true);
			} else {
				jsonGenerator.writeNumberField("id", beer.getBrewery().getId());
				jsonGenerator.writeStringField("name", beer.getBrewery().getName());
				jsonGenerator.writeStringField("uri", Res.getResourceUri(beer.getBrewery()));
			}
			jsonGenerator.writeEndObject();
		} catch (WrongNodeTypeException e) {
			LOG.error(e.getMessage(), e);
		} catch (NotFoundException e) {
			LOG.error(e.getMessage(), e);
		}

		try {
			jsonGenerator.writeObjectFieldStart("beertype");
			if(beer.getBeertype().isUnknown()) {
				jsonGenerator.writeBooleanField("unknown", true);
			} else {
				jsonGenerator.writeNumberField("id", beer.getBeertype().getId());
				jsonGenerator.writeStringField("name", beer.getBeertype().getName());
				jsonGenerator.writeStringField("uri", Res.getResourceUri(beer.getBeertype()));
			}
			jsonGenerator.writeEndObject();
		} catch (NotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (WrongNodeTypeException e) {
			LOG.error(e.getMessage(), e);
		}

		try {
			jsonGenerator.writeArrayFieldStart("tags");
			for (TagModel tag : beer.getTagModels()) {
				jsonGenerator.writeStartObject();
				jsonGenerator.writeStringField("name", tag.getName());
				jsonGenerator.writeStringField("uri", Res.getResourceUri(tag));
				jsonGenerator.writeEndObject();
			}
			jsonGenerator.writeEndArray();
		} catch (NotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (WrongNodeTypeException e) {
			LOG.error(e.getMessage(), e);
		}

		jsonGenerator.writeStringField("uri", Res.getResourceUri(beer));
		jsonGenerator.writeEndObject();
	}

	@Override
	public Class<BeerModel> handledType() {
		return BeerModel.class;
	}

}
