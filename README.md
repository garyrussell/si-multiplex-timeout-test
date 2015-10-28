# si-multiplex-timeout-test
Spring integration 3.0.7 - Test multiplex timeout 

Took multiplex example directly from https://github.com/spring-projects/spring-integration-samples. 



Modifications:

1.) Then modified the pom file to use SI 3.0.7.RELEASE and Spring test 3.2.7.RELEASE

2.) Modified EchoService to sleep for 15 seconds if the string "TIMEOUT_TEST" is passed

3.) Created a unit test to expect the MessageTimeoutException. 



Result:

When timeout unit test is run, it never returnes.
