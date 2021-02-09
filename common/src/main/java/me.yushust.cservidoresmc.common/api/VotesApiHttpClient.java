package me.yushust.cservidoresmc.common.api;

import com.google.gson.Gson;
import me.yushust.cservidoresmc.common.config.ConfigurationAdapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Default implementation of {@link VotesApiClient}
 * to interact with the votes http API
 */
public class VotesApiHttpClient
    implements VotesApiClient {

  private static final String BASE_URL = "https://40servidoresmc.es/api2.php";

  private final ConfigurationAdapter config;
  private final Gson gson;
  private final Executor executor;

  public VotesApiHttpClient(
      ConfigurationAdapter config,
      Gson gson,
      Executor executor
  ) {
    this.config = config;
    this.gson = gson;
    this.executor = executor;
  }

  @Override
  public CompletableFuture<VoteResponse> vote(String playerName) {
    String apiKey = getApiKey();
    return CompletableFuture.supplyAsync(() -> {
      try {
        return getAndParse(
            BASE_URL + "?nombre=" + playerName + "&clave=" + apiKey,
            VoteResponse.class
        );
      } catch (IOException e) {
        throw new IllegalStateException("Cannot execute http request to api", e);
      }
    }, executor);
  }

  @Override
  public CompletableFuture<ServerStatistics> fetchStats() {
    String apiKey = getApiKey();
    return CompletableFuture.supplyAsync(() -> {
      try {
        return getAndParse(
            BASE_URL + "?estadisticas=1&clave=" + apiKey,
            ServerStatistics.class
        );
      } catch (IOException e) {
        throw new IllegalStateException("Cannot execute http request to api", e);
      }
    }, executor);
  }

  private <T> T getAndParse(String urlString, Class<T> type)
      throws IOException {

    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setReadTimeout(getTimeout());

    try (Reader reader = new InputStreamReader(connection.getInputStream())) {
      return gson.fromJson(reader, type);
    }
  }

  private int getTimeout() {
    int timeout = config.getInt("api.timeout");
    if (timeout < 0) {
      throw new IllegalStateException("Invalid timeout, it's negative! (" + timeout + ')');
    } else {
      return timeout;
    }
  }

  private String getApiKey() {
    String apiKey = config.getString("api.key");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new IllegalStateException("Invalid API Key, it's null or empty!");
    } else {
      return apiKey;
    }
  }

}
