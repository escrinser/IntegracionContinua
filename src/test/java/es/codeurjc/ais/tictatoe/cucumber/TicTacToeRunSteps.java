package es.codeurjc.ais.tictatoe.cucumber;

import static org.junit.Assert.assertTrue;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerResult;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

public class TicTacToeRunSteps {
 
	private Player PlayerO;
	private Player PlayerX;

	private TicTacToeGame game;
	
    @Given("^a game with Player O and Player X with an empty board$")
    public void a_game_with_player_o_and_player_x_with_an_empty_board() throws Throwable {
    	
    	game = new TicTacToeGame();
    	
    	PlayerO = new Player(1, "O", "Sergio");
    	PlayerX = new Player(2, "X", "Jose");
    	
    	game.addPlayer(PlayerO);
    	game.addPlayer(PlayerX);
    	
        throw new PendingException();
    }

    @When("^player (.+) made a moves to cell number (.+)$")
    public void player_made_a_moves_to_cell_number(int nameofplayer, int numberofcell) throws Throwable {
		if (game.checkTurn(nameofplayer)) {
			game.mark(numberofcell);
		}
        throw new PendingException();
    }

    @Then("^player O is the winner    $")
    public void player_o_is_the_winner() throws Throwable {
    	WinnerResult res = game.checkWinner();
    	assertTrue(res.win);
    	
		WinnerValue winner = new WinnerValue();
		winner.player = PlayerO;
		winner.pos = res.pos;

        int [] winPos = winner.pos;

        if (winPos != null)
        {
        	for (int winPosition : winPos)
        	{
        	   System.out.println(winPosition);
        	}
        }
    	System.out.println("The winner is player: " + winner.player.getName());
        assertTrue("The winner is Player O.", winner.player.getName() == PlayerO.getName());
        throw new PendingException();
    }

    @Then("^Player X is the winner$")
    public void player_x_is_the_winner() throws Throwable {
    	WinnerResult res = game.checkWinner();
    	assertTrue(res.win);
    	
		WinnerValue winner = new WinnerValue();
		winner.player = PlayerX;
		winner.pos = res.pos;
		
        assertTrue("The winner is Player X.", winner.player.getName() == PlayerX.getName());
        throw new PendingException();
    }

    @Then("^the result is draw$")
    public void the_result_is_draw() throws Throwable {
    	assertTrue("Check draw", game.checkDraw());
        throw new PendingException();
    }
}