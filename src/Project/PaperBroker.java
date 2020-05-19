package Project;

import java.util.ArrayList;
import java.util.Collections;


import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEvent;

public class PaperBroker extends DatacenterBroker
{

    private ArrayList<Cloudlet> original_list;

    private double cost=0;

    public PaperBroker(String name) throws Exception
    {
        super(name);
    }

    public void scheduleTaskstoVms(ArrayList<Cloudlet> list)
    {
        ArrayList<Cloudlet>clist=new ArrayList<Cloudlet>();
        original_list=list;
        int num_cloudlet=original_list.size();
        int num_vm=vmList.size();
        int i,j,k;
        ArrayList<Cloudlet> time_cloudlet_list=new ArrayList<Cloudlet>();
        ArrayList<Cloudlet> cost_cloudlet_list=new ArrayList<Cloudlet>();
        ArrayList<Vm> time_vm_list=new ArrayList<Vm>();
        ArrayList<Vm> cost_vm_list=new ArrayList<Vm>();
        if(num_cloudlet%2==0)
        {
            j=num_cloudlet/2;
        }
        else
        {
            j=(num_cloudlet+1)/2;
        }
        if(num_vm%2==0)
        {
            k=num_vm/2;
        }
        else
        {
            k=(num_vm+1)/2;
        }
        for(i=0;i<j;i++)
        {
            time_cloudlet_list.add(original_list.get(i));
        }
        for(i=j;i<num_cloudlet;i++)
        {
            cost_cloudlet_list.add(original_list.get(i));
        }
        for(i=0;i<k;i++)
        {
            time_vm_list.add(vmList.get(i));
        }
        for(i=k;i<num_vm;i++)
        {
            cost_vm_list.add(vmList.get(i));
        }
        Time_Grouping time =new Time_Grouping(time_cloudlet_list,time_vm_list);
        Cost_Grouping cost1=new Cost_Grouping(cost_cloudlet_list,cost_vm_list);
        ArrayList<Cloudlet> timecl=time.getOutput_cloudlet();
        ArrayList<Integer>timevm=time.getOutput_vm();
        ArrayList<Cloudlet> costcl=cost1.getOutput_cloudlet();
        ArrayList<Integer>costvm=cost1.getOutput_vm();
        for(i=0;i<timecl.size();i++)
        {
            clist.add(timecl.get(i));
        }
        for(i=0;i<costcl.size();i++)
        {
            clist.add(costcl.get(i));
        }

        cost+=time.getCost();
        cost+=cost1.getCost();
        submitCloudletList(clist);
        for(i=0;i<timecl.size();i++)
        {
            bindCloudletToVm(timecl.get(i).getCloudletId(),timevm.get(i));
        }
        for(i=0;i<costcl.size();i++)
        {
            //System.out.println(costcl.get(i).getCloudletId()+" "+costvm.get(i));
            bindCloudletToVm(costcl.get(i).getCloudletId(),costvm.get(i));
        }

    }

    public void scheduleTaskstoVms1()
    {
        int reqTasks=cloudletList.size();
        int reqVms=vmList.size();
        Vm vm = vmList.get(0);


        for(int i=0;i<reqTasks;i++){
            bindCloudletToVm(i, (i%reqVms));
            System.out.println("Task"+cloudletList.get(i).getCloudletId()+" is bound with VM"+vmList.get(i%reqVms).getId());
        }

        //System.out.println("reqTasks: "+ reqTasks);

        ArrayList<Cloudlet> list = new ArrayList<Cloudlet>();
        for (Cloudlet cloudlet : getCloudletReceivedList()) {
            list.add(cloudlet);

        }

        //setCloudletReceivedList(null);

        Cloudlet[] list2 = list.toArray(new Cloudlet[list.size()]);

        //System.out.println("size :"+list.size());

        Cloudlet temp= null;


        int n = list.size();


        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){

                if(list2[j-1].getCloudletLength() / (vm.getMips()*vm.getNumberOfPes()) > list2[j].getCloudletLength() / (vm.getMips()*vm.getNumberOfPes())){
                    //swap the elements!
                    //swap(list2[j-1], list2[j]);
                    temp= list2[j-1];
                    list2[j-1]= list2[j];
                    list2[j]=temp;
                }
                // printNumbers(list2);
            }
        }

        ArrayList<Cloudlet> list3 = new ArrayList<Cloudlet>();

        for(int i=0;i<list2.length;i++){
            list3.add(list2[i]);
        }
        //printNumbers(list);

        setCloudletReceivedList(list);


        //System.out.println("\n\tSJFS Broker Schedules\n");



        //System.out.println("\n");
    }



    public void printNumber(Cloudlet[] list)
    {
        for(int i=0; i<list.length;i++){
            System.out.print(" "+ list[i].getCloudletId());
            System.out.println(list[i].getCloudletStatusString());
        }
        System.out.println();
    }

    public void printNumbers(ArrayList<Cloudlet> list)
    {
        for(int i=0; i<list.size();i++){
            System.out.print(" "+ list.get(i).getCloudletId());
        }
        System.out.println();
    }




    protected void cloudletExecution(Cloudlet cloudlet)
    {

        if (getCloudletList().size() == 0 && cloudletsSubmitted == 0)
        {
            System.out.println(CloudSim.clock() + ": " + getName() + ": All Cloudlets executed. Finishing...");
            clearDatacenters();
            finishExecution();
        }
        else
         {
            if (getCloudletList().size() > 0 && cloudletsSubmitted == 0)
            {
                clearDatacenters();
                createVmsInDatacenter(0);
            }

        }
    }

    public double getCost()
    {
        return cost;
    }


}
