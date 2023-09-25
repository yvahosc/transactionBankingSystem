package org.makaia.transactionBankingSystem.validation;

import org.makaia.transactionBankingSystem.exception.ApiException;

public interface ValidationEntryData {
    String validationEntryData() throws ApiException;
}
