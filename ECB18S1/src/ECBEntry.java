import java.util.Date;

import javax.swing.SpringLayout.Constraints;

/**
 * ECBEntry: elements in the electronic contact book (ECB).
 * 
 * @author Chen Zhuge
 * @version 0.06
 * updated on 20180606
 *
 */

/**
 * This class is the information of a contact.
 * @author Chen Zhuge
 *
 */
public class ECBEntry
{
    final static String TAG_NAME = "name";
    final static String TAG_BRITHDAY = "birthday"; 
    final static String TAG_PHONE = "phone"; 
    final static String TAG_ADDRESS = "address"; 
    final static String TAG_EMAIL = "email"; 
    
    private String m_strName;
    private CustomDate m_cdBirthday;
    private String m_strPhone;
    private String m_strAddress;
    private String m_strEmail;
    
    private boolean m_bIsValid;
    
    /**
     * Constructor of ECBEntry.
     * Check if an ECBEntry contains both name and birthday. If not, this instance will be marked as "invalid"
     * in m_bIsValid field.
     * 
     * @param strRecord The record from input file "phone book file".
     */
    public ECBEntry(String strRecord)
    {
        m_bIsValid = true;
        
        String[] aryStrInfo = strRecord.split(";");
        
        for (int i = 0; i < aryStrInfo.length; i++)
        {
            String[] aryStrSubInfo = aryStrInfo[i].trim().split(" ");
            String strTag = aryStrSubInfo[0];
            if (strTag.equalsIgnoreCase(TAG_NAME))
            {
                m_strName = aryStrInfo[i].trim().substring(TAG_NAME.length() + 1).trim();
                if (m_strName.matches("^[a-zA-Z ,.'-]+$") == false)
                {
                    m_bIsValid = false;
                    break;
                }
            }
            else if (strTag.equalsIgnoreCase(TAG_BRITHDAY))
            {
                m_cdBirthday = new CustomDate(aryStrInfo[i].trim().substring(TAG_BRITHDAY.length() + 1));
                if (m_cdBirthday.GetDate() == null || m_cdBirthday.GetDate().after(new Date()))
                {
                    m_bIsValid = false;
                    break;
                }
            }
            else if (strTag.equalsIgnoreCase(TAG_PHONE))
            {
                m_strPhone = aryStrInfo[i].trim().substring(TAG_PHONE.length() + 1).trim();
                if (m_strPhone.matches("^[0-9 +-]+$") == false)
                {
                    //m_bIsValid = false;
                    //break;
                    m_strPhone = null;
                }
                
                //trim the heading zeros.
                //reference: https://stackoverflow.com/questions/2800739/how-to-remove-leading-zeros-from-alphanumeric-text/2800839#2800839
                if (m_strPhone != null)
                {
                    m_strPhone = m_strPhone.replaceFirst("^0+(?!$)", "");
                }     
            }
            else if (strTag.equalsIgnoreCase(TAG_ADDRESS))
            {
                m_strAddress = aryStrInfo[i].trim().substring(TAG_ADDRESS.length() + 1).trim();
                
                if (m_strAddress.matches(".*[0-9]{4}$") == false)
                {
                    m_strAddress = null;
                }
                
//                if (m_strAddress != null)
//                {
//                    m_strAddress = m_strAddress.replaceFirst("^[^0-9]+(?!$)", "");
//                }
                
            }
            else if (strTag.equalsIgnoreCase(TAG_EMAIL))
            {
                m_strEmail = aryStrInfo[i].trim().substring(TAG_EMAIL.length() + 1).trim();
                if (m_strEmail.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$") == false)
                {
                    m_strEmail = null;
                }
            }
        }
        
        if (m_strName == null || m_cdBirthday == null)
        {
            m_bIsValid = false;
        }
        
    }
    
    /**
     * Summarize this entry's information (in a friendly format).
     * @return
     */
    public String GetInfo()
    {
        String strInfo = TAG_NAME + ": " + m_strName + "\r\n" + 
                         TAG_BRITHDAY + ": " + m_cdBirthday.GetStrDate() + "\r\n";
       
        if (m_strAddress != null)
        {
            strInfo += TAG_ADDRESS + ": " + m_strAddress + "\r\n"; 
        }
        
        if (m_strPhone != null)
        {
            strInfo += TAG_PHONE +": " + m_strPhone + "\r\n"; 
        }
        
        if (m_strEmail != null)
        {
            strInfo += TAG_EMAIL + ": " + m_strEmail + "\r\n"; 
        }
        
        //strInfo += "\r\n";    //no longer needed
        
        return strInfo;
    }
    
    /**
     * 
     * @return Return true if this instance is valid; return false if this instance is invalid.
     */
    public boolean GetValidation()
    {
        return m_bIsValid;
    }

    
    //Below is the setters and getters.
    
    /**
     * @return the m_strName
     */
    public String GetName()
    {
        return m_strName;
    }

    /**
     * @param m_strName the m_strName to set
     */
    public void SetName(String m_strName)
    {
        this.m_strName = m_strName;
    }

    /**
     * @return the m_strBirthday
     */
    public CustomDate GetBirthday()
    {
        return m_cdBirthday;
    }

    /**
     * @param m_strBirthday the m_strBirthday to set
     */
    public void SetBirthday(CustomDate m_strBirthday)
    {
        this.m_cdBirthday = m_strBirthday;
    }

    /**
     * @return the m_strPhone
     */
    public String GetPhone()
    {
        return m_strPhone;
    }

    /**
     * @param m_strPhone the m_strPhone to set
     */
    public void SetPhone(String m_strPhone)
    {
        this.m_strPhone = m_strPhone;
    }

    /**
     * @return the m_strAddress
     */
    public String GetAddress()
    {
        return m_strAddress;
    }

    /**
     * @param m_strAddress the m_strAddress to set
     */
    public void SetAddress(String m_strAddress)
    {
        this.m_strAddress = m_strAddress;
    }

    /**
     * @return the m_strEmail
     */
    public String GetEmail()
    {
        return m_strEmail;
    }

    /**
     * @param m_strEmail the m_strEmail to set
     */
    public void SetEmail(String m_strEmail)
    {
        this.m_strEmail = m_strEmail;
    }
    
}


