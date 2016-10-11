Feature: Check twitter widget on landing page

    Scenario: Open home page and check youtube in the widget
        When open home page
        Then tweet with text 'MS_DHS' from 'MLICCI' should be presented

    Scenario: close browser
        When close browser
