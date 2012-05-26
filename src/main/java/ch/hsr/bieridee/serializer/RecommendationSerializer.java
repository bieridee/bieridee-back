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
import ch.hsr.bieridee.models.RecommendationModel;

/**
 * JSON serializer for recommendation models.
 * 
 */
public class RecommendationSerializer extends JsonSerializer<RecommendationModel> {
	
	private static final Logger LOG = Logger.getLogger(RecommendationSerializer.class);
	
	@Override
	public void serialize(RecommendationModel recommendation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("weight", recommendation.getWeight());
		jsonGenerator.writeNumberField("normalizedWeight", recommendation.getRoundedNormalizdWeight());

		jsonGenerator.writeObjectFieldStart("beer");
		try {
			jsonGenerator.writeNumberField("id", recommendation.getBeer().getId());
			jsonGenerator.writeStringField("name", recommendation.getBeer().getName());
			jsonGenerator.writeStringField("uri", Res.getResourceUri(recommendation.getBeer()));
		} catch (NotFoundException e) {
			LOG.debug(e.getMessage(), e);
		} catch (WrongNodeTypeException e) {
			LOG.debug(e.getMessage(), e);
		}
		jsonGenerator.writeEndObject();

		jsonGenerator.writeObjectFieldStart("user");
		try {
			jsonGenerator.writeStringField("user", recommendation.getUser().getUsername());
			jsonGenerator.writeStringField("uri", Res.getResourceUri(recommendation.getUser()));
		} catch (NotFoundException e) {
			LOG.debug(e.getMessage(), e);
		} catch (WrongNodeTypeException e) {
			LOG.debug(e.getMessage(), e);
		}
		jsonGenerator.writeEndObject();

		jsonGenerator.writeEndObject();

	}

	@Override
	public Class<RecommendationModel> handledType() {
		return RecommendationModel.class;
	}
}
