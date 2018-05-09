Feature: Winner is a player who first mark cells on board in a horizontal, vertical or diagonal position.

  Scenario: The winner is user who marked column, vertical or diagonal with the same symbols. In this case the winner started the movements.
    Given a game with Player O and Player X with an empty board
    When player <name_of_player> made a moves to cell number <number_of_cell>
    Then player O is the winner    
    Examples:
      |number_of_cell|name_of_player|
      |      0       |   Player O   |
      |      4       |   Player X   |
      |      3       |   Player O   |
      |      2       |   Player X   |
      |      6       |   Player O   |
    #The board should be equal to
    #  | O | _ | X |
    #  | O | X | _ |
    #  | O | _ | _ |

  Scenario: The winner is user who marked column, vertical or diagonal with the same symbols. In this case the winner did not start the movements.
    Given a game with Player O and Player X with an empty board
    When player <name_of_player> made a moves to cell number <number_of_cell>
    Then Player X is the winner
    Examples:
      |number_of_cell|name_of_player|
      |      2       |   Player O   |
      |      0       |   Player X   |
      |      4       |   Player O   |
      |      3       |   Player X   |
      |      8       |   Player O   |
      |      6       |   Player X   |
    
    #The board should be equal to
    #  | X | _ | O |
    #  | X | O | _ |
    #  | X | _ | O |

    
  Scenario: There is no winner, they draw. There is no user who marked column, vertical or diagonal with the same symbol.
    Given a game with Player O and Player X with an empty board
    When player <name_of_player> made a moves to cell number <number_of_cell>
    Then the result is draw
    Examples:
      |number_of_cell|name_of_player|
      |      2       |   Player O   |
      |      0       |   Player X   |
      |      3       |   Player O   |
      |      1       |   Player X   |
      |      4       |   Player O   |
      |      5       |   Player X   |
      |      7       |   Player O   |
      |      6       |   Player X   |
      |      8       |   Player O   |
    
    #The board should be equal to
    # | X | X | O |
    # | O | O | X |
    # | X | O | O |