Feature: test feature

  @test1
  Scenario Outline: test 1
    Given step 1 test 1 feature
    When step 2 test 1 feature
      | username   | password   | email   |tranferValue|
      | <username> | <password> | <email> |<tranferValue>|
    Then step 3 test 1 feature

    Examples:
      | username | password | email |tranferValue|
    ##@externaldata@./src/test/resources/dataEntry.xlsx@test1

  @test2
  Scenario Outline: test 2
    Given step 1 test 1 feature
    When step 2 test 1 feature
      | username   | password   | email   |
      | <username> | <password> | <email> |
    Then step 3 test 1 feature

    Examples:
      | username | password | email |
    ##@externaldata@./src/test/resources/dataEntry.xlsx@test2
