package me.yushust.votes.common.serialize;

import com.google.gson.*;
import me.yushust.votes.common.api.ServerStatistics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServerStatisticsDeserializer
    implements JsonDeserializer<ServerStatistics> {

  @Override
  public ServerStatistics deserialize(
      JsonElement json,
      Type typeOfT,
      JsonDeserializationContext context
  ) throws JsonParseException {
    JsonObject object = json.getAsJsonObject();
    JsonArray lastVotesJson = object.get("ultimos20votos").getAsJsonArray();
    List<ServerStatistics.Vote> lastVotes = new ArrayList<>();
    for (JsonElement element : lastVotesJson) {
      JsonObject vote = element.getAsJsonObject();
      lastVotes.add(new ServerStatistics.Vote(
          vote.get("nombre").getAsString(),
          vote.get("premiado").getAsBoolean()
      ));
    }
    return new ServerStatistics(
        object.get("nombre").getAsString(),
        object.get("puesto").getAsInt(),
        object.get("votoshoy").getAsInt(),
        object.get("votoshoypremiados").getAsInt(),
        object.get("votossemanales").getAsInt(),
        object.get("votossemanalespremiados").getAsInt(),
        lastVotes
    );
  }

}
