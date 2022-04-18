@IGNORE
Feature: Get customer profile

  Background:
    * url baseUrl

  @IT08IAW-696-UC01
  Scenario: IT08IAW-696-UC01 - Get customer profile with invalid token
    * print 'BEGIN - IT08IAW-696-UC01 - Get customer profile with invalid token'
    Given path '/client-app/profile'
    And header Authorization = 'Bearer XXX.XX.XX'
    And header Accept = 'application/json'
    And header Accept-Version = "1.0.0"
    When method get
    Then status 401
    * print 'END - IT08IAW-696-UC01 - Get customer profile with invalid token'

  @IT08IAW-696-UC02
  Scenario: IT08IAW-696-UC02 - Get customer profile with en expired token
    * print 'BEGIN - IT08IAW-696-UC02 - Get customer profile with en expired token'
    * def expiredToken = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Im5mVXQ4YVdQaGdWRFl6OGN5ODJzQXQyN0JEVSJ9.eyJhdWQiOiJodHRwczovL2ludC1hcGltLmxwbC1jbG91ZC5jb20vaW50L3dmai9wcm9kdWN0MzYwIiwiaXNzIjoiaHR0cDovL2NpZC5jbGFzcC1pbmZyYS5jb20vYWRmcy9zZXJ2aWNlcy90cnVzdCIsImlhdCI6MTYyMDI4NTYxOCwiZXhwIjoxNjIwMjg5MjE4LCJHcm91cCI6ImRlZmF1bHQiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiJhZGFjNDQyOC01ZmViLTQ2NzEtYWQwOS05ZDM4MmZjMjQ2MTEiLCJhdXRobWV0aG9kIjoidXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6UGFzc3dvcmRQcm90ZWN0ZWRUcmFuc3BvcnQiLCJhdXRoX3RpbWUiOiIyMDIxLTA1LTA2VDA3OjIwOjE4LjIwM1oiLCJ2ZXIiOiIxLjAiLCJzY3AiOiJvcGVuaWQifQ.dzbcZtKPyoRDcyI_SWa8VUbSSsJfZEZ61zQAi_FWpmkQ9Y-MJpUn-lz__p5iEUaYvkPbsyzAdGdhwqtpRGVhsNLsRW0NWtvQZZ2D09vEd4wvrRjn1egKSQnqwuycBOFVSxx5ufNBEXAQdS-hmwpeqMFFxacckYozGtCi9kwIKjwa7O8-49h4JSjA81H_tR4N-LQ_CoPiA7x3iO-0s4t4IDM4BdQnRIb5fgMupa7tvhfQ6jwIxqJH_MdGOy7vQyY4L76XGHEMwylFwBlkKPR5otapLWrAEWa-bhY2F0aI4sWWKIWzW0RGCU4npsLBX999XEgbZHwwsr6r2K-6bL1wXQ'
    Given path '/client-app/profile'
    And header Authorization = 'Bearer ', expiredToken
    And header Accept = 'application/json'
    And header Accept-Version = "1.0.0"
    When method get
    Then status 401
    * print 'END - IT08IAW-696-UC02 - Get customer profile with en expired token'

  @IT08IAW-696-UC03
  Scenario: IT08IAW-696-UC03 - Get customer profile with a bad version
    * print 'BEGIN - IT08IAW-696-UC03 - Get customer profile with a bad version'
    Given path '/client-app/profile'
    And header Authorization = 'Bearer ' + accessToken
    And header Accept = 'application/json'
    And header Accept-Version = "0.0.1"
    When method get
    Then status 501
    * print 'END - IT08IAW-696-UC03 - Get customer profile with a bad version'

  @IT08IAW-696-UC04
  Scenario: IT08IAW-696-UC04 - Get customer profile with Chanel email
    * print 'BEGIN - IT08IAW-696-UC03 - Get customer profile with a Chanel email'
    Given path '/client-app/profile'
    And header Authorization = 'Bearer ' + accessToken
    And header Accept = 'application/json'
    And header Accept-Version = "1.0.0"
    When method get
    Then status 200
    * match $.profileJWT == '#notnull'
    * print 'END - IT08IAW-696-UC04 - Get customer profile with a Chanel email'

  @IT08IAW-696-UC05
  Scenario: IT08IAW-696-UC05 - Get customer profile with generic email
    * print 'BEGIN - IT08IAW-696-UC05 - Get customer profile with a generic email'
    Given path '/client-app/profile'
    And header Authorization = 'Bearer ' + accessToken
    And header Accept = 'application/json'
    And header Accept-Version = "1.0.0"
    When method get
    Then status 200
    * match $.profileJWT == '#notnull'
    * print 'END - IT08IAW-696-UC05 - Get customer profile with a generic email'
