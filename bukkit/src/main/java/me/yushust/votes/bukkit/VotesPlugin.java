package me.yushust.votes.bukkit;

import com.google.gson.Gson;
import me.yushust.votes.bukkit.config.BukkitConfigurationAdapter;
import me.yushust.votes.common.api.VotesApiClient;
import me.yushust.votes.common.api.VotesApiHttpClient;
import me.yushust.votes.common.async.ExecutorFactory;
import me.yushust.votes.common.config.ConfigurationAdapter;
import me.yushust.votes.common.serialize.GsonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;

public class VotesPlugin extends JavaPlugin {

  @Override
  public void onEnable() {

    ConfigurationAdapter config = new BukkitConfigurationAdapter(
        this,
        "config.yml"
    );

    Gson gson = GsonFactory.create();

    Executor executor = ExecutorFactory
        .createFrom(config, getPlatformExecutor());

    VotesApiClient client = new VotesApiHttpClient(config, gson, executor);
  }

  private Executor getPlatformExecutor() {
    return command -> Bukkit.getScheduler().runTaskAsynchronously(this, command);
  }

}
