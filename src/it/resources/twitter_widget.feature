Feature: Check twitter widget on landing page

  Scenario: Open home page and check youtube in the widget
    When open home page
    Then tweet with text 'CWDS' from 'ChildWelfareDgtlSvcs' should be presented
