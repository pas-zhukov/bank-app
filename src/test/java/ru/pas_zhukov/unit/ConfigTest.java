package ru.pas_zhukov.unit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pas_zhukov.config.AccountProperties;

public class ConfigTest {

    public AnnotationConfigApplicationContext context;

    public AccountProperties properties;
    public Long defaultAmount;
    public Double transferCommission;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext("ru.pas_zhukov");
        properties = context.getBean(AccountProperties.class);
        defaultAmount = properties.getDefaultAmount();
        transferCommission = properties.getTransferCommission();
    }

    @Test
    public void propertiesNotNullTest() {
        assert properties != null;
        assert defaultAmount != null;
        assert transferCommission != null;
    }
}


