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
    private static int cur=1;
    private static int m=0;
    private static int num_vm;


    public VM_creater(int num_vm,int broker_id)
    {
        int vm_id ;
        this.num_vm=num_vm;
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
        setm();
    }

    public ArrayList<Vm> getVM_list()
    {
        return VM_list;
    }

    public static double get_cost(int i,double b)
    {
        double a=(cur+(i%mod)*m)%num_vm+1;
        return a;
    }

    public static void reset()
    {
        cur=1;
        m=0;
        setm();
    }

    public static void setCur()
    {
        int i,j;
        for(j=1;j<num_vm;j++)
        {
            if(gcd((cur+j)%num_vm,num_vm)==1)
            {
                cur=(cur+j)%num_vm;
                return;
            }
        }
        setm();
    }

    private static void setm()
    {
        int i,j;
        if(m==0)
        {
            for(i=num_vm/2+1;i<=num_vm;i++)
            {
                if(gcd(i,num_vm)==1)
                {
                    m=i;
                    return;
                }
            }
        }
        for(j=1;j<num_vm;j++)
        {
            if(gcd((m+j)%num_vm,num_vm)==1)
            {
                m=(m+j)%num_vm;
                return;
            }
        }
    }

    public static int getMod()
    {
        return mod;
    }

    private static int gcd(int a,int b)
    {
        if(b==0)
        {
            return a;
        }
        return gcd(b,a%b);
    }
}