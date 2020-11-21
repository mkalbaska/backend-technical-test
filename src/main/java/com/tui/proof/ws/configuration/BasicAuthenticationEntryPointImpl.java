package com.tui.proof.ws.configuration;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthenticationEntryPointImpl extends BasicAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() {
        setRealmName(RandomStringUtils.randomAlphabetic(10));
        super.afterPropertiesSet();
    }
}
