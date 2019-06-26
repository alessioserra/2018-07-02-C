package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.util.WeightCombiner;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	Graph<Airport, DefaultWeightedEdge> grafo;
	List<Airport> vertici = new ArrayList<>();
	ExtFlightDelaysDAO dao;
	Map<Integer, Airport> idMap = new HashMap<>();
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(int x) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungo vertici
		vertici = dao.loadAllAirportsMinAirlines(x,idMap);
		Graphs.addAllVertices(this.grafo, vertici);
		
		//Aggiungo archi
		for (Airport a1 : vertici) {
			for (Airport a2 : vertici) {
						
				if (!a1.equals(a2)) {
				double weight = dao.getWeight(a1, a2);
				if (weight>0) Graphs.addEdge(this.grafo, a1, a2, weight);
				}
			}
		}
		
		
		System.out.println("GRAFO CREATO");
		System.out.println("#NODI: "+this.grafo.vertexSet().size());
		System.out.println("#ARCHI: "+this.grafo.edgeSet().size());
	}
	
	public List<Airport> getAirports(){
		return this.vertici;
	}
	
	public List<Ordinabili> getAdiacenti(Airport a){
		
		List<Airport> result = new ArrayList<>();
		List<Ordinabili> ordinabili = new ArrayList<>();
		
		result =Graphs.neighborListOf(this.grafo, a);
		
		for (Airport a2 : result) {
		
			double weight = this.grafo.getEdgeWeight(this.grafo.getEdge(a, a2));
			
			Ordinabili o = new Ordinabili(a2,weight);
			
			ordinabili.add(o);
			
		}
		
		Collections.sort(ordinabili);
		
		return ordinabili;
	}

}
