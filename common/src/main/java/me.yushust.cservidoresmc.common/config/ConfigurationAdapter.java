package me.yushust.cservidoresmc.common.config;

/**
 * Configuration abstraction, used to support
 * any platform. Implementations are platform-specific.
 */
public interface ConfigurationAdapter {

  /** Gets the string at the given {@code path} */
  String getString(String path);

}
