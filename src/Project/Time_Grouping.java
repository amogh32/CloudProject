package Project;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;

public class Time_Grouping
{
    private Common common=new Common();
    private ArrayList<Cloudlet> time_cloudlet_list;
    private ArrayList<Vm> time_vm_list;
    private ArrayList<Cloudlet> output_cloudlet=new ArrayList<Cloudlet>();
    private ArrayList<Integer> output_vm=new ArrayList<Integer>();
    private int cloudlet_count;
    private int vm_count;
    private ArrayList<Cloudlet> high=new ArrayList<Cloudlet>();
    private ArrayList<Cloudlet> medium=new ArrayList<Cloudlet>();
    private ArrayList<Cloudlet> low=new ArrayList<Cloudlet>();
    private double[] wait;
    private double cost;

    public Time_Grouping(ArrayList<Cloudlet> time_cloudlet_list, ArrayList<Vm> time_vm_list)
    {
        this.time_cloudlet_list=time_cloudlet_list;
        this.time_vm_list=time_vm_list;
        cloudlet_count=time_cloudlet_list.size();
        vm_count=time_vm_list.size();
        wait=new double[vm_count];
        cost=0;
        schedule();
    }

    private void schedule()
    {
        int i,j,k;
        for (i=0;i<vm_count;i++)
        {
            wait[i]=0;
        }
        for(i=0;i<cloudlet_count;i++)
        {
            if(common.get_priority(time_cloudlet_list.get(i).getCloudletId()%common.getSize())==0)
            {
                high.add(time_cloudlet_list.get(i));
            }
            else if(common.get_priority(time_cloudlet_list.get(i).getCloudletId()%common.getSize())==1)
            {
                medium.add(time_cloudlet_list.get(i));
            }
            else if(common.get_priority(time_cloudlet_list.get(i).getCloudletId()%common.getSize())==2)
            {
                low.add(time_cloudlet_list.get(i));
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
            //System.out.print(list.get(0).getCloudletId());
            double min = -1.0;
            for(j=0;j<vm_count;j++)
            {
                t=wait[j]+(double)list.get(0).getCloudletLength()/(time_vm_list.get(j).getMips()*time_vm_list.get(j).getNumberOfPes());
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
            }
            output_cloudlet.add(list.get(0));
            output_vm.add(time_vm_list.get(k).getId());
            double temp=(double)list.get(0).getCloudletLength()/(time_vm_list.get(k).getMips()*time_vm_list.get(k).getNumberOfPes());
            cost+=VM_creater.get_cost(time_vm_list.get(k).getId(),temp);
            wait[k]=min;
            //System.out.println(min+" "+k+" "+list.get(0).getCloudletId()+" "+time_vm_list.get(k).getMips());
            list.remove(0);
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
            //System.out.println(list.get(i).getCloudletId());
            for(j=0;j<s-1-i;j++)
            {
                if(list.get(j).getCloudletLength()>list.get(j+1).getCloudletLength())
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