package ro.unibuc.slots.dto;

import ro.unibuc.slots.model.Turn;
import ro.unibuc.slots.model.WinningCombination;

public class TurnDto {
	private Turn turn;
	private WinningCombination winningCombination;
	
	public TurnDto() {}

	public TurnDto(Turn turn, WinningCombination winningCombination) {
		this.turn = turn;
		this.winningCombination = winningCombination;
	}

	public Turn getTurn() {
		return turn;
	}
	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public WinningCombination getWinningCombination() {
		return winningCombination;
	}

	public void setWinningCombination(WinningCombination winningCombination) {
		this.winningCombination = winningCombination;
	}
}
