package com.example.ft;

import com.example.ft.security.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeatureTogglesApplicationTests {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void jwtTokens() {
        String userName = "nuno";
        String token = jwtTokenProvider.generateToken(userName);
        AssertionErrors.assertEquals("username", userName, jwtTokenProvider.getClaims(token).getSubject());
    }
}
