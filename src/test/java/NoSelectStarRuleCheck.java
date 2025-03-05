public class NoSelectStarRuleCheck {

    public void selectStarExamples() {
        // Simple string with SELECT *
        String sql1 = "SELECT * FROM users"; // Noncompliant {{Avoid using 'SELECT *' in SQL queries. Specify the required columns explicitly.}}

        // Mixed case and whitespace
        String sql2 = "select   *    from products"; // Noncompliant

        // Multiline
        String sql3 = "SELECT\n" +  // Noncompliant
                "  *\n" +
                "FROM customers";

        // In variable assignment
        String sql4;
        sql4 = "SELECT * FROM orders"; // Noncompliant

        // In method call
        executeQuery("SELECT * FROM invoices"); // Noncompliant

        // These should NOT trigger the rule (compliant cases)
        String goodSql1 = "SELECT id, name, email FROM users";
        String goodSql2 = "INSERT INTO users VALUES (1, 'John')";
        String goodSql3 = "UPDATE users SET name = 'John' WHERE id = 1";
        String goodSql4 = "DELETE FROM users WHERE id = 1";
        String goodSql5 = "SELECT COUNT(*) FROM users"; // This is acceptable as it's an aggregate function

        // Not SQL at all
        String notSql = "This is not SQL and has a * in it";
    }

    private void executeQuery(String query) {
        // Method implementation
    }
}
