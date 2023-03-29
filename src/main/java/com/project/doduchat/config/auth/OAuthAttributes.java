package com.project.doduchat.config.auth;

import com.project.doduchat.domain.Mentee;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    // 추가
    private String nickname;
    private String gender;
    private String mobile;
    private String birthyear;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String nickname, String gender, String mobile, String birthyear) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        // 추가
        this.nickname = nickname;
        this.gender = gender;
        this.mobile = mobile;
        this.birthyear = birthyear;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                // 추가
                //.nickname((String) attributes.get("nickname"))
                //.gender((String) attributes.get("gender"))
                //.mobile((String) attributes.get("mobile"))
                //.birthyear((String) attributes.get("birthyear"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                // 추가
                .nickname((String) response.get("nickname"))
                .gender((String) response.get("gender")) // gender 추가
                .mobile((String) response.get("mobile")) // mobile 추가
                .birthyear((String) response.get("birthyear")) // birthyear 추가
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Mentee toEntity() {
        return Mentee.builder()
                .nickname(name)
                .email(email)
                // 추가
                .nickname(nickname)
                .gender(gender)
                .phone(mobile)
                .age(2023 - Integer.parseInt(birthyear))
                .build();
    }

    public Mentee toGoogleEntity(){
        return Mentee.builder()
                .nickname(name)
                .email(email)
                .build();

    }

}