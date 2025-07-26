package abdumalik.prodev.userservice.security.oauth2;

import abdumalik.prodev.userservice.model.entity.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new IllegalArgumentException("Sorry! Login with " + registrationId + " is not supported yet.");
        }

    }

}