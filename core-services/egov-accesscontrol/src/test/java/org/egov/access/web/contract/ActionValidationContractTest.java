package org.egov.access.web.contract;

import org.egov.access.web.contract.validateaction.ActionValidationContract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionValidationContractTest {

	@Test
	public void testThatActionValidationContractContainsInfoAboutActionAllow() {
		ActionValidationContract actionValidation = ActionValidationContract.builder().allowed("TRUE").build();

		Assertions.assertEquals("TRUE", actionValidation.getAllowed());
	}
}
