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
    Provider_attributes providerAttributes = transportRoutes.getProvider_attributes();
    if (providerAttributes == null) {
      return null;
    }
    switch (provider) {
      case "vbb":
        return providerAttributes.getVbb() != null
          ? providerAttributes.getVbb().getProvider_icon_url() : null;
      case "drivenow":
        return providerAttributes.getDrivenow() != null ?
          providerAttributes.getDrivenow().getProvider_icon_url() : null;
      case "car2go":
        return providerAttributes.getCar2go() != null ?
          providerAttributes.getCar2go().getProvider_icon_url() : null;
      case "google":
        return providerAttributes.getGoogle() != null ?
          providerAttributes.getGoogle().getProvider_icon_url() : null;
      case "nextbike":
        return providerAttributes.getNextbike() != null ?
          providerAttributes.getNextbike().getProvider_icon_url() : null;
      case "callabike":
        return providerAttributes.getNextbike() != null ?
          providerAttributes.getCallabike().getProvider_icon_url() : null;
      default:
        return null;
    }
  }
}
