package Project;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;

public class Cost_Grouping
{
    private Common common=new Common();
    private ArrayList<Cloudlet> cost_cloudlet_list;
    private ArrayList<Vm> cost_vm_list;
    private ArrayList<Cloudlet> output_cloudlet=new ArrayList<Cloudlet>();
    private ArrayList<Integer> output_vm=new ArrayList<Integer>();
    private int cloudlet_count;
    private int vm_count;
    private ArrayList<Cloudlet> high=new ArrayList<Cloudlet>();
    private ArrayList<Cloudlet> medium=new ArrayList<Cloudlet>();
    private ArrayList<Cloudlet> low=new ArrayList<Cloudlet>();
    private double cost=0;

    public Cost_Grouping(ArrayList<Cloudlet> cost_cloudlet_list, ArrayList<Vm> cost_vm_list)
    {
        this.cost_cloudlet_list=cost_cloudlet_list;
        this.cost_vm_list=cost_vm_list;
        cloudlet_count=cost_cloudlet_list.size();
        vm_count=cost_vm_list.size();
        schedule();
    }

    private void schedule()
    {
        int i,j,k;

        for(i=0;i<cloudlet_count;i++)
        {
            if(common.get_priority(cost_cloudlet_list.get(i).getCloudletId()%common.getSize())==0)
            {
                high.add(cost_cloudlet_list.get(i));
            }
            else if(common.get_priority(cost_cloudlet_list.get(i).getCloudletId()%common.getSize())==1)
            {
                medium.add(cost_cloudlet_list.get(i));
            }
            else if(common.get_priority(cost_cloudlet_list.get(i).getCloudletId()%common.getSize())==2)
            {
                low.add(cost_cloudlet_list.get(i));
            }
        }
        sort_list(high);
        sort_list(medium);
        sort_list(low);
        int hw=common.get_weight(0);
        int mw=common.get_weight(1);
        int lw=common.get_weight(2);
        while(true)
        {
            sch(hw,high);
            sch(mw,medium);
            sch(lw,low);
            if(high.isEmpty()&&medium.isEmpty()&&low.isEmpty())
            {
                break;
            }
        }
    }

    private void sch(int w,ArrayList<Cloudlet> list)
    {
        int i,j,k=0;
        double t;
        int si=list.size();
        for(i=0;i<Math.min(w,si);i++)
        {
            double min = -1.0;
            for(j=0;j<vm_count;j++)
            {
                t=VM_creater.get_cost(cost_vm_list.get(j).getId(),(double)list.get(0).getCloudletLength()/(cost_vm_list.get(j).getMips()*cost_vm_list.get(j).getNumberOfPes()));
                if(j==0)
                {
                    min=t;
                    k=j;
                }
                else if(t<min)
                {
                    min=t;
                    k=j;
                }
                //System.out.print(t+" ");
            }
            //System.out.println();
            output_cloudlet.add(list.get(0));
            output_vm.add(cost_vm_list.get(k).getId());
            list.remove(0);
            VM_creater.setCur();
            //System.out.println(min);
            cost+=min;
        }
        //System.out.println();
    }

    private void sort_list(ArrayList<Cloudlet> list)
    {
        int i,j,s;
        s=list.size();
        Cloudlet temp;
        for(i=0;i<s;i++)
        {
            for(j=0;j<s-1-i;j++)
            {
                if(list.get(j).getCloudletLength()<list.get(j+1).getCloudletLength())
                {
                    temp=list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                }
            }
        }
    }

    public ArrayList<Cloudlet> getOutput_cloudlet()
    {
        return output_cloudlet;
    }

    public ArrayList<Integer> getOutput_vm()
    {
        return output_vm;
    }

    public double getCost()
    {
        return cost;
    }
}