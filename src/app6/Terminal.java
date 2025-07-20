package app6;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {


  // Constantes et attributs
  //  ....
  String chaine = "";
  boolean isNumber = false;

  public boolean isNumeric() {
    try {
      Double.parseDouble(chaine);
      return true;
    } catch(NumberFormatException e){
      return false;
    }
  }

  public boolean isValidLetter() {
    return chaine.contentEquals("E") || chaine.contentEquals("T") || chaine.contentEquals("a");
  }

  public boolean isValidOperator() {
    return chaine.contentEquals("+");
  }


  /** Un ou deux constructeurs (ou plus, si vous voulez)
   *  pour l'initalisation d'attributs
   */
  public Terminal( String unit ) {   // arguments possibles
     chaine = unit;
     isNumber = isNumeric();
  }

}
