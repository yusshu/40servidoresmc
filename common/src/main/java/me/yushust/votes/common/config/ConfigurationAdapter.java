package me.yushust.votes.common.config;

import java.util.List;

/**
 * Configuration abstraction, used to support
 * any platform. Implementations are platform-specific.
 */
public interface ConfigurationAdapter {

  /** Gets the string at the given {@code path} */
  String getString(String path);

  /** Gets the boolean at the given {@code path} */
  boolean getBoolean(String path);

  /** Gets the string list at the given {@code path} */
  List<String> getStringList(String path);

  /** Gets the integer value at the given {@code path} */
  int getInt(String path);

}
