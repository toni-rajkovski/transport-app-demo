package com.rajkovski.toni.transportdemo.model;


/**
 * Utilty methods for working with the model.
 */
public final class ModelUtil {

  /**
   * Searches the providers by name and returns the icon url for the given provider.
   *
   * @param transportRoutes the model
   * @param provider the name of the provider
   * @return url
   */
  public static String findProviderIconUrl(TransportRoutes transportRoutes, String provider) {
    switch (provider) {
      case "vbb":
        return transportRoutes.getProvider_attributes().getVbb().getProvider_icon_url();
      case "drivenow":
        return transportRoutes.getProvider_attributes().getDrivenow().getProvider_icon_url();
      case "car2go":
        return transportRoutes.getProvider_attributes().getCar2go().getProvider_icon_url();
      case "google":
        return transportRoutes.getProvider_attributes().getGoogle().getProvider_icon_url();
      case "nextbike":
        return transportRoutes.getProvider_attributes().getNextbike().getProvider_icon_url();
      case "callabike":
        return transportRoutes.getProvider_attributes().getCallabike().getProvider_icon_url();
      default:
        return null;
    }
  }
}
