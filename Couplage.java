package TP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Couplage {
	
	public static void main(String[] args) {
		
		Graphe graphe = creeReseau("donnee.txt");
		
		/*
		 * AFFICHAGE DU CHEMIN PARCOURU EN BFS 
		 
		System.out.println("CHEMIN PARCOURU EN BFS :");
		System.out.println("************************************\n");
		
       LinkedList<Noeud> cheminBfs = new LinkedList<Noeud>();		
	   cheminBfs = graphe.cheminBFS(new Noeud(0), new Noeud(11));
		for(int i = 0; i < cheminBfs.size(); i++)
			if(i == cheminBfs.size() -1)
				System.out.print(cheminBfs.get(i).getLabelNoeud());
			else
				System.out.print(cheminBfs.get(i).getLabelNoeud() + "==>");
		
	
		/*
		 * AFFICHAGE DU CHEMIN PARCOURU EN BFS INVERSE
		 
		System.out.println("\n");
		System.out.println(" ");
		System.out.println("CHEMIN PARCOURU EN BFS INVERSE:");
		System.out.println("************************************");
			
		/*
		 * AFFICHAGE DU COUPLAGE
		 */

		 //boolean exist = graphe.inverseChemin(cheminBfs);
		 System.out.println("\n\nALGORITHME DE FORD FULKERSON :");	
	     int flot_max = graphe.ff(new Noeud(0), new Noeud(11));			
				
	}
	
   /*
    * STATIC VOID POUR CREER UN GRAPHE A PARTIR DE NOTRE FI
    */
	public static Graphe creeReseau(String fichier) {
			
		BufferedReader reader;
		Noeud[][] noeuds = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		int tailleFichier=0;
		
		try {
			//Lire le fichier
			reader = new BufferedReader(new FileReader(fichier));		
			String ligne;
			String personne="";
			
			//Ajouter la premiere ligne dans notre ArrayList
			while((ligne=reader.readLine())!= null) {
				arrayList.add(ligne);
			}
			
			tailleFichier = Integer.parseInt(arrayList.get(0));			
			for(int i= tailleFichier+1; i<= tailleFichier*2; i++) {
				
				if(i<tailleFichier *2)
					personne += i + " ";
				else
					personne += i;
			}
			
			arrayList.remove(0);
			arrayList.add(0, personne);
			
			int largeur = tailleFichier;
			int longueur = (tailleFichier * 2) + 2;
			
			noeuds = new Noeud[longueur][largeur];
			
			for(int i=0; i<longueur; i++) 
			{
				for(int j=0; j<largeur; j++) {
					
					noeuds[i][j]= new Noeud(-1);
				}
			}
			//suite
			String[] s = arrayList.get(0).split(" ");
			
			for(int i = 0; i < s.length; i++) {
				
				noeuds[0][i] = new Noeud(Integer.parseInt(s[i]));
			}
			
			//suite
			for(int i = 1; i <= tailleFichier; i++) {
				
				String[] currentL = arrayList.get(i).split(" ");			
				int ii = tailleFichier + i;
				
				for(int j = 0; j < currentL.length; j++) {					
					noeuds[ii][j] = new Noeud(Integer.parseInt(currentL[j]));					
				}			
			}
			//SUITE
			for(int i = 1; i < tailleFichier + 1; i++) {		
				noeuds[i][0] = new Noeud(noeuds.length - 1);	
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ex) {	
			System.out.println("Error " + ex);
			ex.printStackTrace();
		}	
		return new Graphe(noeuds, tailleFichier);		
	}

}
