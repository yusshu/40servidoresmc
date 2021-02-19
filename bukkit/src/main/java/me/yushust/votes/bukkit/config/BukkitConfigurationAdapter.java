package me.yushust.votes.bukkit.config;

import me.yushust.votes.common.config.ConfigurationAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

public class BukkitConfigurationAdapter implements ConfigurationAdapter {

  private final YamlConfiguration delegate;

  public BukkitConfigurationAdapter(Plugin plugin, String name) {
    File file = new File(plugin.getDataFolder(), name);
    if (file.exists()) {
      this.delegate = YamlConfiguration.loadConfiguration(file);
    } else {
      try {
        InputStream resource = plugin.getResource(name);
        if (resource != null) {
          try (Reader reader = new InputStreamReader(resource)) {
            this.delegate = YamlConfiguration.loadConfiguration(reader);
          }
        } else {
          this.delegate = new YamlConfiguration();
          if (file.createNewFile()) {
            this.delegate.save(file);
          }
        }
      } catch (IOException e) {
        plugin.getLogger()
            .log(
                Level.WARNING,
                "An error occurred while creating a configuration" +
                    " adapter for " + name + " file configuration",
                e
            );
        Bukkit.shutdown();
        throw new IllegalStateException(e);
      }
    }
  }

  @Override
  public String getString(String path) {
    return delegate.getString(path);
  }

  @Override
  public int getInt(String path) {
    return delegate.getInt(path);
  }

}
