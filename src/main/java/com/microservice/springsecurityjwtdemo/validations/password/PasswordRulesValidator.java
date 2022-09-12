package com.microservice.springsecurityjwtdemo.validations.password;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;


public class PasswordRulesValidator implements ConstraintValidator<PasswordRules, String>{

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password==null)
			return false;
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new LowercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new NumericalSequenceRule(3, false),
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
        				String.join(" ", validator.getMessages(result)))
                .addConstraintViolation();
        return false;
	}

}
