package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class TriggerNameContextProvider extends LSCompletionProvider {
    public TriggerNameContextProvider() {
        this.attachmentContext = SiddhiQLParser.Trigger_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        return null;
    }

    public List<CompletionItem> getCompletions(LSOperationContext context){
        List<CompletionItem> completionItems=new ArrayList<>();
        return completionItems;
    }
}
