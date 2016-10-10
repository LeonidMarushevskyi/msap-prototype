Feature: I want to be able to find facility from landing page.

    Scenario: Log in as admin and verify admin functionality
        When open home page
        When search address '721 Parkwood Drive, Long Beach, MS, USA'  with age group '12-24'on landing page
        Then verify facility with address '214 Saint Augustine Drive' and name 'Barrington Extend A Care' presents in the list
