package test.guess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TxtParser
{
    private static final String DEFAULT_FILE_NAME = "Words.txt";
    
    private String fileName;
    private int length;
    private String validateString;
    
    //private List<String> wordsList;
    private List<String> validWords;
    //private List<String> invalidWords;
    
    
    public TxtParser(int length, String validate)
    {
        this.length = length;
        this.validateString = validate;
    }
    
    public List<String> getValidateWords()
    {
        if (validWords == null || validWords.isEmpty())
        {
            parseFile();
        }
        Collections.sort(validWords);
        return validWords;
    }

    public void setValidateWords(List<String> validateWords)
    {
        this.validWords = validateWords;
    }
    
    
    private void parseFile()
    {
        // generate txt files.
        TxtFileProcessor.process();
        
        Map<String, Integer> valid = pre_Process(validateString);

        Iterator<Entry<String, Integer>> it = valid.entrySet().iterator();

        List<String> validedwords = new ArrayList<String>();
        while (it.hasNext())
        {
            Entry<String, Integer> entry = it.next();

            String location = this.getClass().getPackage().toString().replaceAll("package ", "").replaceAll("\\.", "/");

            String filelocation = location + "/" + entry.getKey() + "_" + length + ".txt";

            URL fileUrl = this.getClass().getClassLoader().getResource("");
            
            File file = new File(fileUrl.getPath().replaceAll("bin", "src") + filelocation);
            
            if (!file.exists())
            {
                continue;
            }

            FileReader fr;
            try
            {
                fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String s;

                while ((s = br.readLine()) != null)
                {
                    if (s.trim().equals(""))
                    {
                        continue;
                    }

                    if (validateWord(s.trim(), validateString))
                    {
                        validedwords.add(s.trim());
                    }
                }

            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.setValidateWords(validedwords);

    }
    
    private boolean validateWord(String source, String condition)
    {
        boolean ret = false;

        Map<String, Integer> conditionmap = pre_Process(condition);
        Map<String, Integer> sourcemap = pre_Process(source);

        Iterator<Entry<String, Integer>> it = sourcemap.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<String, Integer> entry = it.next();
            if (!conditionmap.containsKey(entry.getKey()))
            {
                return false;
            }

            if (entry.getValue() > conditionmap.get(entry.getKey()))
            {
                return false;
            }            
            
        }
        
        ret = true;

        return ret;
    }
    
    private Map<String, Integer> pre_Process(String condition)
    {
        Map<String, Integer> ret = new HashMap<String, Integer>();
        
        for(int i=0; i< condition.length(); i++)
        {
            String s = condition.substring(i, i + 1).toLowerCase();
            if (ret.containsKey(s))
            {
                int count = ret.get(s) + 1;
                ret.put(s, count);
            }
            else
            {
                ret.put(s, new Integer(1));
            }
        }
        
        return ret;
    }
    
}
