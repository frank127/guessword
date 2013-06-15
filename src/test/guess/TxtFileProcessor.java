package test.guess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TxtFileProcessor
{
    private static final String[] alphabetic = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        process();
    }
    
    /**
     * will process txt file based on letters 
     * a.txt will be broken down to a_1.txt, a_2.txt, ... a_8.txt
     */
    public static void process()
    {
        // class package location
        String location = TxtFileProcessor.class.getPackage().toString().replaceAll("package ", "").replaceAll("\\.", "/");

        String readFileName;
        String readFileLocation;
        URL readFileUrl;
        File readFile;
        FileReader fr;

        String wrFileName;
        String wrFileLocation;
        URL wrFileUrl;
        File wrFile;
        FileWriter fw;

        for (String letter : alphabetic)
        {
            readFileName = letter + ".txt";   // a.txt
            readFileLocation = location + "/" + readFileName;  // package + a.txt
            readFileUrl = TxtFileProcessor.class.getClassLoader().getResource(readFileLocation);  // get path
            
            if (readFileUrl == null)
            {
                continue;
            }
            
            readFile = new File(readFileUrl.getPath().replaceAll("bin", "src"));  //read file

            if (!readFile.exists())  
            {
                // file doesnt exist, skip to next letter
                continue;
            }

            try
            {
                fr = new FileReader(readFile);
                BufferedReader br = new BufferedReader(fr);
                BufferedWriter bw;
                String s = null;

                int index = 0;
                List<String> oldString = new ArrayList<String>();
                while ((s = br.readLine()) != null)
                {
                    s = s.trim();
                    if (s.equals("") || s.length() > 8)
                    {
                        // if current line is emtyp or longer than 8, its not valid line.
                        continue;
                    }

                    if (index == 0 || index == s.length())
                    {
                        index = s.length();
                        oldString.add(s);
                    }
                    else
                    {
                        // write to the file
                        wrFileName = letter + "_" + index + ".txt";
                        wrFileLocation = readFileUrl.getPath().replaceAll("bin", "src");
                        wrFileLocation = wrFileLocation.replaceAll(letter + ".txt", letter + "_" + index + ".txt");
                        
                        writeToFile(wrFileLocation, oldString);
                        
                        index = s.length();
                        oldString.clear();
                        oldString.add(s);
                    }                   
                    
                }
                if (!oldString.isEmpty())
                {
                    wrFileName = letter + "_" + index + ".txt";
                    wrFileLocation = readFileUrl.getPath().replaceAll("bin", "src");
                    wrFileLocation = wrFileLocation.replaceAll(letter + ".txt", letter + "_" + index + ".txt");
                    
                    writeToFile(wrFileLocation, oldString);
                    oldString.clear();
                }

                fr.close();
                
                System.out.println(letter + " Done");
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
    }
    
    public static void writeToFile(String filePath, List<String> output)
    {
        File file = new File(filePath);

        if (file.exists())
        {
            file.delete();
        }

        try
        {
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String tmp : output)
            {
                bw.write(tmp);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       

    }

}
