package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {
    Terminal value;

    /**Constructeur pour l'initialisation d'attribut(s)
     */
    public FeuilleAST(String lexicalUnit) {  // avec arguments
        value = new Terminal(lexicalUnit);
    }


    /** Evaluation de feuille d'AST
    */
    public int EvalAST( ) {
        return -420;
    }


    /** Lecture de chaine de caracteres correspondant a la feuille d'AST
    */
    public String LectAST( ) {
        return "not done";
    }

}
