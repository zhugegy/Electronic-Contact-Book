import java.io.File;
import java.util.Scanner;

/**
 * ECBProcessor: The processor of the ECB.
 * @author Chen Zhuge
 * @version 0.03
 * @last updated on 20180523
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
     * @param CommandsFIle
     * @param OutputFileName
     * @param ReportFileName
     */
    public ECBProcessor(String strPhoneBookFileName, String strCommandsFIle, 
            String strOutputFileName, String strReportFileName)
    {
        m_strPhoneBookFileName = strPhoneBookFileName;
        m_strInstructionFileName = strCommandsFIle;
        m_strOutputFileName = strOutputFileName;
        m_strReportFileName = strReportFileName;
               
        m_ecbInstance = new ECB();
        
        DoTheJob();     
    }
     
    /**
     * The whole job process: Read initial input -> Read and execute instructions from instruction file.
     * @return return 0 if success.
     */
    private int DoTheJob()
    {
        /* Read the phone book file, and construct the ECBEntries. For valid ECBEntires,
         * add them into the ECB, who has an array list to store them.
        */
        ProcessPhoneBookFile();
        
        // Read the instruction file, and execute these instructions.
        ProcessInstructionFile();
        
        //debug
        //m_ecbInstance.__testPrintTheNames();
        
        return 0;
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
            while (sScanner.hasNextLine())
            {
                String strTmpCmd = "add " + sScanner.nextLine();
                CommandExecution ceTmp = new CommandExecution(strTmpCmd, m_ecbInstance, null);        
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
