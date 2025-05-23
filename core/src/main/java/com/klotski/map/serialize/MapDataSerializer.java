package com.klotski.map.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.klotski.logic.Pos;
import com.klotski.map.MapData;
import com.klotski.polygon.Chess;

import java.io.IOException;

/**
 * 自定义地图数据序列化器
 *
 * @author BingoCAT
 */
public class MapDataSerializer extends JsonSerializer<MapData>
{

    @Override
    public void serialize(MapData value, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        gen.writeStartObject();
        gen.writeStringField("type", "mapData");
        gen.writeStringField("mapType",String.valueOf(value.getMapType()));
        gen.writeStringField("height",String.valueOf(value.getHeight()));
        gen.writeStringField("width",String.valueOf(value.getWidth()));
        gen.writeStringField("name",value.getMapName());
        gen.writeStringField("mainIndex",String.valueOf(value.getMainIndex()));
        gen.writeArrayFieldStart("grades");
        for(int i : value.getGrades())
        {
            gen.writeNumber(i);
        }
        gen.writeEndArray();
        gen.writeArrayFieldStart("exits");
        for(Pos p : value.getExit())
        {
            gen.writeStartArray();
            gen.writeNumber(p.getX());
            gen.writeNumber(p.getY());
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeArrayFieldStart("chesses");
        for(Chess chess : value.getChesses())
        {
            gen.writeStartObject();
            gen.writeStringField("chessName", chess.getChessName());
            gen.writeStringField("width", String.valueOf(chess.getChessWidth()));
            gen.writeStringField("height", String.valueOf(chess.getChessHeight()));
            gen.writeStringField("x", String.valueOf(chess.getPosition().getX()));
            gen.writeStringField("y", String.valueOf(chess.getPosition().getY()));
            gen.writeStringField("imagePath",chess.getImagePath());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
