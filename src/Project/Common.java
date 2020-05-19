package Project;

import java.util.HashMap;
import java.util.Map;

public class Common
{
    private int[] priority={0,1,0,2,1,0};
    private Map<Integer,Integer> weight=new HashMap<Integer,Integer>();
    private int size;


    public Common()
    {
        weight.put(0,3);
        weight.put(1,2);
        weight.put(2,1);
        size=priority.length;
    }

    public int getSize()
    {
        return size;
    }

    public int get_priority(int i)
    {
        return priority[i];
    }

    public int get_weight(int i)
    {
        return weight.get(i);
    }
}