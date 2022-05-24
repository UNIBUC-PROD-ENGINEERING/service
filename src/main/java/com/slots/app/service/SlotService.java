package com.slots.app.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.slots.app.model.WinningCombination;

@Service
public class SlotService {
	private final List<String> symbols = Stream.of("*", "7", "_", "+", "/").collect(Collectors.toList());

	// public String generateRandomSymbol() {
		//return symbols.get(new Random().nextInt(symbols.size() - 1));
	//}

	public List<WinningCombination> getWinningCombinations() {
		return Stream.of(
				new WinningCombination(1.0, "*", "7", "_"),
				new WinningCombination(5.0, "_", "_", "7"),
				new WinningCombination(10.0, "7", "7", "7"),
				new WinningCombination(10.0, "+", "+", "+"),
				new WinningCombination(10.0, "_", "_", "_"),
				new WinningCombination(10.0, "/", "/", "/")
		).collect(Collectors.toList());
	}
}
