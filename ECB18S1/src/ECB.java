/**
 * ECB: The electronic contact book (ECB).
 * 
 * @author Chen Zhuge
 * @version 0.02
 * @last updated on 20180520
 * 
 */
import java.util.ArrayList;

/**
 * This class is like a container for all the contacts.
 * @author Chen Zhuge
 * @version 0.02
 * updated on 20180606
 */
public class ECB
{
    private ArrayList<ECBEntry> m_alEntries;
    
    /**
     * Constructor of ECB.
     */
    public ECB()
    {
        m_alEntries = new ArrayList<ECBEntry>();
    }
      
    /**
     * Get the entries list.
     * @return the m_alEntries
     */
    public ArrayList<ECBEntry> GetEntriesList()
    {
        return m_alEntries;
    }
    
    
//    /**
//     * Only for debug purpose.
//     */
//    public void __testPrintTheNames()
//    {
//        for (ECBEntry e : GetEntriesList())
//        {
//            System.out.println(e.GetName());
//        }
//    }

}
