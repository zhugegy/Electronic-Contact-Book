import java.io.File;
import java.util.Scanner;

/**
 * ECBProcessor: The processor of the ECB.
 * @author Chen Zhuge
 * @version 0.04
 * @last updated  on 20180602
 */

/**
 * The controller of this program. The program starts with the construction of this class.
 * @author Chen Zhuge
 * @version 0.02
 * updated on 20180606
 *
 */
public class ECBProcessor
{
    private String m_strPhoneBookFileName;
    private String m_strInstructionFileName;
    private String m_strOutputFileName;
    private String m_strReportFileName;
    
    private ECB m_ecbInstance;
    
    /**
     * Constructor of ECBProcessor
     * @param strPhoneBookFileName
     * @param strCommandsFIle
     * @param strOutputFileName
     * @param strReportFileName
     */
    public ECBProcessor(String strPhoneBookFileName, String strCommandsFIle, 
            String strOutputFileName, String strReportFileName)
    {
        m_strPhoneBookFileName = strPhoneBookFileName;
        m_strInstructionFileName = strCommandsFIle;
        m_strOutputFileName = strOutputFileName;
        m_strReportFileName = strReportFileName;
               
        m_ecbInstance = new ECB();
    
        ProcessPhoneBookFile();
        ProcessInstructionFile();
    }     
    
    /**
     * Read the phone book file, and construct the ECBEntries. For valid ECBEntires,
     * add them into the ECB, who has an array list to store them.
     * 
     * @return return 0 if success.
     */
    private int ProcessPhoneBookFile()
    {
        try
        {
            File fRead = new File(m_strPhoneBookFileName);
            Scanner sScanner = new Scanner(fRead);
            String strContactInfo = "";
            while (sScanner.hasNextLine())
            {
                String strTmp = sScanner.nextLine();
                
                //if this is the delimiter of a contact info
                if (strTmp.length() == 0)
                {
                    strContactInfo = "add " + strContactInfo;
                    CommandExecution ceTmp = new CommandExecution(strContactInfo, m_ecbInstance, null); 
                    strContactInfo = "";
                }
                //if this is an informative string
                else
                {
                    strContactInfo = strContactInfo + strTmp + ";";
                }
            }
                  
            //If the file is not end with an empty line, we still need to deal with this (rare) case.
            if (strContactInfo.length() != 0)
            {
                strContactInfo = "add " + strContactInfo;
                CommandExecution ceTmp = new CommandExecution(strContactInfo, m_ecbInstance, null); 
                //->//strContactInfo = "";
            }
            
        }catch (Exception e)
        {
            // TODO: handle exception
        }
              
        return 0;
    }
    
    /**
     * Read the instruction file, and execute these instructions.
     * @return return 0 if success.
     */
    private int ProcessInstructionFile()
    {
        try
        {
            File fRead = new File(m_strInstructionFileName);
            Scanner sScanner = new Scanner(fRead);
            while (sScanner.hasNextLine())
            {
                CommandExecution tmp = new CommandExecution(sScanner.nextLine(), 
                        m_ecbInstance,  m_strOutputFileName + ";" + m_strReportFileName);        
            }         
        }catch (Exception e)
        {
            // TODO: handle exception
        }
        
        return 0;
    }
    
    
}
