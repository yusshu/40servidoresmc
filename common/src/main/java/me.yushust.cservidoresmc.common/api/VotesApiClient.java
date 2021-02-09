package me.yushust.cservidoresmc.common.api;

import java.util.concurrent.CompletableFuture;

/**
 * The main handler for the votes
 * API (40servidoresmc.com)
 */
public interface VotesApiClient {

  /**
   * Sends a vote request of the given {@code playerName}
   * @param playerName The voter name
   * @return Asynchronous response of the vote
   */
  CompletableFuture<VoteResponse> vote(String playerName);

  /**
   * Fetches the stats to the votes http api
   * for the current server
   */
  CompletableFuture<ServerStatistics> fetchStats();

}
