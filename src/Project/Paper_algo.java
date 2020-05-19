package Project;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;


public class Paper_algo
{
    private int vm_count;
    private int cloudlet_count;
    JTextField[] time;
    JTextField[] cost;
    int index;
    private VM_creater vm_list;
    private Cloudlet_Creater cloudlet_list;

    public Paper_algo(int vm_count, int cloudlet_count, JTextField[] time,JTextField[] cost, int index)
    {
        this.vm_count=vm_count;
        this.cloudlet_count=cloudlet_count;
        this.time=time;
        this.cost=cost;
        this.index=index;
    }

    public void start()
    {
        System.out.println("Starting Paper algorithm...");
        try {
            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;

            CloudSim.init(num_user, calendar, trace_flag);

            Datacenter datacenter0 = createDatacenter("Data_center_0");

            PaperBroker broker = createBroker();
            int brokerId = broker.getId();

            vm_list=new VM_creater(vm_count,brokerId);
            broker.submitVmList(vm_list.getVM_list());

            cloudlet_list = new Cloudlet_Creater(cloudlet_count,brokerId);

            broker.scheduleTaskstoVms(cloudlet_list.getCloudlet_list());

            CloudSim.startSimulation();
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            CloudSim.stopSimulation();

            cost[index].setText(broker.getCost()+"");

            printCloudletList(newList);

            System.out.println("Paper Algorithm finished!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("The simulation has been terminated due to an unexpected error");
        }
    }

    private  Datacenter createDatacenter(String name)
    {
        Datacenter datacenter=new DataCenterCreater().createUserDatacenter(name, vm_count);
        return datacenter;
    }

    private PaperBroker createBroker()
    {

        PaperBroker broker = null;
        try
        {
            broker = new PaperBroker("Paper_Broker");
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return broker;
    }

    private void printCloudletList(List<Cloudlet> list)
    {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        System.out.println();
        System.out.println("========== OUTPUT ==========");
        System.out.println("Cloudlet ID" + indent + "STATUS"  + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time"+ indent+ "waiting time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++)
        {
            cloudlet = list.get(i);
            System.out.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS)
            {
                //System.out.println("SUCCESS");
                System.out.println( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() + indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + dft.format(cloudlet.getFinishTime())+indent + indent + dft.format(cloudlet.getWaitingTime()));
            }
            if(i==size-1)
            {
                time[index].setText(dft.format(cloudlet.getFinishTime()));
            }
        }

    }
}