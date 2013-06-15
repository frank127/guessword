package test.guess;

import java.util.List;

public class TestReader
{
    private static final String validate = "aniywptolsor";
    private static int word_length = 3;

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        TxtParser parser = new TxtParser(word_length, validate);
        
        List<String> txtValidate = parser.getValidateWords();
        System.out.println("Condition: " + validate + " ------------------------------------------");

        System.out.println("Valid------------------------------------------");
        for (int i = 0; i < txtValidate.size(); i++)
        {
            if ((i % 10) == 0 && i > 0)
            {
                System.out.println();
            }
            System.out.print((i + 1) + ": " + txtValidate.get(i) + " ");
        }


    }

}
