package io.siddhi.langserver.completion;

import io.siddhi.langserver.DocumentManagerImpl;
import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code CompletionUtil} Provide utilities for completion providers.
 */
public class CompletionUtil {
   public static List<CompletionItem> getCompletions(CompletionParams completionParams) throws URISyntaxException {

       /** resolve the position*/
       Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
       String sourceContent = DocumentManagerImpl.getInstance().getFileContent(path);
       Position cursorPosition = completionParams.getPosition();
       List<CompletionItem> completionItems = new ArrayList<>();

       /** retrieve the completions from siddhi completion engine */
       LSContext.INSTANCE.setPosition(cursorPosition.getLine() + 1, cursorPosition.getCharacter());
       LSContext.INSTANCE.setSourceContent(sourceContent);
       ContextTreeGenerator.INSTANCE.generateContextTree();
       Map<Class, LSCompletionProvider> providers = LSContext.INSTANCE.FACTORY.getProviders();
       LSCompletionProvider contextProvider = providers.get(LSContext.INSTANCE.getCurrentContext().getClass());
       if (contextProvider != null) {
           //completionItems = contextProvider.getCompletions(LSContext.INSTANCE);
           CompletionItem completionItem = new CompletionItem();
           completionItem.setInsertText("test");
           completionItem.setLabel("testlabel");
           completionItem.setKind(CompletionItemKind.Snippet);
           completionItem.setDetail("testsugession");
           completionItem.setFilterText("test");
           completionItems.add(completionItem);
       }
       return completionItems;
   }
}

















   /**private static final String LINE_SEPARATOR=System.lineSeparator();
    public static ModifiedContent getModifiedContent(CompletionParams completionParams) throws IOException,
            URISyntaxException {
        /** current cursor position
        Position position = completionParams.getPosition();
        /** current line
        int cursorLine = position.getLine();
        /**get the document uri
        Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
        /** save original content
        String originalContent = DocumentManagerImpl.getInstance().getFileContent(path);
        /** split the document content into separate lines
        String[] lines = originalContent.split("\\r?\\n");
        lines[cursorLine] = lines[cursorLine].replaceAll("\\S", "");

        /**new position after removing the new line chars
        Position modifiedPosition = new Position(position.getLine()+1,position.getCharacter());
        return new ModifiedContent(originalContent, modifiedPosition);
      private static class ModifiedContent {
    private String content;
    private Position position;

    private ModifiedContent(String content, Position position) {
    this.content = content;
    this.position = position;
    }

    String getContent() {
    return content;
    }

    Position getPosition() {
    return position;
    }
    }
    }*/
