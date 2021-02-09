package me.yushust.cservidoresmc.common.api;

/**
 * Represents the result
 * of a vote, it can be successful
 * or a fail
 */
public class VoteResponse {

  /** The server web in the votes page*/
  private final String web;

  /** The vote status */
  private final Status status;

  public VoteResponse(String web, Status status) {
    this.web = web;
    this.status = status;
  }

  public String getWeb() {
    return web;
  }

  public Status getStatus() {
    return status;
  }

  public enum Status {
    NOT_VOTED,
    SUCCESS,
    ALREADY_VOTED,
    INVALID_KEY
  }

}
