package me.yushust.votes.common.async;

import me.yushust.votes.common.config.ConfigurationAdapter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Utility class for creating
 * {@link Executor} instances
 */
public final class ExecutorFactory {

  private ExecutorFactory() {
  }

  /**
   * Creates a new {@link Executor} from the specified
   * {@code config}
   * @return The created executor
   */
  public static Executor createFrom(
      ConfigurationAdapter config,
      Executor platformExecutor
  ) {

    String type = config.getString("asyncops.executor");

    if (type == null) {
      type = "platform";
    } else {
      type = type.toLowerCase();
    }

    switch (type) {

      case "platform": {
        return platformExecutor;
      }

      case "fixed-thread-pool": {
        int threads = config.getInt("asyncops.threads");
        if (threads < 1) {
          throw new IllegalArgumentException(
              "Invalid configuration value found in path 'asyncops.threads': "
              + " Invalid thread count '" + threads + "'"
          );
        }
        return Executors.newFixedThreadPool(threads);
      }

      default: {
        throw new IllegalArgumentException(
            "Unsupported executor type: '" + type + "'. Path: 'asyncops.executor'"
        );
      }
    }
  }

}
