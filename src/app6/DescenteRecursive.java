package app6;

import java.util.ArrayList;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  // Attributs
  public AnalLex lexicalAnalyzer;
  String[] lexicalUnits;

  /** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
  */
  public DescenteRecursive(String inputFilePath, String outputSyntaxicFilePath, String outputLexicalFilePath) {
    lexicalAnalyzer = new AnalLex(inputFilePath);
    if (lexicalAnalyzer.isWrong) {
      ErreurSynt("Cannot continue further because the parsing failed");
    } else {
      System.out.println("Reading lexical output...");
      Reader lexReader = new Reader(outputLexicalFilePath);

      System.out.println("Parsing into lexical units...");
      lexicalUnits = lexReader.toString().split("\n");

    }
  }


  /** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
    *    Elle retourne une reference sur la racine de l'AST construit
  */
  public ElemAST AnalSynt( ) {
    System.out.println("Analysing... Calling E...");
    ElemAST result =  E(0);
    System.out.println("Analysis over");
    return result;
  }


  // Methode pour chaque symbole non-terminal de la grammaire retenue
  public ElemAST E(ArrayList<Terminal> parseData) {
    RecDescentResp resp = T(new RecDescentResp(parseData));
    ElemAST elemT = resp.elem;
    ArrayList<Terminal> remainder = resp.remainder;

    // Reached the end of parsing return the end
    if (remainder.isEmpty()) {
     return elemT;
    }

    Terminal pivot = remainder.get(0);
    ArrayList<Terminal> right = new ArrayList<>(remainder.subList(1, remainder.size()));
    return switch (pivot.lexeme) {
          case "+", "-" -> new NoeudAST(pivot, elemT, E(right));
          default -> {
              System.out.println("Grammar Analysis expected a '+'/'-' pivot to the right of your expression");
              yield null;
          }
      };
  }

  public RecDescentResp T(RecDescentResp parseData) {
    // TODO
  }

  public RecDescentResp P(RecDescentResp parseData) {
    // TODO
  }


  /** ErreurSynt() envoie un message d'erreur syntaxique
  */
  public void ErreurSynt(String s) {
  }

  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWriteEval = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [3];
      args[0] = System.getProperty("user.dir") + "/ExpArith.txt";
      args[1] = System.getProperty("user.dir") + "/ResultatSyntaxique.txt";
      args[2] = System.getProperty("user.dir") + "/ResultatLexical.txt";
    }
    DescenteRecursive dr = new DescenteRecursive(args[0], args[1], args[2]);
    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite 
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

}

public class RecDescentResp {
  public ElemAST elem;
  public ArrayList<Terminal> remainder;
  RecDescentResp(ArrayList<Terminal> EData) {
    this.elem = null;
    this.remainder = EData;
  }
}