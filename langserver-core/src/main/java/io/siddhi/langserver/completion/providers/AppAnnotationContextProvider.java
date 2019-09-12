package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.SnippetProvider;
import io.siddhi.langserver.completion.snippet.SnippetGenerator;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import io.siddhi.query.compiler.SiddhiCompiler;
import io.siddhi.query.compiler.SiddhiQLLexer;
import io.siddhi.query.compiler.SiddhiQLParser;

import java.util.ArrayList;
import java.util.List;

public class AppAnnotationContextProvider extends LSCompletionProvider {
    public AppAnnotationContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.App_annotationContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
         SnippetProvider sinppetProvider=new SnippetProvider();
         return (ArrayList)sinppetProvider.getSnippets((SiddhiQLParser.App_annotationContext) lsContext.getCurrentContext(),lsContext);
    }


}
