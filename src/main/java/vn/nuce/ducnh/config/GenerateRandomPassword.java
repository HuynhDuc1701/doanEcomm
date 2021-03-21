package vn.nuce.ducnh.config;

import com.mifmif.common.regex.Generex;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GenerateRandomPassword {
    private final SecureRandom SECURE_RANDOM = new SecureRandom();

    public String generateRandom() {
        Generex generex = new Generex("([a-z]){1}([a-z]|[A-Z]|[0-9]){5}([0-9]{1})([A-Z]){1}");
        // Generate random String
        String randomStr = generex.random();
        String password = RandomStringUtils.random(8, 0, 0, true, true, (char[]) null, SECURE_RANDOM);
        return randomStr;

    }
}
