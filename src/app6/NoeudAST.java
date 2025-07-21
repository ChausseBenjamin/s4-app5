package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  ElemAST left;
  ElemAST right;
  Terminal pivot;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String input, ElemAST left, ElemAST right ) { // avec arguments
    right = right;
    left = left;
    //value = new Terminal(input);
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {
     return -69;
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
     return "not done";
  }

}


