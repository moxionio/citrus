/*
 * Copyright 2006-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.validation.script;

import org.springframework.integration.core.Message;
import org.springframework.integration.message.MessageBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.consol.citrus.exceptions.ValidationException;
import com.consol.citrus.testng.AbstractBaseTest;

/**
 * @author Christoph Deppisch
 */
public class GroovyScriptMessageValidatorTest extends AbstractBaseTest {
    
    GroovyScriptMessageValidator validator = new GroovyScriptMessageValidator();
    
    private Message<?> message;

    @BeforeMethod
    public void prepareTestData() {
        message = MessageBuilder.withPayload("This is plain text!").setHeader("operation", "unitTesting").build();
    }
    
    @Test
    public void testGroovyScriptValidation() throws ValidationException {
        String validationScript = "assert headers.operation == 'unitTesting'\n" +
        		"assert payload == 'This is plain text!'\n" +
        		"assert payload.contains('!')";
        
        ScriptValidationContext validationContext = new ScriptValidationContext(validationScript, 
        GroovyScriptMessageValidator.GROOVY_SCRIPT_TYPE, context);
        
        validator.validateMessage(message, context, validationContext);
    }
    
    @Test
    public void testGroovyScriptValidationVariableSupport() throws ValidationException {
        String validationScript = "assert headers.operation == 'unitTesting'\n" +
                "assert payload == '${plainText}'\n" +
                "assert payload.contains('!')";
        
        context.setVariable("plainText", "This is plain text!");
        
        ScriptValidationContext validationContext = new ScriptValidationContext(validationScript, 
        GroovyScriptMessageValidator.GROOVY_SCRIPT_TYPE, context);
        
        validator.validateMessage(message, context, validationContext);
    }
    
    @Test
    public void testGroovyScriptValidationWrongValue() throws ValidationException {
        String validationScript = "assert headers.operation == 'somethingElse'\n" +
                "assert payload == 'This is plain text!'\n" +
                "assert payload.contains('!')";
        
        ScriptValidationContext validationContext = new ScriptValidationContext(validationScript, 
        GroovyScriptMessageValidator.GROOVY_SCRIPT_TYPE, context);
        
        try {
            validator.validateMessage(message, context, validationContext);
        } catch (ValidationException e) {
            Assert.assertTrue(e.getCause() instanceof AssertionError);
            return;
        }
        
        Assert.fail("Missing validation exception due to wrong value");
    }
}