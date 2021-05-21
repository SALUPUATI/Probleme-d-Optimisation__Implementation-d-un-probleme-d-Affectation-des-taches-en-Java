package TP;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList; 

public class Graphe {
	
	private int nbvertex;
	protected Noeud [][] noeuds;
	private int nbnoeuds;
	
	public Graphe(Noeud[][] noeud, int nbvertex) {
		
	this.noeuds = noeud;
	this.nbvertex = nbvertex;
	this.nbnoeuds=noeuds.length;
	
	}
	//Verification des Choix des taches dans l'ensemble
	public boolean isNoeud(int i, int j) {		
		
		if(noeuds[i][j].getLabelNoeud() >= 0) {		
			return true;
		}
		return false;
	}
	
	public boolean isExist(int line, Noeud noeud) {		
		for(int i = 0; i < nbvertex; i++) {			
			
			if(noeuds[line][i].getLabelNoeud() == noeud.getLabelNoeud())
			{	
				return true;
			}
		}		
		return false;
	}
	
	public List<Noeud> NoeudVoisin(int index){	
		List<Noeud> edge = new ArrayList<Noeud>();
	      for(int i=0; i < nbvertex ; i++) 
	    	  if(isNoeud(index, i)) 
	    	 edge.add(new Noeud(noeuds[index][i].getLabelNoeud()));    	        
		return edge;
		
	}
	public List<Noeud> index(Noeud noeud){	
		List<Noeud> edge = new ArrayList<Noeud>();	
		for(int i = 0; i < noeuds.length; i++)
			if(isExist(i, noeud))
				edge.add(new Noeud(i));		
		return edge;
	}
	
	/*
	 * Dans la classe Graphe, écrire une méthode
		public LinkedList<Noeud> cheminBFS(Noeud s, Noeud t)
		qui cherche un plus court chemin (en nombre d’arc) du nœud s au nœud t. La
		méthode renvoi null si un tel chemin n’existe pas.
	 */
	public LinkedList<Noeud> cheminBFS(Noeud s, Noeud p){
		 
		boolean NoeudVisite[] = new boolean[noeuds.length];
		int [] positionNoeud= new int[noeuds.length];
		
		//Initialiser tous les Noeuds parcouru a faux
		for (int i = 0; i<NoeudVisite.length; i++) 
		{
			NoeudVisite[i]=false;
		}
		// Nous nous placons a la racine
		NoeudVisite[s.getLabelNoeud()] = true;
		positionNoeud[s.getLabelNoeud()]=0;
		
		Queue<Noeud> queue = new ArrayDeque<Noeud>();
		LinkedList<Noeud> PCC = new LinkedList<Noeud>();
		
		queue.add(s);
		PCC.add(s);
		
		//Parcours en BFS pour atteindre tous les sommets et determiner le PCC
	  while(!queue.isEmpty()) {
		  
		  s = queue.poll();
		  
		  //Nous parcourons les successeurs du Noeuds Actuels
		  for(Noeud id :NoeudVoisin(s.getLabelNoeud())) {
			  
			  if(NoeudVisite[id.getLabelNoeud()] == false) {
				  
				   NoeudVisite[id.getLabelNoeud()]=true;
				   positionNoeud[id.getLabelNoeud()] = positionNoeud[s.getLabelNoeud()];
				   queue.add(id);
				   PCC.add(id);
			  }
		  }
	  }	
		return PCC;	
	}
	
