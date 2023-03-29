package com.project.doduchat.config.auth;

import com.project.doduchat.domain.Mentee;
import com.project.doduchat.repository.MenteeRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Transactional
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MenteeRepository menteeRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //서비스 구분을 위한 작업 (구글:google, 네이버:naver)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); //리턴해야하는 user 정보

        OAuthAttributes attributes;

        if(registrationId.equals("naver")){
            //oauth2user에서 반환하는 사용자 정보는 map이기 때문에 값 하나하나를 반환하기 위해 of()
             attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

            Mentee mentee = saveOrUpdate(attributes);
            httpSession.setAttribute("user", new SessionUser(mentee));
        }
        else if(registrationId.equals("google")){
            attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

            Mentee mentee = saveOrUpdate2(attributes);
            httpSession.setAttribute("user", new SessionUser(mentee));
        }
        else{
            throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        return new DefaultOAuth2User(
                //Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_MENTEE")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    //네이버
    private Mentee saveOrUpdate(OAuthAttributes attributes) {
        // 이메일이 이미 존재하면
        Mentee mentee = menteeRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getNickname(), attributes.getGender(), attributes.getMobile(), 2023 - Integer.parseInt(attributes.getBirthyear())))
                .orElse(attributes.toEntity());

        return menteeRepository.save(mentee);
    }

    //구글
    private Mentee saveOrUpdate2(OAuthAttributes attributes){

        Mentee mentee = menteeRepository.findByEmail(attributes.getEmail())
                //.map(entity -> entity.update2(attributes.getNickname()))
                .orElse(attributes.toGoogleEntity());

        return menteeRepository.save(mentee);
    }
}
