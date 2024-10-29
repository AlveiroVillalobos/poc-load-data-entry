@transferTest
Feature: transfer feature

  @transferTest1
  Scenario Outline: transfer test 1
    Given step 1 transfer test 1 feature
    When step 2 transfer test 1 feature
      | username   | password   | email   | transferValue   | bankName   | dateTransfer   |
      | <username> | <password> | <email> | <transferValue> | <bankName> | <dateTransfer> |
    Then step 3 transfer test 1 feature

    Examples:
      | username | password | email | transferValue | bankName | dateTransfer |
    ##@externaldata@./src/test/resources/dataEntry.xlsx@transferTest2