	/*
	 * Dans la classe Graphe, écrire une méthode
	public boolean inverseChemin(LinkedList<Noeud> chemin)
	qui prend en argument un chemin et inverser tous les arcs sur ce chemin.
	 */
	
public boolean inverseChemin(LinkedList<Noeud> chemin) {
	
	 boolean isExist = false;
	 
	 Noeud p = chemin.getLast();
	 
	 boolean visited[] = new boolean[chemin.size()];
	 int positionNoeud[] = new int[chemin.size()];
	 
	 //Initialiser tous les Noeuds a false
	 for(int i=0; i<visited.length; i++) {
		 visited[i]=false;
	 }
	 
	 //On se place aux Noeuds Puits
	 visited[p.getLabelNoeud()] = true;
	 positionNoeud[p.getLabelNoeud()]=0;
	 
	 Queue<Noeud> queue = new ArrayDeque<Noeud>();
	 LinkedList<Noeud> cheminRetour = new LinkedList<Noeud>();
	 
	 queue.add(p);
	 cheminRetour.add(p);
	 
	 //Parcours des Autres Noeuds du GRaphe
	 while(!queue.isEmpty()) {
		 p = queue.poll();
		 
		 //Verifications de voisin du Noeud Actuel
		 for(Noeud id : index(p)) {
			 
			 //Visite des Noeuds non encore visite
			 if(visited[id.getLabelNoeud()] == false) 
			 {		 
				 visited[id.getLabelNoeud()]= true;
				 positionNoeud[id.getLabelNoeud()]= positionNoeud[p.getLabelNoeud()]+1;
				 queue.add(id);
				 cheminRetour.add(id);
			 }
			 /*
			  * La méthode renvoi true si l’oprération complète
                avec succès, false s’il y a un arc du chemin qui n’appartient pas au graphe.
			  */
			 else if(positionNoeud[p.getLabelNoeud()]== positionNoeud[id.getLabelNoeud()]) {
				return false; 
			 }
		 }
	 }
	 
		for(int i=0; i<cheminRetour.size(); i++) 
			if(i == cheminRetour.size() - 1)				
				System.out.print(cheminRetour.get(i).getLabelNoeud());
			else
				System.out.print(cheminRetour.get(i).getLabelNoeud() + " ==>");
				return isExist;
	}

/*
 * QUESTION 1 ET 2 RESOLU
 *  Dans la classe Graphe, écrire une méthode public int ff(Noeud s, Noeud t)
	qui execute une version de l’algorithme Ford
 */


/*
 * Algorithme de Ford Fulkerson
 */
 	public int ff(Noeud s, Noeud t) {
	
	LinkedList<Noeud> Bfs = new LinkedList<Noeud>();
	Bfs = cheminBFS(s, t);
	int flot_max = 0;
	
	List<Noeud> tache = index(Bfs.getLast());
	List<Noeud> personne = NoeudVoisin(Bfs.getFirst().getLabelNoeud());
	
	//Recuperer les successeurs du Noeuds Puits
	int succesurPuit = tache.size();
	
	int[] nbresS = new int[succesurPuit];
	Noeud[] couplage = new Noeud[succesurPuit];
	
	for(int i=0; i<couplage.length; i++) {
		couplage[i]=new Noeud(-1);
	}
	
	for(int i=0; i<succesurPuit; i++) {
		nbresS[i] = index(tache.get(i)).size();
	}
	
	for(int i=0; i<succesurPuit; i++) {
		 int valmin = min_valTable(nbresS);
		 Noeud noeudM = tache.get(valmin);
		 
		 for(Noeud pred : index(noeudM)) {
			 /*
			  * Verification de la loi de Kirkoff
			  * Augmentation des flots
			  */
			if((!verif_affectation(couplage, pred)) && (couplage[valmin]).getLabelNoeud() < 0) {
				
				couplage[valmin]=pred;
				flot_max+=1;
			} 
		 }
		 nbresS[valmin] = succesurPuit;
	}
	System.out.println("------------------------------------------------------------");
	System.out.println("| Flot Maximal egal a : " + flot_max + " \t\t\t\t\t\t|");
	System.out.println("------------------------------------------------------------");
	
	for(int i=0; i< couplage.length; i++) {
	int num = i+1;
	System.out.println("|*| -- Personne numero" +" "+ PositionNoeud(personne, couplage[i]) + " "+"=========>"+" " + "Tache Numero"+ " "+num + " "+ "|*|" );
	}
	
	return succesurPuit;	
	}

 	/*
 	 * Methode pour afficher Le Graphe
 	 */
 	public void afficherGraphe() {	
 		for(int i=0; i<noeuds.length; i++) {
 			 
 			List<Noeud> no = NoeudVoisin(i);
 			System.out.print(i + " : ");
 			
 			for(int j=0; j<no.size();j++) {
 				System.out.print((no.get(j).getLabelNoeud()!= -1)? no.get(j).getLabelNoeud() +" " : "");
 			}
 			System.out.print("\n");
 		}
 	}
 	 
 	// Verification si la tache est deja affetcer pour ne plus l'utiliser
 	 	public boolean verif_affectation(Noeud[] tab, Noeud n) {
 		
 	 		boolean TacheAffecte = false;		
 	 		for(int i = 0; i < tab.length; i ++) {
 			
 			if(tab[i].getLabelNoeud() ==  n.getLabelNoeud())
 				TacheAffecte = true;
 	 		}
 	 		return TacheAffecte;
 	 	}
	
 	 	//Recuperer la valeur minimale du Tableau
 		
 	 	public int min_valTable(int[] tab) {		
 			int index = 0;
 			int min  = tab[0];
 			
 			for(int i = 0; i < tab.length; i++) {			
 				if(tab[i] < min) {				
 					min = tab[i];
 					index = i;
 				}
 			}
 			return index;
 	 } 	
 	 /*
 	 	 * @Eddy K
 	 	 * Methode qui nous aide a recuperer la position du Noeud dans le liste et le renvoi
 	 	 * Retourne alors le label de la personne
 	 	 */
 	 	 	public int PositionNoeud(List<Noeud> list, Noeud noeud) {
 	 	 	 int position  = -1;
 	 		
 	 	 	 for(int i = 0; i < list.size(); i++) {
 	 			if(list.get(i).getLabelNoeud() == noeud.getLabelNoeud()) {		
 	 				position = i +1;
 	 			}
 	 		}
 	 		return position;
 	 	  }
}

