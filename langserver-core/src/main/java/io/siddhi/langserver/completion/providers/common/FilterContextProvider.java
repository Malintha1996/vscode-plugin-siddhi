/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for FilterContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Execution_elementContext}.
 */
public class FilterContextProvider extends CompletionProvider {

    public FilterContextProvider() {
        this.providerName = SiddhiQLParser.FilterContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //MathOperationContext is the only context that can be contained by filter context.
        return LSOperationContext.INSTANCE
                .getProvider(SiddhiQLParser.Math_operationContext.class.getName())
                .getCompletions();
    }
}
