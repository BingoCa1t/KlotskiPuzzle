package com.klotski.map.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klotski.logic.Pos;
import com.klotski.map.MapData;
import com.klotski.polygon.Chess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapDataDeserializer extends JsonDeserializer<MapData>
{
    @Override
    public MapData deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException
    {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        MapData mapData = new MapData();

        mapData.setMapType(node.get("mapType").asInt());
        mapData.setMapName(node.get("name").asText());

        // 反序列化 grades 数组
        ArrayList<Integer> grades = new ArrayList<>();
        JsonNode gradesNode = node.get("grades");
        for (JsonNode gradeNode : gradesNode) {
            grades.add(gradeNode.asInt());
        }
        mapData.setGrades(new int[]{grades.get(0),grades.get(1),grades.get(2)});

        // 反序列化 exits 数组
        ArrayList<Pos> exits = new ArrayList<>();
        JsonNode exitsNode = node.get("exits");
        for (JsonNode exitNode : exitsNode) {
            int x = exitNode.get(0).asInt();
            int y = exitNode.get(1).asInt();
            exits.add(new Pos(x, y));
        }
        mapData.setExit(exits);

        // 反序列化 chesses 数组
        ArrayList<Chess> chesses = new ArrayList<>();
        JsonNode chessesNode = node.get("chesses");
        for (JsonNode chessNode : chessesNode) {
            Chess chess = new Chess(chessNode.get("imagePath").asText(),chessNode.get("chessName").asText(),chessNode.get("width").asInt(),chessNode.get("height").asInt());

            int x = Integer.parseInt(chessNode.get("x").asText());
            int y = Integer.parseInt(chessNode.get("y").asText());
            chess.setXY(new Pos(x, y));
            chesses.add(chess);
        }
        mapData.setChesses(chesses);

        return mapData;
    }
}
