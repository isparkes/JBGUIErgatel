/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author TGDSPIA1
 */
public class Validations
{
  public static boolean validateANumber(String ANumber)
  {
    if (ANumber.startsWith("0"))
    {
      if (ANumber.length()==10)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
  }
}
