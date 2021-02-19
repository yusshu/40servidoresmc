package me.yushust.votes.bukkit.command;

import me.yushust.votes.common.api.VotesApiClient;
import me.yushust.votes.common.config.ConfigurationAdapter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;

/**
 * Command to check the server statistics from 40servidoresmc
 */
public class StatsCommand implements CommandExecutor {

  private final VotesApiClient client;
  private final ConfigurationAdapter config;

  public StatsCommand(
      VotesApiClient client,
      ConfigurationAdapter config
  ) {
    this.client = client;
    this.config = config;
  }

  @Override
  public boolean onCommand(
      CommandSender sender,
      Command command,
      String label,
      String[] args
  ) {

    if (sender.hasPermission("40servidoresmc.stats")) {
      // TODO: Send no permission message
      return true;
    }

    client.fetchStats()
        .whenComplete((stats, error) -> {
          if (error != null) {
            // TODO: Send error message
            return;
          }

          if (stats == null) {
            // TODO: Send error message
            return;
          }

          String delimiter = config.getString("messages.stats.last20votes.delimiter");
          String lastVotes = stats.getLastVotes()
              .stream()
              .map(vote -> {
                String elementTemplate = config.getString(
                    "messages.stats.last20votes.element." +
                        (vote.isRewarded() ? "rewarded" : "not-rewarded")
                );
                return elementTemplate.replace("%voter%", vote.getName());
              })
              .collect(Collectors.joining(delimiter));

          sender.sendMessage(
              config.getString("messages.stats.template")
                .replace("%name%", stats.getName())
                .replace("%place%", stats.getPlace() + "")
                .replace("%day_votes%", stats.getDayVotes() + "")
                .replace("%rewarded_day_votes%", stats.getRewardedDayVotes() + "")
                .replace("%week_votes%", stats.getWeekVotes() + "")
                .replace("%rewarded_week_votes%", stats.getRewardedWeekVotes() + "")
                .replace("%last20votes%", lastVotes)
          );
        });
    return true;
  }
}
