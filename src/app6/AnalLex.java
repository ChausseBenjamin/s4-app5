package app6;

/** @author Ahmed Khoumsi */

import java.util.ArrayList;

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  // Attributs
  //  ...
  static int state = 0;
  String expression = "";
  boolean isWrong = false;
  ArrayList<Terminal> terminalArray = new ArrayList<Terminal>();

	
  /** Constructeur pour l'initialisation d'attribut(s)
   */
  public AnalLex( ) {
    //
  }

  public AnalLex(String fileContents ) {
    expression = fileContents;

    // Remove all whitespaces from the string.
    expression = expression.replaceAll("\\s+","");

    for (int index = 0; index < expression.length(); index++) {
      char character = expression.charAt(index);
      Terminal newTerminal = new Terminal(String.valueOf(character));
      terminalArray.add(newTerminal);
    }
  }


  /** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
  */
  public boolean resteTerminal( ) {
    return terminalArray.size() > 0;
  }
  
  
  /** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
  */
  public Terminal prochainTerminal( ) {
    Terminal toReturn = terminalArray.get(0);
    terminalArray.remove(0);
    return toReturn;
  }

 
  /** ErreurLex() envoie un message d'erreur lexicale
  */
  public void ErreurLex(String s) {	
     System.out.println("ERROR. Character = " + s);
    isWrong = true;
  }

  public static void end(String outputFileName, String toWrite) {
    Writer w = new Writer(outputFileName,toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Writing to output file " + outputFileName);
  }
  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    System.out.println("Working Directory = " + System.getProperty("user.dir"));

    if (args.length == 0) { // Aucun arguments données
      args = new String [2];
      args[0] = System.getProperty("user.dir") + "/ExpArith.txt";
      args[1] = System.getProperty("user.dir") + "/ResultatLexical.txt";
    }

    Reader r = new Reader(args[0]);
    System.out.println("Contenue de l'expression = " + r.toString());

    if(r.toString().length() == 0) {
      System.out.println("\t expression vide!");
    } else {
      AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

      // Execution de l'analyseur lexical
      Terminal t = null;
      while(lexical.resteTerminal()) {
        t = lexical.prochainTerminal();
        switch (state) {

          case 0:
            if (t.chaine.contentEquals("+")) {
              toWrite += t.chaine + "\n";
              lexical.ErreurLex("Cannot start with an operator");
              end(args[1], toWrite);
              return;
            } else if (t.isNumber) {
              toWrite += t.chaine;
              state = 1;
            } else {
              lexical.ErreurLex("Unknown character: " + t.chaine);
              end(args[1], toWrite);
              return;
            }
            break;

          case 1:
            if (t.isNumber) {
              toWrite += t.chaine;
            } else if (t.chaine.contentEquals("+")) {
              toWrite += "\n" + t.chaine + "\n";
              state = 0;
            } else {
              lexical.ErreurLex("Unknown character: " + t.chaine);
              end(args[1], toWrite);
              return;
            }
            break;
        }
      }
      System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    }
    System.out.println("Fin d'analyse lexicale");
    end(args[1], toWrite);
  }
}
