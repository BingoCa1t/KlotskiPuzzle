package com.klotski.map.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klotski.map.MapData;

import java.io.IOException;

public class MapDataDeserializer extends JsonDeserializer<MapData>
{
    @Override
    public MapData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException
    {
        return null;
    }
}
