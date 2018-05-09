package es.codeurjc.ais.tictatoe;

import org.junit.Test;
import es.codeurjc.ais.tictactoe.Board;
import es.codeurjc.ais.tictactoe.TicTacToeGame.Cell;

import static org.junit.Assert.*;

import org.junit.Before;

public class BoardTest {

  Board board;
  
  @Before
  public void setUp() 
  {
	this.board = new Board();
	this.board.enableAll();
  }
  
  @Test
  public void testGetCellsIsWinner_when_playerX_starts_and_wins() {

    // Given  
	//The board should be equal to
	// | X | X | X |
	// | O | O | _ |
	// | _ | _ | _ |
	for (int x=0; x<5; x++)
	{
	    Cell cell = this.board.getCell(x);

		if (cell == null) {
			assertTrue("cell is null", cell == null);
		}

		if (cell.active) 
		{
			if (x == 0 || x == 1 || x == 2)
			{
				cell.value = "X";
			}
			else
			{
				cell.value = "O";
			}
			cell.active = false;
		}
	}
	
	assertFalse("Check draw: ", board.checkDraw());
    assertArrayEquals(new int[]{0,1,2}, board.getCellsIfWinner("X"));
    assertNull("PlayerO loses", board.getCellsIfWinner("O"));
  }
  
  @Test
  public void testGetCellsIsWinner_when_playerO_starts_and_wins() {

    // Given  
	//The board should be equal to
	// | O | O | O |
	// | X | X | _ |
	// | _ | _ | _ |
	for (int x=0; x<5; x++)
	{
	    Cell cell = this.board.getCell(x);

		if (cell == null) {
			assertTrue("cell is null", cell == null);
		}

		if (cell.active) 
		{
			if (x == 0 || x == 1 || x == 2)
			{
				cell.value = "O";
			}
			else
			{
				cell.value = "X";
			}
			cell.active = false;
		}
	}
	
	assertFalse("Check draw: ", board.checkDraw());
    assertArrayEquals(new int[]{0,1,2}, board.getCellsIfWinner("O"));
    assertNull("PlayerO loses", board.getCellsIfWinner("X"));
  }
  
  @Test
  public void testRealDraw() {

    // Given 
	//The board should be equal to
	// | X | X | O |
	// | O | O | X |
	// | X | O | X |
	for (int x=0; x<9; x++)
	{
	    Cell cell = this.board.getCell(x);

		if (cell == null) {
			assertTrue("cell is null", cell == null);
		}

		if (cell.active) 
		{
			if (x == 0 || x == 1 || x == 5 || x == 6 || x == 8)
			{
				cell.value = "X";
			}
			else
			{
				cell.value = "O";
			}
			cell.active = false;
		}
	}
    
    assertTrue("Check draw", board.checkDraw());
    assertNull("PlayerX draw", board.getCellsIfWinner("X")); 
    assertNull("PlayerO draw", board.getCellsIfWinner("O"));
  }
	
  @Test
  public void testDraw_Even_Player1_Odd_Player2_All_Values() {

    // Given
	//The board should be equal to
	// | X | O | X |
	// | O | X | O |
	// | X | O | X |
	for (int x=0; x<9; x++)
	{
	    Cell cell = this.board.getCell(x);

		if (cell == null) {
			assertTrue("cell is null", cell == null);
		}

		if (cell.active) 
		{
			if (x%2 == 0)	
			{
				cell.value = "X";
			}
			else
			{
				cell.value = "O";
			}
			cell.active = false;
		}
	}

    assertTrue("Check draw: ", board.checkDraw());
    assertArrayEquals(new int[]{0,4,8}, board.getCellsIfWinner("X"));
    assertNull("PlayerO draw", board.getCellsIfWinner("O")); 
  }
  
  @Test
  public void testDraw_when_any_player_win_and_not_all_cells_with_value() {

    // Given 
	//The board should be equal to
	// | X | O | X |
	// | _ | _ | _ |
	// | _ | _ | _ |
	for (int x=0; x<3; x++)
	{
	    Cell cell = this.board.getCell(x);

		if (cell == null) {
			assertTrue("cell is null", cell == null);
		}

		if (cell.active) 
		{
			if (x == 0 || x == 2)
			{
				cell.value = "X";
			}
			else
			{
				cell.value = "O";
			}
			cell.active = false;
		}
	}

    assertFalse("Check draw when any player win and not all cells have value", board.checkDraw());
    assertNull("PlayerX draw", board.getCellsIfWinner("X")); 
    assertNull("PlayerO draw", board.getCellsIfWinner("O"));
  }
}