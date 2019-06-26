package it.polito.tdp.extflightdelays.model;

public class Ordinabili implements Comparable<Ordinabili>{

	private Airport airport;
	private double weight;
	
	@Override
	public int compareTo(Ordinabili arg0) {
		return -(int)(weight-arg0.weight);
	}

	public Ordinabili(Airport airport, double weight) {
		this.airport = airport;
		this.weight = weight;
	}

	public Airport getAirport() {
		return airport;
	}

	public double getWeight() {
		return weight;
	}
	
	
	
	
	
}
