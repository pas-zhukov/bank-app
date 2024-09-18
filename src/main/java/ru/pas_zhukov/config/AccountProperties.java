package ru.pas_zhukov.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {

    private final Long defaultAmount;
    private final double transferCommission;

    public AccountProperties(@Value("${account.default-amount}") Long
                                     defaultAmount,
                             @Value("${account.transfer-commission}") double
                                     transferCommission
    ) {
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public Long getDefaultAmount() {
        return defaultAmount;
    }

    public Double getTransferCommission() {
        return transferCommission;
    }
}
