package me.yushust.votes.common.serialize;

import com.google.gson.*;
import me.yushust.votes.common.api.VoteResponse;

import java.lang.reflect.Type;

public class VoteResponseDeserializer
    implements JsonDeserializer<VoteResponse> {

  @Override
  public VoteResponse deserialize(
      JsonElement json,
      Type typeOfT,
      JsonDeserializationContext context
  ) throws JsonParseException {
    JsonObject object = json.getAsJsonObject();
    String web = object.get("web").getAsString();
    int status = object.get("status").getAsInt();
    return new VoteResponse(
        web,
        VoteResponse.Status.values()[status]
    );
  }

}
