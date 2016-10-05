Feature: I want to be able to put city or zip code in address lookup field and hit enter to receive information.

    Scenario: Log in as admin and verify admin functionality
        When open home page
        When login with login 'parent' and password 'parent'
        When open facilities page
        When search '721 Parkwood Drive, Long Beach, MS, USA' in address search in modal window and apply
        Then verify facility with address '214 Saint Augustine Drive' and name 'Barrington Extend A Care' presents in the list
        When search '118 Forest Drive, D'Iberville, MS, USA' in facility address search
        Then verify facility with address '4540 Brodie Road' and name 'Bellaire YMCA' presents in the list
        When do Ask About for facility with address '4540 Brodie Road' and name 'Bellaire YMCA' and send letter
        When open facilities page
        When open inbox page
        When verify letter to 'CHHS Support' with subject 'Bellaire YMCA' and text 'I am interested in more information about' is sent



