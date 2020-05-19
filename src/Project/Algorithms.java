package Project;

import java.util.ArrayList;

public class Algorithms
{
    private static ArrayList<String> algos=new ArrayList<String>();
    private int size;


    public Algorithms()
    {
        algos.add("Paper");

        size=algos.size();
    }

    public String get_algo(int i)
    {
        return algos.get(i);
    }

    public int get_size()
    {
        return size;
    }
}