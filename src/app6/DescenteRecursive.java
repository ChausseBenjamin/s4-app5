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
    RecDescentResp result = F();
    if (!result.remainder.isEmpty()) {
      System.out.printf("Failure to complete parse. Unable to parse this remaining expression %s\n", result.remainder);
    }
    System.out.println("Analysis over");
    return result.elem;
  }

  // Methode pour chaque symbole non-terminal de la grammaire retenue
  public RecDescentResp E(ArrayList<Terminal> parseData) {
    RecDescentResp resp = T(parseData);
    ElemAST elemT = resp.elem;
    ArrayList<Terminal> remainder = resp.remainder;

    // Reached the end of parsing (no rhs)
    if (remainder.isEmpty()) {
     return resp;
    }

    Terminal pivot = remainder.get(0);
    String pivotStr = pivot.lexeme;
    switch (pivotStr) {
      case "+", "-":
        RecDescentResp right = E(new ArrayList<>(remainder.subList(1, remainder.size())));
        return new RecDescentResp(
          right.remainder,
          new NoeudAST(pivot, elemT, right.elem)
        );

      default:
        System.out.printf("Grammar Analysis expected a '+' or '-' pivot to the right of your expression, found %s\n", pivotStr);
        return null;
    }
  }

  public  RecDescentResp T(ArrayList<Terminal> parseData) {
    RecDescentResp resp = P(parseData);
    ElemAST elemP = resp.elem;
    ArrayList<Terminal> remainder = resp.remainder;

    // Reach the end of the parsing (no rhs)
    if (remainder.isEmpty()) {
      return resp;
    }

    Terminal pivot = remainder.get(0);
    String pivotStr = pivot.lexeme;
    switch (pivotStr) {
      case "*","/":
        RecDescentResp right = T(new ArrayList<>(remainder.subList(1, remainder.size())));
        return new RecDescentResp(
          right.remainder,
          new NoeudAST(pivot,elemP,right.elem)
        );
      default:
        System.out.printf("Grammar Analysis expected a '*' or '/' pivot to the right of your expression, found %s\n", pivotStr);
        return null;
    }
  }

  public RecDescentResp P(ArrayList<Terminal> parseData) {
    if (parseData.isEmpty()) {
      System.out.println("Grammar Analysis expected a delimiter, literal or identifier but found nothing");
      return null;
    };

    Terminal pivot = parseData.get(0); // In this case the "pivot" is either our leaf or an opening "("
    String pivotStr = pivot.lexeme;
    ArrayList<Terminal> remainder = new ArrayList<>(parseData.subList(1, parseData.size()));

    switch (pivot.type) {
      case delimiter:
        // Ensure we start a delim block with an opening paren
        if (pivotStr != "(") {
          System.out.printf("Grammar Analysis expected a '(' delimiter next, found %s\n", pivotStr);
        }
        RecDescentResp inner = E(remainder);
        if (inner.remainder.isEmpty()) {
          System.out.println("Grammar Analysis expected a ')' delimiter next but found nothing\n");
        }
        if ( inner.remainder.get(0).lexeme != ")") {
          System.out.printf("Grammar Analysis expected a ')' delimiter next, found %s\n", pivotStr);
        }
        return new RecDescentResp(
          new ArrayList<>(inner.remainder.subList(1,inner.remainder.size())),
          inner.elem
        );


      case identifier,literal:
        return new RecDescentResp(
          remainder,
          new FeuilleAST(pivot)
        );


      default:
        System.out.printf("Grammar Analysis found an unexpected terminal: %s\n", pivotStr);
        return null;
      }

    }

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
  // E() doesn't have a remainder as it's the root node
  RecDescentResp(ArrayList<Terminal> EData) {
    this.elem = null;
    this.remainder = EData;
  }
  RecDescentResp(ArrayList<Terminal> remainder, ElemAST elem) {
    this.elem = elem;
    this.remainder = remainder;
  }
}