import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * CommandExecution: Command execution. Parse the commands. For each line in command file, one object will
 * be created.
 * 
 * @author Chen Zhuge
 * @version 0.02
 * @last updated on 20180521
 */
public class CommandExecution
{
    final String CMD_ADD = "add";
    final String CMD_DELETE = "delete";
    final String CMD_QUERY = "query";
    final String CMD_SAVE = "save";
    
    private String m_strCommand;
    private ECB m_ecbInstance;
    private String m_strAttachInfo;
    
    /**
     * Execute a line of instruction from the instruction file.
     * @param strCommand Original line from the instruction file.
     * @param ecbTarget ECB class's instance.
     * @param strAttachInfo Providing additional information, such as output and report files path. 
     */
    public CommandExecution(String strCommand, ECB ecbTarget, String strAttachInfo)
    {
        // TODO Auto-generated constructor stub
        m_strCommand = strCommand;
        m_ecbInstance = ecbTarget;
        m_strAttachInfo = strAttachInfo;
   
        String[] aryStrInfo = strCommand.split(" ");
        
        if (aryStrInfo[0].equalsIgnoreCase(CMD_ADD))
        {
            CommandExecutionAdd();         
        }
        else if (aryStrInfo[0].equalsIgnoreCase(CMD_DELETE))
        {
            CommandExecutionDelete();           
        }
        else if (aryStrInfo[0].equalsIgnoreCase(CMD_QUERY))
        {
            CommandExecutionQuery();
        }
        else if (aryStrInfo[0].equalsIgnoreCase(CMD_SAVE))
        {
            CommandExecutionSave();
        }
            
    }
    
    /** Add command.
     * 
     */
    private void CommandExecutionAdd()
    {
        String strCmdContent = m_strCommand.substring(4);
        ECBEntry tmp = new ECBEntry(strCmdContent);
        
        if (tmp.GetValidation() == false)
        {
            return;
        }
        
        int nIndex = IfAlreadyExist(tmp, m_ecbInstance);
        
        /* If the entry is already in the array list, first remove it, then add the new instance 
        into the array list.*/ 
        if (nIndex != -1)
        {
            m_ecbInstance.GetEntriesList().remove(nIndex);
        }
        
        m_ecbInstance.GetEntriesList().add(tmp);
        
        return;
    }
    
    /** Delete command.
     * 
     */
    private void CommandExecutionDelete()
    {
        String strCmdContent = m_strCommand.substring(7);
        String[] aryCmdContentLst = strCmdContent.split(";");
        
        // delete command with only name as the identification.
        if (aryCmdContentLst.length == 1)
        {
            int nTargetIndex = GetTargetIndex(aryCmdContentLst[0], m_ecbInstance);
            while ( nTargetIndex != -1)
            {
                m_ecbInstance.GetEntriesList().remove(nTargetIndex);
                nTargetIndex = GetTargetIndex(aryCmdContentLst[0], m_ecbInstance);
            }
        }
        // delete command with name and birthday as the identification.
        else if (aryCmdContentLst.length == 2)
        {
            CustomDate cdTmp = new CustomDate(aryCmdContentLst[1]); 
            
            //invalid birthday check
            if (cdTmp.GetDate() == null)
            {
                return;
            }
            
            int nTargetIndex = GetTargetIndex(aryCmdContentLst[0], cdTmp, m_ecbInstance);
            
            while ( nTargetIndex != -1)
            {
                m_ecbInstance.GetEntriesList().remove(nTargetIndex);
                nTargetIndex = GetTargetIndex(aryCmdContentLst[0], cdTmp, m_ecbInstance);
            }
        }
        
        return;
    }
    
