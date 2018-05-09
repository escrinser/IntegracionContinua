package es.codeurjc.ais.tictatoe;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import es.codeurjc.ais.tictactoe.Connection;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;


public class TicTacToeGameTest {

  TicTacToeGame tictactoegame;
  Connection connection1;
  Connection connection2;
  
  Player playerX;
  Player playerO;
	
  @Before
  public void setUp() 
  {
	this.tictactoegame = new TicTacToeGame();
	
	connection1 = mock(Connection.class);
	connection2 = mock(Connection.class);
	
	tictactoegame.addConnection(connection1);
	tictactoegame.addConnection(connection2);

	playerX = new Player(1, "X", "Sergio");
	playerO = new Player(2, "O", "Jose");
		
	tictactoegame.addPlayer(playerX);
	tictactoegame.addPlayer(playerO);
	
	verify(connection1, times(2)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(playerX, playerO)));
    verify(connection2, times(2)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(playerX, playerO)));
  }
  
  @After
  public void tearDown() 
  {
    reset(connection1);
    reset(connection2);
  }
  
  @Test
  public void testTicTacToeGame_PlayerX_starts_and_wins() {

    // Given  
	//The board should be equal to
	// | X | O | X |
	// | O | X | O |
	// | X | _ | _ |
    for (int i=0; i<7; i++)
    {
    	reset(connection1);
    	if (i%2 == 0)
    	{
    		tictactoegame.checkTurn(playerX.getId());
    		tictactoegame.mark(i);
    		if (i != 6)
    		{
    			verify(connection1).sendEvent(EventType.SET_TURN, playerO);
    		}
    	}
    	else
    	{
    		tictactoegame.checkTurn(playerO.getId());
    		tictactoegame.mark(i);
    		verify(connection1).sendEvent(EventType.SET_TURN, playerX);
    	}    	
    }
    
    ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
    verify(connection1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	WinnerValue winner = argument.getValue();
	
    assertTrue("The winner is Player X.", winner.player.getName() == playerX.getName());
    assertArrayEquals(new int[]{6,4,2}, winner.pos);
    assertFalse("Check draw", tictactoegame.checkDraw());
  }
  

  @Test
  public void testTicTacToeGame_PlayerX_starts_and_loses() {

    // Given  
	//The board should be equal to
	// | X | _ | O |
	// | X | O | X |
	// | O | _ | _ |
    for (int i=0; i<7; i++)
    {
    	reset(connection1);
    	if (i == 0)
    	{
    		if(tictactoegame.checkTurn(playerX.getId()))
    		{
    		   tictactoegame.mark(i);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerO);
    		}
    	}
    	else 
    	{
    		if (i%2 == 0)    		
	    	{
	    		if(tictactoegame.checkTurn(playerO.getId()))
	    		{
	    			tictactoegame.mark(i);
	    			if (i != 6)
	    			{
	    				verify(connection1).sendEvent(EventType.SET_TURN, playerX);
	    			}
	    		}
	    	}
	    	else
	    	{
	    		if (tictactoegame.checkTurn(playerX.getId()))
	    		{
	    			tictactoegame.mark(i);
	    			verify(connection1).sendEvent(EventType.SET_TURN, playerO);
	    		}
	    	}   
    	}    	
    }
    
    ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
    verify(connection1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	WinnerValue winner = argument.getValue();
	
    assertTrue("The winner is Player O.", winner.player.getName() == playerO.getName());
    assertArrayEquals(new int[]{6,4,2}, winner.pos);
    assertFalse("Check draw", tictactoegame.checkDraw());
  }
  
  @Test
  public void testTicTacToeGame_Draw() {

    // Given  
	//The board should be equal to
	// | X | X | O |
	// | O | O | X |
	// | X | O | X |
    for (int i=0; i<9; i++)
    {
        reset(connection1);
    	if (i == 0 )
    	{
    		if(tictactoegame.checkTurn(playerX.getId()))
    		{
    		   tictactoegame.mark(i);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerO);
    		}
    	}
    	else if (i == 1)
    	{
    		if(tictactoegame.checkTurn(playerO.getId()))
    		{
    		   tictactoegame.mark(i + 1);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerX);
    		}
    	}
    	else if (i == 2)
    	{
    		if(tictactoegame.checkTurn(playerX.getId()))
    		{
    		   tictactoegame.mark(i - 1);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerO);
    		}
    	}
    	else if (i == 3)
    	{
    		if(tictactoegame.checkTurn(playerO.getId()))
    		{
    		   tictactoegame.mark(i);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerX);
    		}
    	}
    	else if (i == 4)
    	{
    		if(tictactoegame.checkTurn(playerX.getId()))
    		{
    		   tictactoegame.mark(i + 1);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerO);
    		}
    	}
    	else if (i == 5)
    	{
    		if(tictactoegame.checkTurn(playerO.getId()))
    		{
    		   tictactoegame.mark(i - 1);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerX);
    		}
    	}
    	else if (i == 6)
    	{
    		if(tictactoegame.checkTurn(playerX.getId()))
    		{
    		   tictactoegame.mark(i);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerO);
    		}
    	}
    	else if (i == 7)
    	{
    		if(tictactoegame.checkTurn(playerO.getId()))
    		{
    		   tictactoegame.mark(i);
    		   verify(connection1).sendEvent(EventType.SET_TURN, playerX);
    		}
    	}
    	else if (i == 8)
    	{
    		if(tictactoegame.checkTurn(playerX.getId()))
    		{
    		   tictactoegame.mark(i);
    		}
    	}
    }
        
    verify(connection1).sendEvent(eq(EventType.GAME_OVER), eq(null));
    assertTrue("Check draw", tictactoegame.checkDraw());
  }
}