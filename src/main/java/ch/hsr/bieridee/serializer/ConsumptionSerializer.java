package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.models.ConsumptionModel;

/**
 * JSON serializer for action models.
 * 
 */
public class ConsumptionSerializer extends JsonSerializer<ConsumptionModel> {
	@Override
	public void serialize(ConsumptionModel consumptionModel, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("type", consumptionModel.getType());
		jsonGenerator.writeStringField("date", consumptionModel.getDate().toString());
		jsonGenerator.writeNumberField("timestamp", consumptionModel.getDate().getTime());

		jsonGenerator.writeObjectFieldStart("beer");
		jsonGenerator.writeStringField("name", consumptionModel.getBeer().getName());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(consumptionModel.getBeer().getDomainObject()));
		jsonGenerator.writeEndObject();

		jsonGenerator.writeObjectFieldStart("user");
		jsonGenerator.writeStringField("user", consumptionModel.getUser().getUsername());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(consumptionModel.getUser().getDomainObject()));
		jsonGenerator.writeEndObject();

		jsonGenerator.writeEndObject();

	}

	@Override
	public Class<ConsumptionModel> handledType() {
		return ConsumptionModel.class;
	}
}
