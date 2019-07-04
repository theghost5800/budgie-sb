package com.sap.broker.budgie.domain;

import java.net.URI;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class BindResource {
    @SerializedName("app_guid")
    private UUID appGuid;
    private URI route;

    public BindResource(UUID appGuid, URI route) {
        this.appGuid = appGuid;
        this.route = route;
    }

    public UUID getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(UUID appGuid) {
        this.appGuid = appGuid;
    }

    public URI getRoute() {
        return route;
    }

    public void setRoute(URI route) {
        this.route = route;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appGuid == null) ? 0 : appGuid.hashCode());
        result = prime * result + ((route == null) ? 0 : route.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BindResource other = (BindResource) obj;
        if (appGuid == null) {
            if (other.appGuid != null)
                return false;
        } else if (!appGuid.equals(other.appGuid))
            return false;
        if (route == null) {
            if (other.route != null)
                return false;
        } else if (!route.equals(other.route))
            return false;
        return true;
    }

}
