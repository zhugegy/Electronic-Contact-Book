import javax.swing.SpringLayout.Constraints;

/**
 * ECBEntry: elements in the electronic contact book (ECB).
 * 
 * @author Chen Zhuge
 * @version 0.04
 * @last updated on 20180521
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
            String[] aryStrSubInfo = aryStrInfo[i].split(" ");
            String strTag = aryStrSubInfo[0];
            if (strTag.equalsIgnoreCase(TAG_NAME))
            {
                m_strName = aryStrInfo[i].substring(5).trim();
                if (m_strName.matches("^[a-zA-Z ,.'-]+$") == false)
                {
                    m_bIsValid = false;
                    break;
                }
            }
            else if (strTag.equalsIgnoreCase(TAG_BRITHDAY))
            {
                m_cdBirthday = new CustomDate(aryStrInfo[i].substring(9));
                if (m_cdBirthday == null)
                {
                    m_bIsValid = false;
                    break;
                }
            }
            else if (strTag.equalsIgnoreCase(TAG_PHONE))
            {
                m_strPhone = aryStrInfo[i].substring(6).trim();
                if (m_strPhone.matches("^[0-9 +-]+$") == false)
                {
                    m_bIsValid = false;
                    break;
                }
            }
            else if (strTag.equalsIgnoreCase(TAG_ADDRESS))
            {
                m_strAddress = aryStrInfo[i].substring(8).trim();
            }
            else if (strTag.equalsIgnoreCase(TAG_EMAIL))
            {
                m_strEmail = aryStrInfo[i].substring(6).trim();
                if (m_strEmail.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$") == false)
                {
                    m_bIsValid = false;
                    break;
                }
            }
        }
        
        if (m_strName == null || m_cdBirthday == null)
        {
            m_bIsValid = false;
        }
        
    }
    
    public String GetInfo()
    {
        String strInfo = TAG_NAME + ": " + m_strName + "\r\n" + 
                         TAG_BRITHDAY + ": " + m_cdBirthday.GetStrDate() + "\r\n";
        
        if (m_strPhone != null)
        {
            strInfo += TAG_PHONE +": " + m_strPhone + "\r\n"; 
        }
        
        if (m_strAddress != null)
        {
            strInfo += TAG_ADDRESS + ": " + m_strAddress + "\r\n"; 
        }
        
        if (m_strEmail != null)
        {
            strInfo += TAG_EMAIL + ": " + m_strEmail + "\r\n"; 
        }
        
        strInfo += "\r\n";
        
        return strInfo;
    }
    
    public String GetInfoOneLine()
    {
        String strInfo = TAG_NAME + " " + m_strName + ";" + 
                         TAG_BRITHDAY + " " + m_cdBirthday.GetStrDate() + ";";
        
        if (m_strPhone != null)
        {
            strInfo += TAG_PHONE + " " + m_strPhone + ";"; 
        }
        
        if (m_strAddress != null)
        {
            strInfo += TAG_ADDRESS + " " + m_strAddress + ";"; 
        }
        
        if (m_strEmail != null)
        {
            strInfo += TAG_EMAIL + " " + m_strEmail + ";"; 
        }
        
        strInfo += "\r\n";
        
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


