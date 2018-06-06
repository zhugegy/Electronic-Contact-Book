/**
 * CustomDate: Copied from week10 case study. Used for data management.
 * @author Chen Zhuge
 * @version 0.02
 * @last updated on 20180519
 */

import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class CustomDate 
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    private String m_StrDate;
    private Date m_dDate;
    
    /**
     * Constructor.
     * @param strInput
     */
    public CustomDate(String strInput)
    {
        m_StrDate = strInput;
        String[] temp;
        if (m_StrDate.matches("\\d+\\D\\d+\\D\\d+")) 
        {
            temp = m_StrDate.split("\\D");
            if (temp.length == 3)
            {
                for (int i = 0; i < 2; ++i) 
                {
                    if (temp[i].length() < 2)
                    {
                        temp[i] = "0" + temp[i];
                    }
                }
                m_StrDate = temp[0] + "-" + temp[1] + "-" + temp[2];
            }
        }
        
        try
        {
            m_dDate = dateFormat.parse(m_StrDate);
        } catch (Exception e)
        {
            m_dDate = null;
        }
    }
    
    /**
     * Get date.
     * @return
     */
    public Date GetDate()
    {
        if (isLegalDate(m_StrDate) == false)
        {
            return null;
        }
        
        return m_dDate;
    }
    
    /**
     * Get value for comparison or sorting.
     * @return
     */
    public long GetValue()
    {
        return m_dDate.getTime();
    }
    
    /**
     * Get date in string format.
     * @return
     */
    public String GetStrDate()
    {
        return m_StrDate;
    }
    
    /**
     * Check if this date is legal, e.g. 30-02-2000.
     * Reference: https://stackoverflow.com/questions/226910/how-to-sanity-check-a-date-in-java
     * @param strSource
     * @return
     */
    private boolean isLegalDate(String strSource)
    {
        dateFormat.setLenient(false);
        return dateFormat.parse(strSource, new ParsePosition(0)) != null;
    }
}