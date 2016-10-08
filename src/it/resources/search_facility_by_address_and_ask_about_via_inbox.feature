Feature: I want to be able login as parent and find facility near my address.

    Scenario: Log in as admin and verify admin functionality
        When open home page
        When login with login 'fosterparent' and password 'parent'
        When open facilities page
        When search '721 Parkwood Drive, Long Beach, MS, USA' in address search in modal window and apply
        Then verify facility with address '214 Saint Augustine Drive' and name 'Barrington Extend A Care' presents in the list
        When search '176 Reed Avenue, Biloxi, MS, USA' in facility address search
        Then verify facility with address '696 Drive Martin Luther King Junior Boulevard' and name 'Celestine M Coone' presents in the list
        When do Ask About for facility with address '696 Drive Martin Luther King Junior Boulevard' and name 'Celestine M Coone' and send letter
        When open inbox page
        When verify letter to 'MSAP Support' with subject 'Celestine M Coone' and text 'I am interested in more information about' is sent



