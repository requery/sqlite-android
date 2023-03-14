package io.requery.android.database.sqlite;

import org.junit.Assert;
import org.junit.Test;

public class SQLiteStatementTypeTest {

    @Test
    public void recognizesSelectQueryWithComments() {
        String query = "--comment --comment\nSELECT * FROM table WHERE id = 1";
        Assert.assertEquals(SQLiteStatementType.STATEMENT_SELECT, SQLiteStatementType.getSqlStatementType(query));
    }

    @Test
    public void testStripSqlComments() {
        for (TestData test : queriesTestData) {
            String strippedSql = SQLiteStatementType.stripLeadingSqlComments(test.inputQuery);
            Assert.assertEquals("Error in test case\n" + test.inputQuery, test.expectedQuery, strippedSql);
        }
    }

    private static final TestData[] queriesTestData = {
            new TestData("", ""),
            new TestData(" ", ""),
            new TestData("\n", ""),
            new TestData(
                    "\n-- ?1 - version id, required\n-- ?2 - account id, optional\nSELECT\n  SUM(col1 + col2) AS count\nFROM\n  Accounts\nWHERE\n  id = ?1\nAND\n  col3 = 0\nAND\n  CASE WHEN COALESCE(?2, '') = '' THEN 1 ELSE entityId = ?2 END\n",
                    "SELECT\n  SUM(col1 + col2) AS count\nFROM\n  Accounts\nWHERE\n  id = ?1\nAND\n  col3 = 0\nAND\n  CASE WHEN COALESCE(?2, '') = '' THEN 1 ELSE entityId = ?2 END"
            ),
            new TestData(
                    "select * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "select * from employees -- this is a comment",
                    "select * from employees -- this is a comment"
            ),
            new TestData(
                    "select * from employees /* first comment */-- second comment",
                    "select * from employees /* first comment */-- second comment"
            ),
            new TestData(
                    "-- this is a comment\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "-- this is a comment\nselect \"--col\" from employees",
                    "select \"--col\" from employees"
            ),
            new TestData(
                    "-------this is a comment\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "\n-- ?1 first parameter id\n-- ?2 second parameter format \"yyyy-mm-01\"\n-- ?3 third parameter either 'value1' or 'value2'\n\nselect * from employees where param1 = ?1 AND param2 = ?2 AND param3 = ?3",
                    "select * from employees where param1 = ?1 AND param2 = ?2 AND param3 = ?3"
            ),
            new TestData(
                    "/* Single Line Block Comment */ select * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/* Single Line Block Comment */\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/* Multiline Line Block Comment\nHere is another line\nAnd another */\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/*Multiline Line Block Comment\nHere is another line\nAnd another */\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "\nselect * from employees where /* this is param 1 */ param1 = ?1 AND /* this is param 2 */ param2 = ?2 AND /* this is param 3 */ param3 = ?3",
                    "select * from employees where /* this is param 1 */ param1 = ?1 AND /* this is param 2 */ param2 = ?2 AND /* this is param 3 */ param3 = ?3"
            ),
            new TestData(
                    "/* Single Line Block Comment */\n-- another comment\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/* Single Line Block Comment */\n--another comment\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/* Multiline Line Block Comment\nLine 2\nLine 3 */\n-- dashed comment\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/* Multiline Line Block Comment\nLine 2\n-- dashed comment inside block comment\nLine 3 */\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "\nSELECT\n  'All Accounts' AS name,\n  'all-accounts' AS internal_name\nFROM\n  Accounts\nWHERE\n  id = ?1\nAND\n  col3 = 0\n    ",
                    "SELECT\n  'All Accounts' AS name,\n  'all-accounts' AS internal_name\nFROM\n  Accounts\nWHERE\n  id = ?1\nAND\n  col3 = 0"
            ),
            new TestData(
                    "/* Multiline Line Block Comment\nLine 2\nLine 3 */-- single line comment\nselect * from employees",
                    "select * from employees"
            ),
            new TestData(
                    "/* Multiline Line Block Comment\nhttps://foo.bar.com/document/d/283472938749/foo.ts\nLine 3 */-- single line comment\nSELECT\n  'All Accounts' AS name,\n  'all-accounts' AS internal_name\nFROM\n  Accounts\nWHERE\n  id = ?1\nAND\n  col3 = 0\n    ",
                    "SELECT\n  'All Accounts' AS name,\n  'all-accounts' AS internal_name\nFROM\n  Accounts\nWHERE\n  id = ?1\nAND\n  col3 = 0"
            )
    };

    private static class TestData {
        public final String inputQuery;
        public final String expectedQuery;

        public TestData(String inputQuery, String expectedQuery) {
            this.inputQuery = inputQuery;
            this.expectedQuery = expectedQuery;
        }
    }
}