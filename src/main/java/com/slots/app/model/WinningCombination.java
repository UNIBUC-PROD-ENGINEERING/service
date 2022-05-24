package com.slots.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WinningCombination {
	private final Double ratio;
	private final List<String> elementsInCombination = new ArrayList<>();
	
	public WinningCombination(Double ratio, String... symbols) {
		this.ratio = ratio;
		this.elementsInCombination.addAll(Stream.of(symbols).collect(Collectors.toList()));
	}

	public Double getRatio() {
		return ratio;
	}

	public List<String> getElementsInCombination() {
		return elementsInCombination;
	}

	public boolean applies(List<String> symbols) {
		return symbols.equals(elementsInCombination);
	}

	@Override
	public String toString() {
		return "WinningCombination [ratio=" + ratio + ", elementsInCombination=" + elementsInCombination + "]\n";
	}
}
