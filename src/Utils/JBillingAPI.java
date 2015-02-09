/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

//import com.sapienter.jbilling.server.user.partner.db.Partner;
import com.sapienter.jbilling.server.item.ItemDTOEx;
import com.sapienter.jbilling.server.item.ItemTypeWS;
import com.sapienter.jbilling.server.user.partner.PartnerServiceWS;
import com.sapienter.jbilling.server.util.api.JbillingAPI;
import com.sapienter.jbilling.server.util.api.JbillingAPIException;
import com.sapienter.jbilling.server.util.api.JbillingAPIFactory;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainForm.JBGUIMainForm;

/**
 * Proxy: Define as:
 * -Dhttp.proxyHost=proxy -Dhttp.proxyPort=8080
 *
 * on the command line.
 *
 * @author TGDSPIA1
 */
public class JBillingAPI 
{
  public static List<Integer> itemMobileTypes = new ArrayList<Integer>();
  public static List<Integer> itemFIXType = new ArrayList<Integer>();
  public static List<Integer> itemDATAType = new ArrayList<Integer>();
  public static Integer[] m = new Integer[]{29,31,32,56,6600,6601};
  public static Integer[] f = new Integer[]{51,52,53};
  public static Integer[] d = new Integer[]{17,21,23,24,27,28};
  
  private static JbillingAPI api;

  /**
   * @return the api
   */
  public static JbillingAPI getApi() {
    if (api == null) {
      try {
        api = JbillingAPIFactory.getAPI();
        
        api.authenticate("ian-be", "Diving67");
      }
      catch (JbillingAPIException ex)
      {
        System.out.println("JBillingAPI exception");
      }
      catch (IOException ex)
      {
        System.out.println("IO exception");
      }
    }
    
    return api;
  }

  public enum ServiceType{         
     MobileSerices,FIXServices,DataServices
  }

 /**
  * recovers all items from JBilling
  */
  public static ArrayList<ItemLookupResult> getItemList(ArrayList<Integer> typesToLookup)
  {
    ArrayList<ItemLookupResult> resultList = new ArrayList<ItemLookupResult>();

    try
    {
      ItemDTOEx[] itemList = getApi().getAllItems();

      for (ItemDTOEx tmpItem : itemList)
      {
        // Filter for Mobile Bundles
        Integer[] typeList = tmpItem.getTypes();

        for (Integer type: typeList)
        {
          Iterator<Integer> typeSearchIter = typesToLookup.iterator();
          while (typeSearchIter.hasNext())
          {
            Integer typeToLookup = typeSearchIter.next();

            // For each of the search types we are searching for
            if (type.intValue() == typeToLookup.intValue())
            {
              System.out.println(typeToLookup + " : " + tmpItem.getDescription());
              ItemLookupResult tmpItemResult = new ItemLookupResult();
              tmpItemResult.setItemDesc(tmpItem.getDescription());
              tmpItemResult.setItemNumber(tmpItem.getId());
              tmpItemResult.setType(typeToLookup);
              resultList.add(tmpItemResult);
            }
          }
        }
      }

      return resultList;
    }
    catch (Exception ex)
    {
      Logger.getLogger(JBGUIMainForm.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }
  
    public static ItemLookupResult[][] getItemTypeList()
  {
    /*ArrayList<ItemLookupResult> mobileResultList = new ArrayList<ItemLookupResult>();
	ArrayList<ItemLookupResult> dataResultList = new ArrayList<ItemLookupResult>();
	ArrayList<ItemLookupResult> fixResultList = new ArrayList<ItemLookupResult>();
	ArrayList<ArrayList<ItemLookupResult>> arrayItemLookup = new ArrayList<ArrayList<ItemLookupResult>>();*/
      
     itemMobileTypes.addAll(Arrays.asList(m));
     itemFIXType.addAll(Arrays.asList(f));
     itemDATAType.addAll(Arrays.asList(d));
     
     ItemLookupResult itemLookUpResult = new ItemLookupResult();
     List<ItemLookupResult> mobileResultList = new ArrayList<ItemLookupResult>();
     List<ItemLookupResult> dataResultList = new ArrayList<ItemLookupResult>();
     List<ItemLookupResult> fixResultList = new ArrayList<ItemLookupResult>();
     
     //ArrayList<ArrayList<ItemLookupResult>> resList = new ArrayList<ArrayList<ItemLookupResult>>();
     ItemLookupResult[][] res = new ItemLookupResult[3][12];
     
     /*for(int i=0; i<=res.length; i++){     
         ItemLookupResult[] t1 = res[0];
         for(int j=0; j<=t1.length;j++){  
         }     
     }*/    

    try
    {
      ItemTypeWS[] itemTypes = getApi().getAllItemCategories();
      int i=-1;
      int j=-1;
      int k=-1;

      for (ItemTypeWS tmpItemWS : itemTypes)
      {
        itemLookUpResult.setItemDesc(tmpItemWS.getDescription());
        itemLookUpResult.setItemNumber(tmpItemWS.getId());
        System.out.println("ItemType details are:"+itemLookUpResult.getItemDesc()+" and "+itemLookUpResult.getItemNumber());
        
        if(itemMobileTypes.contains(tmpItemWS.getId())){
            i++;
            res[0][i] = itemLookUpResult;
           // mobileResultList.add(itemLookUpResult);            
            continue;
        }
        
        if(itemFIXType.contains(tmpItemWS.getId())){
            j++;
            res[1][j] = itemLookUpResult;
            //fixResultList.add(itemLookUpResult);
            continue;
        }
        
        if(itemDATAType.contains(tmpItemWS.getId())){
            k++;
            res[2][k] = itemLookUpResult;
            //dataResultList.add(itemLookUpResult);
            continue;
        }
        System.out.println("items added to arrays "+i+";"+j+";"+k);
      }     

      return res;
	 }
    catch (Exception ex)
    {
        //System.out.println(ex);
      Logger.getLogger(JBGUIMainForm.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }
    
  public static List<Integer> getPartnerList(){
    List<PartnerServiceWS> partnerList;
    ArrayList<Integer> result = new ArrayList<Integer>();
    try{
      partnerList = getApi().getPartnerList();
      
      for (PartnerServiceWS partner : partnerList)
      {
        result.add(partner.getId());
      }
    }
    catch(Exception e){
        System.out.println(e);
    }
    return result;
  }
}
