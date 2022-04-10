Feature:  Search hotel on Booking.com
  Scenario Outline: User searches for hotel
    Given  User is on main screen
    When User enters the name of the <hotel> in the search field
    And User click "Search" button
    And Searching <hotel> is displayed
    Then  Page shows <hotel> name and its <rating>
    Examples:
      | hotel                   | rating |
      | "Fairmont San Francisco" | "8.3"  |

