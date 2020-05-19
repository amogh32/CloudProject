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

import java.util.ArrayList;

public class Cloudlet_Creater
{
    private ArrayList<Cloudlet> cloudlet_list=new ArrayList<Cloudlet>();
    private int size;

    public Cloudlet_Creater(int num_tasks,int broker_id)
    {
        int cloudlet_id ;
        int pesNumber=1;
        long[] length = {30000, 27000, 29000, 32000, 33000, 27000, 26000, 29500, 31200, 31500};
        long fileSize = 300;
        int mod=length.length;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        for(cloudlet_id=0;cloudlet_id<num_tasks;cloudlet_id++)
        {
            Cloudlet task = new Cloudlet(cloudlet_id, length[cloudlet_id%mod], pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            task.setUserId(broker_id);
            cloudlet_list.add(task);
        }
        size=cloudlet_list.size();
        System.out.println("Cloudlets created successfully");
    }

    public ArrayList<Cloudlet> getCloudlet_list()
    {
        return cloudlet_list;
    }
}