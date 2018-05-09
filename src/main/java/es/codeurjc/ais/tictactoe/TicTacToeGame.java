package es.codeurjc.ais.tictactoe;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicTacToeGame {

	public enum EventType {
		JOIN_GAME, GAME_READY, MARK, SET_TURN, GAME_OVER, RESTART, RECONNECT
	}

	public static class Cell {
		public volatile boolean active = false;
		public volatile String value;
	}

	public static class WinnerResult {
		public boolean win;
		public int[] pos;
	}

	static class CellMarkedValue implements Serializable {
		int cellId;
		Player player;
	}

	public static class WinnerValue implements Serializable {
		public Player player;
		public int[] pos;
	}

	static class Event implements Serializable {
		EventType type;
		Object data;
	}

	private List<Connection> connections = new CopyOnWriteArrayList<>();
	private List<Player> players = new CopyOnWriteArrayList<>();
	private Board board = new Board();

	private int currentTurn = 0;
	private boolean ready = false;

	public void disableAll() {
		board.disableAll();
	}

	public void enableAll() {
		board.enableAll();
	}

	public boolean mark(int cellId) {

		Cell cell = this.board.getCell(cellId);

		if (cell == null) {
			return false;
		}

		if (this.ready && cell.active) {

			Player player = this.players.get(this.currentTurn);

			cell.value = player.getLabel();
			cell.active = false;

			CellMarkedValue value = new CellMarkedValue();
			value.cellId = cellId;
			value.player = player;
			//System.out.println("Event Mark: Player Name " + value.player.getName() + " and cell: " + value.cellId);
			this.sendEvent(EventType.MARK, value);

			WinnerResult res = this.checkWinner();

			if (res.win) {

				this.disableAll();

				WinnerValue winner = new WinnerValue();
				winner.player = this.players.get(this.currentTurn);
				winner.pos = res.pos;
				
			    /*int [] winPos = winner.pos;

			    if (winPos != null)
			    {
			    	for (int winPosition : winPos)
			    	{
			    	   System.out.println(winPosition);
			    	}
			    }
				System.out.println("The winner is player: " + winner.player.getName());*/
				this.sendEvent(EventType.GAME_OVER, winner);

			} else if (this.checkDraw()) {

				//System.out.println("Sent GAME_OVER null");
				this.sendEvent(EventType.GAME_OVER, null);

			} else {
				changeTurn();
			}
		}

		return true;
	}

	private void changeTurn() {
		this.currentTurn = (this.currentTurn + 1) % 2;
		//System.out.println("Change turn to " + this.players.get(this.currentTurn).getName());
		this.sendEvent(EventType.SET_TURN, this.players.get(this.currentTurn));
	}

	public boolean checkTurn(int playerId) {
		return this.players.get(this.currentTurn).getId() == playerId;
	}

	public WinnerResult checkWinner() {
		
		Player player = this.players.get(this.currentTurn);

		int[] winPos = board.getCellsIfWinner(player.getLabel());
		
		WinnerResult result = new WinnerResult();
		result.win = (winPos != null);
		result.pos = winPos;
		
		return result;
	}

	public boolean checkDraw() {

		return board.checkDraw();
	}

	public void addPlayer(Player player) {

		if (this.players.size() < 2) {

			if (this.players.isEmpty() || players.get(0).getId() != player.getId()) {

				this.players.add(player);
				this.ready = this.players.size() == 2;
				
				this.sendEvent(EventType.JOIN_GAME, players);

				if (this.ready) {
					this.enableAll();
					this.sendEvent(EventType.SET_TURN, this.players.get(this.currentTurn));
				}
			}
		}
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void addConnection(Connection connection) {
		this.connections.add(connection);
	}
	
	public void restart() {

		board = new Board();

		sendEvent(EventType.RESTART, null);

		changeTurn();
	}

	private void sendEvent(EventType type, Object value) {

		for(Connection c : connections) {
			c.sendEvent(type, value);
		}
	}
	
}
