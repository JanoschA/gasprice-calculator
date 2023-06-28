package com.dejaad.gpc.security.oauth2.service;

import com.dejaad.gpc.config.AppConfig;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Service
public class AuthorizationRedirectService {

    private final AppConfig appConfig;

    public AuthorizationRedirectService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appConfig.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getPath().equalsIgnoreCase(clientRedirectUri.getPath())
                            && authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()
                            && Objects.equals(authorizedURI.getScheme(), clientRedirectUri.getScheme());
                });
    }
}
