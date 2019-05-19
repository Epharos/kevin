package fr.epharos;

import java.util.Scanner;

public class Main 
{
	public static void main(String[] args)
	{		
		freq.load();
		
		if(freq.getLoaded("frequency") != null)
			frequency = Long.valueOf(freq.getLoaded("frequency"));
		
		frequency("keke");
		
		System.out.println("Hello World!");
		
		Saver s = new Saver("test.dat"); //Cr�ation de l'instance d'un Saver
		s.addToSaver("cle", "valeur"); //Ajout d'une valeur dans la sauvegarde
		s.addToSaver("t", 17);
		s.save(); //On lance la sauvegarde
		
		//-----------
		
		s.load(); //On charge pour pouvoir r�cup�rer les valeurs
		System.out.println(s.getLoaded("cle")); //Et on r�cup�re la valeur par rapport � sa cl� (et l� je l'affiche)
		int i = Integer.valueOf(s.getLoaded("t")); //Tu peux stocker le chargement dans des variables � condition de bien les caster avant
		
		//-----------
		
		//Pour sauvegarder automatiquement tu fais un nouveau thread comme �a :
		
		Runnable r = new Runnable() //on cr�e une instance de Runnable
		{
			public void run() //on r��crit la fonction run (appel�e au d�but du Thread)
			{
				Saver sb = new Saver("thread.dat");
				long ls = System.currentTimeMillis(); //compteur pour la sauvegarde
				
				for(;;) //boucle infinie qui n'affectera pas le thread principal donc qui ne bloquera rien
				{					
					if(System.currentTimeMillis() - ls >= frequency * 1000) //s'il s'est pass� 60 secondes entre maintenant et la derni�re sauvegarde
					{
						ls = System.currentTimeMillis(); //alors on refresh le compteur
						sb.clear(); //on clear notre saver pour pas enregistrer 4651651 fois les m�mes valeurs
						sb.addToSaver("univers", 42); //on ajoute la cl�/valeur
						sb.save(); //et on pense � sauvegarder
					}
				}
			}
		};
		
		Thread t = new Thread(r);
		t.start(); //et on pense bien � lancer le thread !
	}
	
	static long frequency = 60;
	static Saver freq = new Saver("frequency.dat");
	
	public static void frequency(String password) //�videmment l� je met le mot de passe en argument, mais toi tu l'encrypte en SHA256 et tu le fous en dur dans le code
	{
		System.out.println("Current frequency : " + frequency + " second(s)");
		
		String p = "";
		Scanner sc = new Scanner(System.in);
		
		while(!p.equals(password))
		{
			System.out.print("Enter password to change frequency of saving : ");
			p = sc.nextLine();
			
			if(p.equals("quit"))
				return;
		}
		
		System.out.println("Enter the new frequency : ");
		long nf = sc.nextLong();
		
		frequency = nf;
		freq.addToSaver("frequency", frequency);
		freq.save();
		freq.clear();
	}
}
