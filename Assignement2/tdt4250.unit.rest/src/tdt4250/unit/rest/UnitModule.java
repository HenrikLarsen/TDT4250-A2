package tdt4250.unit.rest;

import java.io.IOException;

import org.osgi.service.component.annotations.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

import tdt4250.unit.api.UnitSearchResult;


public class UnitModule extends Module {

	@Override
	public String getModuleName() {
		return "UnitModule";
	}

	@Override
	public Version version() {
		return Version.unknownVersion();
	}

	private final SimpleSerializers serializers = new SimpleSerializers();

	public UnitModule() {
		serializers.addSerializer(UnitSearchResult.class, new JsonSerializer<UnitSearchResult>() {
			@Override
			public void serialize(UnitSearchResult unitSearchResult, JsonGenerator jsonGen,
					SerializerProvider serializerProvider) throws IOException {
				jsonGen.writeStartObject(unitSearchResult);
				jsonGen.writeBooleanField("success", unitSearchResult.isSuccess());
				jsonGen.writeStringField("message", unitSearchResult.getMessage());
				if (unitSearchResult.getLink() != null) {
					jsonGen.writeStringField("link", unitSearchResult.getLink().toString());
				}
			}
		});
	}

	@Override
	public void setupModule(final SetupContext context) {
		context.addSerializers(serializers);
	}
}
