Feature: Add user feature
  Add user

  Scenario: Add new user not possible
    Given there is one user with 'test.test@gmail.com' email address
    When try to count for users with  'test.test@gmail.com' email address
    Then count returns '1'

  Scenario: Add new user possible
    Given there is '0' user with 'a.b@gmail.com' email address
    When try to add new user with  'a.b@gmail.com' email address
    Then new count returns '1'
