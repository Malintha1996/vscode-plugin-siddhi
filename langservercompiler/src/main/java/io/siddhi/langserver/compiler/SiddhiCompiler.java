
/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.langserver.compiler;

import io.siddhi.langserver.compiler.exception.SiddhiParserException;
import io.siddhi.langserver.compiler.internal.LanguageServerParserErrorStrategy;
import io.siddhi.langserver.compiler.internal.SiddhiErrorListener;
import io.siddhi.langserver.compiler.internal.SiddhiQLBaseVisitorImpl;
import io.siddhi.query.api.definition.AggregationDefinition;
import io.siddhi.query.api.definition.FunctionDefinition;
import io.siddhi.query.api.definition.StreamDefinition;
import io.siddhi.query.api.definition.TableDefinition;
import io.siddhi.query.api.execution.partition.Partition;
import io.siddhi.query.api.execution.query.Query;
import io.siddhi.query.api.execution.query.StoreQuery;
import io.siddhi.query.api.expression.constant.TimeConstant;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Siddhi Language Server compiler.
 */
public class SiddhiCompiler {

    public static Object parse(String source) {

        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.setErrorHandler(new LanguageServerParserErrorStrategy());
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        try {
            SiddhiQLParser.ParseContext tree = parser.parse();
            return tree;
        } catch (SiddhiParserException error) {
           if (((LanguageServerParserErrorStrategy) parser.getErrorHandler()).triggered) {
               return ((LanguageServerParserErrorStrategy) parser.getErrorHandler()).contextTree;
           } else {
               return error;
           }
        }
    }

    public static StreamDefinition parseStreamDefinition(String source) {

        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.definition_stream_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (StreamDefinition) eval.visit(tree);
    }

    public static TableDefinition parseTableDefinition(String source) throws SiddhiParserException {

        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.definition_table_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (TableDefinition) eval.visit(tree);
    }

    public static AggregationDefinition parseAggregationDefinition(String source) throws SiddhiParserException {

        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.definition_aggregation_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (AggregationDefinition) eval.visit(tree);
    }

    public static Partition parsePartition(String source) throws SiddhiParserException {

        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.partition_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (Partition) eval.visit(tree);
    }

    public static Query parseQuery(String source) throws SiddhiParserException {

        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.query_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (Query) eval.visit(tree);
    }

    public static FunctionDefinition parseFunctionDefinition(String source) throws SiddhiParserException {
        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.definition_function_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (FunctionDefinition) eval.visit(tree);
    }

    public static TimeConstant parseTimeConstantDefinition(String source) throws SiddhiParserException {
        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.time_value();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (TimeConstant) eval.visit(tree);
    }

    public static StoreQuery parseStoreQuery(String storeQuery) throws SiddhiParserException {

        ANTLRInputStream input = new ANTLRInputStream(storeQuery);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        ParseTree tree = parser.store_query_final();

        SiddhiQLVisitor eval = new SiddhiQLBaseVisitorImpl();
        return (StoreQuery) eval.visit(tree);
    }

    public static String updateVariables(String siddhiApp) {
        String updatedSiddhiApp = siddhiApp;
        if (siddhiApp.contains("$")) {
            Pattern variablePattern = Pattern.compile("\\$\\{(\\w+)\\}");
            Matcher variableMatcher = variablePattern.matcher(siddhiApp);
            while (variableMatcher.find()) {
                String key = variableMatcher.group(1);
                String value = System.getProperty(key);
                if (value == null) {
                    value = System.getenv(key);
                    if (value == null) {
                        Pattern appNamePattern = Pattern.compile("@app:name\\(\\W*('|\")(\\w+)('|\")\\W*\\)");
                        Matcher appNameMatcher = appNamePattern.matcher(siddhiApp);
                        if (appNameMatcher.find()) {
                            String appName = appNameMatcher.group(2);
                            throw new SiddhiParserException("No system or environmental variable found for '${"
                                    + key + "}', for Siddhi App '" + appName + "'");
                        } else {
                            throw new SiddhiParserException("No system or environmental variable found for '${"
                                    + key + "}'");
                        }
                    }
                }
                updatedSiddhiApp = updatedSiddhiApp.replaceAll("\\$\\{(" + key + ")\\}", value);
            }
        }
        return updatedSiddhiApp;
    }
}
