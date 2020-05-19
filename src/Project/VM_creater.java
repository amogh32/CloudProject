package Project;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;

public class VM_creater
{
    private ArrayList<Vm> VM_list=new ArrayList<Vm>();
    private int size_list;
    private static double[] cost={1.01,0.99,1.05,0.985,1.005,1.015};
    private static int mod=6;


    public VM_creater(int num_vm,int broker_id)
    {
        int vm_id ;
        long[] mips = {500,490,505,495,493,503};

        long size = 1000;
        mod=mips.length;
        int ram = 64;
        long bw = 1000;
        int[] pesNumber = {1,1,1,1,1,1};
        UtilizationModel utilizationModel = new UtilizationModelFull();
        for(vm_id=0;vm_id<num_vm;vm_id++)
        {
            VM_list.add(new Vm(vm_id, broker_id, mips[vm_id%mod], pesNumber[vm_id%mod], ram, bw, size, "VM"+vm_id, new CloudletSchedulerSpaceShared()));
        }

        System.out.println("Vm's created successfully");
        size_list=VM_list.size();

    }

    public ArrayList<Vm> getVM_list()
    {
        return VM_list;
    }

    public static double get_cost(int i,double b)
    {
        double a=(0.012+i*i*i)*b-i*i;
        return a;
    }

    public static int getMod()
    {
        return mod;
    }
}