    /** Query command.
     * 
     */
    private void CommandExecutionQuery()
    {
        String strCmdContent = m_strCommand.substring(6);
        ArrayList<ECBEntry> alStorage = new ArrayList<ECBEntry>();
        
        String[] aryStrInfo = strCmdContent.split(" ");
        
        if (aryStrInfo[0].equalsIgnoreCase(ECBEntry.TAG_NAME))
        {
            String strTarget = strCmdContent.substring(5);
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
              if (e.GetName().equalsIgnoreCase(strTarget)) 
              {
                  alStorage.add(e);
              }
            }
        }
        else if (aryStrInfo[0].equalsIgnoreCase(ECBEntry.TAG_BRITHDAY))
        {
            String strTarget = strCmdContent.substring(9);
            CustomDate tmp = new CustomDate(strTarget);
            
            if (tmp.GetDate() == null)
            {
                //invalid
                return;
            }
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
              if (e.GetBirthday().GetValue() == tmp.GetValue()) 
              {
                  alStorage.add(e);
              }
            }
            
        }
        else if (aryStrInfo[0].equalsIgnoreCase(ECBEntry.TAG_PHONE))
        {
            String strTarget = strCmdContent.substring(6);
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
              if (e.GetPhone().equalsIgnoreCase(strTarget)) 
              {
                  alStorage.add(e);
              }
            }
        }
        else if (aryStrInfo[0].equalsIgnoreCase(ECBEntry.TAG_ADDRESS))
        {
            String strTarget = strCmdContent.substring(8);
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
              if (e.GetAddress().equalsIgnoreCase(strTarget)) 
              {
                  alStorage.add(e);
              }
            }
        }
        else if (aryStrInfo[0].equalsIgnoreCase(ECBEntry.TAG_EMAIL))
        {
            String strTarget = strCmdContent.substring(6);
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
              if (e.GetEmail().equalsIgnoreCase(strTarget)) 
              {
                  alStorage.add(e);
              }
            }
        }
        
        SortByNameThenBirthday(alStorage);
        
        String[] aryStrFilePath = m_strAttachInfo.split(";");
        String strReportFilePath = aryStrFilePath[1];
        
        try
        {
            FileWriter fwAppendWriter = new FileWriter(strReportFilePath, true);
            PrintWriter pwWriter = new PrintWriter(fwAppendWriter);
            
            pwWriter.printf("====== %s ======\r\n", m_strCommand);
            for (ECBEntry e : alStorage)
            {
                pwWriter.printf("%s", e.GetInfo());
            }
            pwWriter.printf("====== %s%s ======\r\n\r\n", "end of ", m_strCommand);
            
            pwWriter.close();
  
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        
        //debug
//        for (ECBEntry e : alStorage)
//        {
//            System.out.println(e.GetName() + " with " + e.GetBirthday().GetDate());
//        }
      
        return;
    }
    
    public void CommandExecutionSave()
    {
        String[] aryStrFilePath = m_strAttachInfo.split(";");
        String strReportFilePath = aryStrFilePath[0];
        
        try
        {
            File fwWriter = new File(strReportFilePath);
            PrintWriter pwWriter = new PrintWriter(fwWriter);
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
                pwWriter.printf("%s", e.GetInfoOneLine());
                pwWriter.printf("\r\n");    //blank line between entries.
            }
            
            pwWriter.close();
  
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        
        
        //print out a reader-friendly file as well.
        String strReprotFileFriendlyPath = strReportFilePath.substring(0, strReportFilePath.length() - 6);
        strReprotFileFriendlyPath += "Friendly.txt";
        
        try
        {
            File fwWriter = new File(strReprotFileFriendlyPath);
            PrintWriter pwWriter = new PrintWriter(fwWriter);
            
            for (ECBEntry e : m_ecbInstance.GetEntriesList())
            {
                pwWriter.printf("%s", e.GetInfo());
                pwWriter.printf("\r\n");    //blank line between entries.
            }
            
            pwWriter.close();
  
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        
        return;
    }  
        
    private void SortByNameThenBirthday(ArrayList<ECBEntry> alToSort)
    {
        for (int i = 0; i < alToSort.size(); i++)
        {
            for (int j = 0; j < alToSort.size() - i - 1; j++)
            {
                if (IsEntryBigger(alToSort.get(j), alToSort.get(j + 1)))
                {
                    ECBEntry tmp = null;
                    tmp = alToSort.get(j + 1);
                    alToSort.set(j + 1, alToSort.get(j));
                    alToSort.set(j, tmp);     
                }
            }
        }
        
        return;
    }
    
    private boolean IsEntryBigger(ECBEntry ecbe1, ECBEntry ecbe2)
    {
        if (ecbe1.GetName().compareToIgnoreCase(ecbe2.GetName()) > 0)
        {
            return true;
        }
        
        if (ecbe1.GetName().compareToIgnoreCase(ecbe2.GetName()) == 0)
        {
            if (ecbe1.GetBirthday().GetValue() > ecbe2.GetBirthday().GetValue())
            {
                return true;
            }
        }
        
        
        return false;
    }
    
    /**
     * Query. Parse the recording from file m_strInstructionFileName as the input.
     * @param strCategory name, birthday, phone, address, email.
     * @param strContent content to match.
     * @return array list that contains all the matched contacts.
     */
//    private ArrayList<ECBEntry> QueryEntry(String strCategory, String strContent)
//    {
//        return null;
//    }
    
    
    
    /**
     * Find the index of a target in the array list by matching its name.
     * @param strName
     * @param ecbInstance
     * @return If found, return its index in array list. If not found, return -1.
     */
    private int GetTargetIndex(String strName, ECB ecbInstance)
    {
        for (int i = 0; i < ecbInstance.GetEntriesList().size(); i++)
        {
            if (ecbInstance.GetEntriesList().get(i).GetName().equalsIgnoreCase(strName))
            {
                return i;
            }
        }
                
        return -1;
    }
    
    /**
     * Find the index of a target in the array list by matching both its name and birthday.
     * @param strName
     * @param cdDate
     * @param ecbInstance
     * @return If found, return its index in array list. If not found, return -1.
     */
    private int GetTargetIndex(String strName, CustomDate cdDate, ECB ecbInstance)
    {
        for (int i = 0; i < ecbInstance.GetEntriesList().size(); i++)
        {
            if (ecbInstance.GetEntriesList().get(i).GetName().equalsIgnoreCase(strName) &&
                ecbInstance.GetEntriesList().get(i).GetBirthday().GetValue() == 
                cdDate.GetValue())
            {
                return i;
            }
        }
           
        return -1;
    }
    
    
    /**
     * Check if the instance is already in the array list.
     * @param ecbeInstance
     * @return If yes, return its index. If no, return -1.
     */
    private int IfAlreadyExist(ECBEntry ecbeInstance, ECB ecbInstance)
    {
        
        for (int i = 0; i < ecbInstance.GetEntriesList().size(); i++)
        {
            if (ecbInstance.GetEntriesList().get(i).GetName().equalsIgnoreCase(ecbeInstance.GetName()) &&
                ecbInstance.GetEntriesList().get(i).GetBirthday().GetValue() == 
                ecbeInstance.GetBirthday().GetValue())
            {
                return i;
            }
        }
        
        return -1;
    }
}
 