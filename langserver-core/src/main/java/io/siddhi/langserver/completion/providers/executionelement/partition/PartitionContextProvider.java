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

package io.siddhi.langserver.completion.providers.executionelement.partition;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

/**
 * Provides completions for PartitionContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.PartitionContext}.
 */
public class PartitionContextProvider extends CompletionProvider {

    public PartitionContextProvider() {

        this.providerName = SiddhiQLParser.PartitionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        ParserRuleContext partitionContext = (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(
                SiddhiQLParser.PartitionContext.class.getName());
        int childCount = partitionContext.getChildCount();
        List<ParseTree> children = partitionContext.children;
        if (childCount > 1 && children.get(childCount - 1) instanceof TerminalNodeImpl) {
            if ("with".equalsIgnoreCase(children.get(childCount - 1).getText())) {
                return LSCompletionContext.INSTANCE
                        .getProvider(SiddhiQLParser.Partition_with_streamContext.class.getName()).getCompletions();
            } else if (children.get(childCount - 2) instanceof SiddhiQLParser.Partition_with_streamContext) {
                return generateCompletionList(Arrays.asList(SnippetBlockUtil.PARTITION_BLOCK_SNIPPET,
                        SnippetBlockUtil.KEYWORD_BEGIN));
            } else if (children.get(childCount - 1) instanceof TerminalNodeImpl &&
                    "begin".equalsIgnoreCase(children.get(childCount - 1).getText())) {
                List<Object[]> suggestions = Arrays.asList(
                        SnippetBlockUtil.KEYWORD_FROM,
                        SnippetBlockUtil.QUERY_DEFINITION,
                        SnippetBlockUtil.QUERY_FILTER,
                        SnippetBlockUtil.QUERY_JOIN,
                        SnippetBlockUtil.QUERY_PATTERN,
                        SnippetBlockUtil.QUERY_TABLE_JOIN,
                        SnippetBlockUtil.QUERY_WINDOW,
                        SnippetBlockUtil.QUERY_WINDOW_FILTER,
                        SnippetBlockUtil.KEYWORD_END);
                return generateCompletionList(suggestions);
            }
        }
        return generateCompletionList(null);
    }
}
