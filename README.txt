This repository consists of a number of subprojects.

1. mybatis-no-guice
   The original code base was built up from http://www.sivalabs.in/2012/10/mybatis-tutorial-part1-crud-operations.html
   And modified to match the latest patterns etc. used by the MyBatis people.
   And it was changed from using a local MySQL DB to using the Heroku Postgres DB pointed to by DATABASE_URL (or contained in jdbc.properties).

2. mybatis-guice
   The original code base was cut from https://github.com/mybatis/guice/tree/master/src/test/java/org/mybatis/guice/sample
   The code has been separated out into main and test and things simplified noticeably.
   Unlike the other projects this has no main runnable, just tests and these use an in memory HSQLDB DB.

3. jetty-jdbc
   The original code comes from https://github.com/heroku/java-getting-started
   Modified to pick up settings for JDBC connection details for Heroku Postgres BD from DATABASE_URL.
