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

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide completions for AttributeNameContext {@link io.siddhi.query.compiler.SiddhiQLParser.Attribute_nameContext}.
 */
public class AttributeNameContextProvider extends CompletionProvider {

    public AttributeNameContextProvider() {
        this.providerName = SiddhiQLParser.Attribute_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        ParserRuleContext parent = getParent();
        //AttributeName can be a reference
        if (parent instanceof SiddhiQLParser.Attribute_referenceContext) {
            return generateCompletionList(null);
        }
        //AttributeName can be an output attribute
        else if (parent instanceof SiddhiQLParser.Output_attributeContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlockUtil.ALIAS_SNIPPET);
            return generateCompletionList(suggestions);
        } else {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlockUtil.ATTRIBUTE_NAME_AND_TYPE_SNIPPET);
            return generateCompletionList(suggestions);
        }
    }

}