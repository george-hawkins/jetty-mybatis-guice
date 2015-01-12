Each of the subdirectories here contains an individual subproject.

There is no dependency relationship between them and they each have there own completely separate pom.

The subproject look at MyBatis, Guice and Jetty - gradually building up to pulling them all together in the jetty-mybatis-guice subproject.

Most of the projects were based initially on small getting-started projects from tutorials etc.

----

All the subprojects contain NOTES.txt files.

The most interesting one is in jetty-mybatis-guice.

The one in mybatis-guice covers how mappers created using the mybatis-guice logic are different to normal MyBatis mappers in that one can hang onto them.

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
4. jetty-jdbc-guice
   Modification of jetty-jdbc to inject the JDBC connection using Guice.
   Used https://github.com/google/guice/wiki/Servlets and http://stackoverflow.com/questions/8275194/simple-example-with-guice-servlets as guides.
5. jetty-mybatis-guice
   This project pulls everything together, it uses:

   * the mapper, model and service of the mybatis-no-guice subproject.
   * the injector creation logic of the mybatis-guice subproject.
   * the Jetty setup and Guice listener logic of the jetty-jdbc-guice subproject.
