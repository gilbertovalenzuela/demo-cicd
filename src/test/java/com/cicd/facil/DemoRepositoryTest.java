package com.cicd.facil;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DemoRepositoryTest {

    private DemoRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new DemoRepository();
    }

    @Test
    void itShouldGetUserName() {
        String userName = underTest.getUserName();

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userName).isEqualTo("DemoName");
        assertions.assertAll();
    }
}