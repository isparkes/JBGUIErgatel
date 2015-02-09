/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author TGDSPIA1
 */
public class Common {

  public static final Integer BUNDLE_TYPE = new Integer(7000);
  public static final Integer RATE_TYPE = new Integer(58);

  /**
   * Show a simple Dialog with an "OK" Button
   *
   * @param rootPane
   * @param message
   */
  public static void showErrorBox(Component rootPane, String message) {
    // The A Number did not have the right format
    JOptionPane.showMessageDialog(rootPane, message);
  }

  /**
   * Show a conformation box with "Yes" "No" and "Cancel".
   *
   * @param rootPane
   * @param message
   */
  public static boolean showConfirmationBox(Component rootPane, String message) {
    // The A Number did not have the right format
    int returnValue = JOptionPane.showConfirmDialog(rootPane, message);
    return (returnValue == 0);
  }

  /**
   * Needed to create XMLGregorianCalendar instances
   */
  private static DatatypeFactory df = null;

  static {
    try {
      df = DatatypeFactory.newInstance();
    } catch (DatatypeConfigurationException dce) {
      throw new IllegalStateException(
              "Exception while obtaining DatatypeFactory instance", dce);
    }
  }

  /**
   * Converts a java.util.Date into an instance of XMLGregorianCalendar
   *
   * @param date Instance of java.util.Date or a null reference
   * @return XMLGregorianCalendar instance whose value is based upon the value
   * in the date parameter. If the date parameter is null then this method will
   * simply return null.
   */
  public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
    if (date == null) {
      return null;
    } else {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTimeInMillis(date.getTime());
      return df.newXMLGregorianCalendar(gc);
    }
  }

  /**
   * Converts an XMLGregorianCalendar to an instance of java.util.Date
   *
   * @param xgc Instance of XMLGregorianCalendar or a null reference
   * @return java.util.Date instance whose value is based upon the value in the
   * xgc parameter. If the xgc parameter is null then this method will simply
   * return null.
   */
  public static java.util.Date asDate(XMLGregorianCalendar xgc) {
    if (xgc == null) {
      return null;
    } else {
      return xgc.toGregorianCalendar().getTime();
    }
  }

  public static void copyFileByName(String sourceFileName, String destFileName) throws IOException {
    File sourceFile = new File(sourceFileName);
    File destFile = new File(destFileName);
    copyFile(sourceFile,destFile);
  }
  
  public static void copyFile(File sourceFile, File destFile) throws IOException {
    if (!destFile.exists()) {
      destFile.createNewFile();
    }

    FileChannel source = null;
    FileChannel destination = null;

    try {
      source = new FileInputStream(sourceFile).getChannel();
      destination = new FileOutputStream(destFile).getChannel();
      destination.transferFrom(source, 0, source.size());
    } finally {
      if (source != null) {
        source.close();
      }
      if (destination != null) {
        destination.close();
      }
    }
  }
}
