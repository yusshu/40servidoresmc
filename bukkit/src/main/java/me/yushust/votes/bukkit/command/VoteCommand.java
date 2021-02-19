package me.yushust.votes.bukkit.command;

import me.yushust.votes.common.api.VotesApiClient;
import me.yushust.votes.common.config.ConfigurationAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VoteCommand implements CommandExecutor {

  private final VotesApiClient client;
  private final Lock timestampsLock;
  private final Map<UUID, Long> executionTimestamps;
  private final long cooldown;
  private final ConfigurationAdapter config;

  public VoteCommand(
      VotesApiClient client,
      long cooldown,
      ConfigurationAdapter config
  ) {
    this.client = client;
    this.timestampsLock = new ReentrantLock();
    this.executionTimestamps = new HashMap<>();
    this.cooldown = cooldown;
    this.config = config;
  }

  @Override
  public boolean onCommand(
      CommandSender sender,
      Command command,
      String label,
      String[] args
  ) {
    if (!sender.hasPermission("40servidoresmc.vote")) {
      // TODO: Send no permission message
      return true;
    }

    if (!(sender instanceof Player)) {
      // TODO: Send only players message
      return true;
    }

    Player player = (Player) sender;
    Long lastExecution = executionTimestamps.get(player.getUniqueId());

    if (lastExecution != null) {
      long now = System.currentTimeMillis();
      if (lastExecution + cooldown < now) {
        // TODO: Send slow down message
        return true;
      } else {
        executionTimestamps.put(player.getUniqueId(), now);
      }
    }

    client.vote(player.getName())
        .whenComplete((response, error) -> {

          if (error != null) {
            // TODO: Send error message
            return;
          }

          if (response == null) {
            // TODO: Send error message
            return;
          }

          switch (response.getStatus()) {
            case NOT_VOTED: {
              player.sendMessage(config.getString("messages.not-voted"));
              break;
            }
            case SUCCESS: {
              player.sendMessage(config.getString("messages.message"));

              for (String rewardCommand : config.getStringList("rewards")) {
                Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    rewardCommand.replace("%player%", player.getName())
                );
              }

              if (config.getBoolean("broadcast.enabled")) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                  if (
                      !online.equals(player)
                          || config.getBoolean("broadcast.send-broadcast-to-voter")
                  ) {
                    online.sendMessage(
                        config.getString("messages.broadcast")
                            .replace("%player%", player.getName())
                    );
                  }
                }
              }
              break;
            }
            case ALREADY_VOTED: {
              player.sendMessage(config.getString("messages.already-voted"));
              break;
            }
            case INVALID_KEY: {
              player.sendMessage(config.getString("messages.invalid-key"));
              break;
            }
          }

        });

    return true;
  }

}
