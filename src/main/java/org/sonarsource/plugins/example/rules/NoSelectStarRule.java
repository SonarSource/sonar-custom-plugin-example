package org.sonarsource.plugins.example.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashSet;
import java.util.Set;

@Rule(
        key = "NoSelectStarRule",
        name = "SQL queries should not use 'SELECT *'",
        description = "Using 'SELECT *' in SQL queries can lead to performance issues and is considered a bad practice. " +
                "Always specify the columns you need explicitly.",
        priority = org.sonar.check.Priority.MAJOR,
        tags = {"performance", "sql", "convention"}
)
public class NoSelectStarRule extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    // Track already reported issues to avoid duplicates
    private Set<Tree> reportedTrees = new HashSet<>();

    // Regex pattern to identify "SELECT *" statements in SQL queries
    // This pattern matches both case-insensitive "SELECT *" and accounts for whitespace and newlines
    private static final Pattern SELECT_STAR_PATTERN =
            Pattern.compile("(?i)\\bSELECT\\s+\\*\\s+FROM\\b");

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        reportedTrees.clear(); // Clear the set for each new file
        scan(context.getTree());
    }

    @Override
    public void visitLiteral(LiteralTree tree) {
        // Check string literals for SQL statements with "SELECT *"
        if (tree.is(Tree.Kind.STRING_LITERAL)) {
            String value = tree.value();
            checkSelectStar(value, tree);
        }
        super.visitLiteral(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        // Check variable initializers that might contain SQL statements
        ExpressionTree initializer = tree.initializer();
        if (initializer != null) {
            if (initializer.is(Tree.Kind.STRING_LITERAL)) {
                // This will be handled by visitLiteral
            } else if (initializer.is(Tree.Kind.PLUS)) {
                // Handle string concatenations
                String concatenatedString = tryToConcatenateString((BinaryExpressionTree) initializer);
                if (concatenatedString != null) {
                    checkSelectStar(concatenatedString, initializer);
                }
            }
        }
        super.visitVariable(tree);
    }

    @Override
    public void visitAssignmentExpression(AssignmentExpressionTree tree) {
        // Check assignments that might contain SQL statements
        ExpressionTree expression = tree.expression();
        if (expression != null) {
            if (expression.is(Tree.Kind.STRING_LITERAL)) {
                // This will be handled by visitLiteral
            } else if (expression.is(Tree.Kind.PLUS)) {
                // Handle string concatenations
                String concatenatedString = tryToConcatenateString((BinaryExpressionTree) expression);
                if (concatenatedString != null) {
                    checkSelectStar(concatenatedString, expression);
                }
            }
        }
        super.visitAssignmentExpression(tree);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        // Check method arguments that might be SQL statements
        for (ExpressionTree argument : tree.arguments()) {
            if (argument.is(Tree.Kind.STRING_LITERAL)) {
                // This will be handled by visitLiteral
            } else if (argument.is(Tree.Kind.PLUS)) {
                // Handle string concatenations
                String concatenatedString = tryToConcatenateString((BinaryExpressionTree) argument);
                if (concatenatedString != null) {
                    checkSelectStar(concatenatedString, argument);
                }
            }
        }
        super.visitMethodInvocation(tree);
    }

    @Override
    public void visitBinaryExpression(BinaryExpressionTree tree) {
        // String concatenation could be part of an expression
        if (tree.is(Tree.Kind.PLUS)) {
            String concatenatedString = tryToConcatenateString(tree);
            if (concatenatedString != null) {
                checkSelectStar(concatenatedString, tree);
            }
        }
        super.visitBinaryExpression(tree);
    }

    /**
     * Try to concatenate a string from a binary expression tree (string concatenation).
     * Returns null if not all parts are string literals.
     */
    private String tryToConcatenateString(BinaryExpressionTree tree) {
        if (!tree.is(Tree.Kind.PLUS)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean allPartsAreStrings = collectStringParts(tree, sb);

        if (allPartsAreStrings) {
            return sb.toString();
        }
        return null;
    }

    /**
     * Recursively collect all string parts from a concatenation expression.
     * Returns true if all parts were string literals, false otherwise.
     */
    private boolean collectStringParts(ExpressionTree expression, StringBuilder sb) {
        if (expression.is(Tree.Kind.STRING_LITERAL)) {
            LiteralTree literal = (LiteralTree) expression;
            String value = literal.value();
            // Remove quotes
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            sb.append(value);
            return true;
        } else if (expression.is(Tree.Kind.PLUS)) {
            BinaryExpressionTree binary = (BinaryExpressionTree) expression;
            boolean leftIsString = collectStringParts(binary.leftOperand(), sb);
            boolean rightIsString = collectStringParts(binary.rightOperand(), sb);
            return leftIsString && rightIsString;
        }
        return false;
    }

    private void checkSelectStar(String value, Tree tree) {
        // Skip if we've already reported this tree
        if (reportedTrees.contains(tree)) {
            return;
        }

        // Remove escape characters and quotes from the string
        String cleanValue = value.replace("\\\"", "\"")
                .replace("\\\'", "\'")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");

        // Remove the enclosing quotes if present
        if (cleanValue.startsWith("\"") && cleanValue.endsWith("\"")) {
            cleanValue = cleanValue.substring(1, cleanValue.length() - 1);
        }

        // Check if the string contains "SELECT *"
        Matcher matcher = SELECT_STAR_PATTERN.matcher(cleanValue);
        if (matcher.find()) {
            context.reportIssue(this, tree, "Avoid using 'SELECT *' in SQL queries. Specify the required columns explicitly.");
            reportedTrees.add(tree); // Mark this tree as reported
        }
    }
}
