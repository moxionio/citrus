/*
 * Copyright 2006-2010 the original author or authors.
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

package com.consol.citrus.variable;

import org.springframework.integration.core.Message;

import com.consol.citrus.context.TestContext;

/**
 * Class extracting variables form messages. Implementing classes may read
 * message contents and save those to test variables.
 * 
 * @author Christoph Deppisch
 */
public interface VariableExtractor {
    /**
     * 
     * @param message
     * @param context
     */
    public void extractVariables(Message<?> message, TestContext context);
}
