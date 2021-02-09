package me.yushust.cservidoresmc.common.api;

import java.util.List;

/**
 * Represents the server statistics
 * obtained by executing a http request
 * to the votes server (40ServidoresMC)
 */
public class ServerStatistics {

  private final String name;
  private final int place;
  private final int dayVotes;
  private final int rewardedDayVotes;
  private final int weekVotes;
  private final int rewardedWeekVotes;
  private final List<Vote> lastVotes;

  public ServerStatistics(
      String name,
      int place,
      int dayVotes,
      int rewardedDayVotes,
      int weekVotes,
      int rewardedWeekVotes,
      List<Vote> lastVotes
  ) {
    this.name = name;
    this.place = place;
    this.dayVotes = dayVotes;
    this.rewardedDayVotes = rewardedDayVotes;
    this.weekVotes = weekVotes;
    this.rewardedWeekVotes = rewardedWeekVotes;
    this.lastVotes = lastVotes;
  }

  public String getName() {
    return name;
  }

  public int getPlace() {
    return place;
  }

  public int getDayVotes() {
    return dayVotes;
  }

  public int getRewardedDayVotes() {
    return rewardedDayVotes;
  }

  public int getWeekVotes() {
    return weekVotes;
  }

  public int getRewardedWeekVotes() {
    return rewardedWeekVotes;
  }

  public List<Vote> getLastVotes() {
    return lastVotes;
  }

  public static class Vote {

    private final String name;
    private final boolean rewarded;

    public Vote(String name, boolean rewarded) {
      this.name = name;
      this.rewarded = rewarded;
    }

    public String getName() {
      return name;
    }

    public boolean isRewarded() {
      return rewarded;
    }

  }

}
