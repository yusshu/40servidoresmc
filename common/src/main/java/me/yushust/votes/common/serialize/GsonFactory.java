package me.yushust.votes.common.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.yushust.votes.common.api.ServerStatistics;
import me.yushust.votes.common.api.VoteResponse;

/**
 * Utility class for creating pre-configured
 * {@link Gson} instances
 */
public final class GsonFactory {

  private GsonFactory() {
  }

  /**
   * Creates a new {@link Gson} instance registering
   * deserializers for {@link ServerStatistics} and
   * {@link VoteResponse} plain classes
   */
  public static Gson create() {
    return new GsonBuilder()
        .registerTypeAdapter(ServerStatistics.class, new ServerStatisticsDeserializer())
        .registerTypeAdapter(VoteResponse.class, new VoteResponseDeserializer())
        .create();
  }

}
