package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {
    Terminal value;

    /**Constructeur pour l'initialisation d'attribut(s)
     */
    public FeuilleAST(Terminal lexicalUnit) {  // avec arguments
        value = lexicalUnit;
    }

    /** Evaluation de feuille d'AST
    */
    public int EvalAST( ) {
        if (value.type == TypeUniteLexicale.identifier) {
            System.out.println("EVAL ERR: Tried to evaluate an identificator: " + value.lexeme);
            return 0;
        }

        if (value.type == TypeUniteLexicale.operator) {
            System.out.println("EVAL ERR: Operators cannot be a leaf of AST. Critical system error. Char: " + value.lexeme);
            return 0;
        }

        if (value.type == TypeUniteLexicale.delimiter) {
            System.out.println("EVAL ERR: Delimiter cannot be a leaf of AST: " + value.lexeme);
            return 0;
        }

        if (value.type == TypeUniteLexicale.literal) {
            try {
                int result = Integer.parseInt(value.lexeme);
                return result;
            } catch(NumberFormatException e){
                System.out.println("EVAL ERR: Could not parse literal lexeme to int: " + value.lexeme);
                return 0;
            }
        }

        System.out.println("EVAL ERR: Skill issue: Unrecognized token: " + value.lexeme);
        return 0;
    }


    /** Lecture de chaine de caracteres correspondant a la feuille d'AST
    */
    public String LectAST( ) {
        return "not done";
    }

}
