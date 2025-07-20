package app6;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {

  /**
   * Contenue / valeur de l'unité lexical.
   * Un lexeme c'est la chaine de charactères de l'Unité lexicale
   */
  String lexeme = "";

  /**
   * Indique le type de l'unité lexical.
   * Operateur / nombre / etc.
   */
  TypeUniteLexicale type = TypeUniteLexicale.invalid;

  /**
   * Dit si l'unité lexicale à été créé correctement.
   * Si le lexeme ne fait pas partie de l'alphabet, ceci est raised.
   * Après, faut allé voir le contenue du message.
   */
  boolean hasError = false;

  /**
   * Indique pourquoi l'unité lexical n'est pas valide.
   * Par défaut c'est une string vide.
   */
  String errorMessage = "";

  public boolean isNumeric() {
    try {
      Double.parseDouble(chaine);
      return true;
    } catch(NumberFormatException e){
      return false;
    }
  }


  /** Un ou deux constructeurs (ou plus, si vous voulez)
   *  pour l'initalisation d'attributs
   */
  public Terminal( String unit ) {   // arguments possibles
     chaine = unit;
     isNumber = isNumeric();
  }

}
