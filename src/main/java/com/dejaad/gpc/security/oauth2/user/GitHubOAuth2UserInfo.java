package com.dejaad.gpc.security.oauth2.user;

import java.util.Map;

public class GitHubOAuth2UserInfo extends OAuth2UserInfo {
    public GitHubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return "no-email";
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
