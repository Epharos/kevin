package fr.epharos;

public class Main 
{
	public static void main(String[] args)
	{
		System.out.println("Hello World!");
		
		Saver s = new Saver("test.dat"); //Création de l'instance d'un Saver
		s.addToSaver("cle", "valeur"); //Ajout d'une valeur dans la sauvegarde
		s.addToSaver("t", 17);
		s.save(); //On lance la sauvegarde
		
		//-----------
		
		s.load(); //On charge pour pouvoir récupérer les valeurs
		System.out.println(s.getLoaded("cle")); //Et on récupère la valeur par rapport à sa clé (et là je l'affiche)
		int i = Integer.valueOf(s.getLoaded("t")); //Tu peux stocker le chargement dans des variables à condition de bien les caster avant
		
		//-----------
		
		//Pour sauvegarder automatiquement tu fais un nouveau thread comme ça :
		
		Runnable r = new Runnable() //on crée une instance de Runnable
		{
			public void run() //on réécrit la fonction run (appelée au début du Thread)
			{
				Saver sb = new Saver("thread.dat");
				long ls = System.currentTimeMillis(); //compteur pour la sauvegarde
				
				for(;;) //boucle infinie qui n'affectera pas le thread principal donc qui ne bloquera rien
				{					
					if(System.currentTimeMillis() - ls >= 5000) //s'il s'est passé 60 secondes entre maintenant et la dernière sauvegarde
					{
						ls = System.currentTimeMillis(); //alors on refresh le compteur
						sb.clear(); //on clear notre saver pour pas enregistrer 4651651 fois les mêmes valeurs
						sb.addToSaver("univers", 42); //on ajoute la clé/valeur
						sb.save(); //et on pense à sauvegarder
					}
				}
			}
		};
		
		Thread t = new Thread(r);
		t.start(); //et on pense bien à lancer le thread !
	}
}
