Feature: Register new user and log in. Change profile info and password

    Scenario: Prepare test data

    Scenario: Open home page and register new user
        When open home page
        Given random alphabetic name with 'testuser' and length '4' saved to 'testuser' variable
        When register new user with email '${testuser}', login '${testuser}' and password 'password'
        When close browser
        When check confirmation letter for email '${testuser}'

    Scenario: log in and change profile info
        When open home page
        When login with login '${testuser}' and password 'password'
        When search '721 Parkwood Drive, Long Beach, MS, USA' in address search in modal window and apply
        When open my profile
        When fill gender 'Male', DOB mm-dd-yyyy '11'-'22'-'1983', license number '123456' in General Information
        When fill address '1035 West 12th Avenue, Chico, CA, USA', email 'test2@mailinator.com', telephone '5553331111' in Contact Information
        When change old password to the new one 'password2'
        When save changes in profile

    Scenario: log in with new password
        When log out
        When login with login '${testuser}' and password 'password2'
        When search '721 Parkwood Drive, Long Beach, MS, USA' in address search in modal window and apply
        When open my profile
        When log out

    Scenario: close browser
        When close browser
