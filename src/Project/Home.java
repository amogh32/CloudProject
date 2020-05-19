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
import java.awt.*;

public class Home
{
    public static void main(String[] args)
    {
        JFrame f=new JFrame("Project");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int w=(int)width;
        int h=(int)height;
        f.setSize(w,h);

        JLabel title=new JLabel("Cloud Scheduling");
        title.setFont(new Font("Serif", Font.PLAIN, 50));
        title.setBounds(w/2-200,50,400,60);
        f.add(title);

        SpinnerModel Couldlet_value = new SpinnerNumberModel(10, 4, 1000, 1);
        JSpinner cloudlet_spinner = new JSpinner(Couldlet_value);
        cloudlet_spinner.setBounds(w/2,150,100,30);
        f.add(cloudlet_spinner);

        JLabel cloudlet_title=new JLabel("Number of Cloudlets");
        cloudlet_title.setBounds(w/2-200,150,200,30);
        f.add(cloudlet_title);

        SpinnerModel VM_value = new SpinnerNumberModel(8, 4, 50, 1);
        JSpinner VM_spinner = new JSpinner(VM_value);
        VM_spinner.setBounds(w/2,200,100,30);
        f.add(VM_spinner);

        JLabel VM_title=new JLabel("Number of VM's");
        VM_title.setBounds(w/2-200,200,200,30);
        f.add(VM_title);

        JButton main_btn = new JButton("Submit");
        main_btn.setBounds(w/2,250,100,30);
        f.add(main_btn);

        Algorithms algos=new Algorithms();
        int l=algos.get_size(),i;

        int w1=(int)w/9;

        JLabel[] algo_label=new JLabel[l];
        JTextField[] algo_time=new JTextField[l];
        JTextField[] algo_cost=new JTextField[l];
        JButton[] algo_btn=new JButton[l];

        JLabel[] label =new JLabel[3];
        label[0]=new JLabel("Algorithm");
        label[1]=new JLabel("Time");
        label[2]=new JLabel("Cost");
        label[0].setBounds(w1,350,w1,30);
        label[1].setBounds(3*w1,350,w1,30);
        label[2].setBounds(5*w1,350,w1,30);
        f.add(label[0]);
        f.add(label[1]);
        f.add(label[2]);
        for(i=0;i<l;i++)
        {
            algo_label[i]=new JLabel(algos.get_algo(i));
            algo_time[i]=new JTextField(20);
            algo_cost[i]=new JTextField(20);
            algo_btn[i]=new JButton("Find Value");
            algo_time[i].setEditable(false);
            algo_cost[i].setEditable(false);
            algo_label[i].setBounds(w1,400+50*i,w1,30);
            algo_time[i].setBounds(3*w1,400+50*i,w1,30);
            algo_cost[i].setBounds(5*w1,400+50*i,w1,30);
            f.add(algo_label[i]);
            f.add(algo_time[i]);
            f.add(algo_cost[i]);
            if(algos.get_algo(i).equals("Paper"))
            {

            }
            else
            {
                algo_btn[i].setBounds(7*w1,400+50*i,w1,30);
                f.add(algo_btn[i]);
            }
        }

        main_btn.addActionListener(e->
        {

            new Paper_algo((int)VM_value.getValue(),(int)Couldlet_value.getValue(),algo_time,algo_cost,0).start();
            main_btn.setEnabled(false);
            ((SpinnerNumberModel) VM_value).setMinimum((int)VM_value.getValue());
            ((SpinnerNumberModel) VM_value).setMaximum((int)VM_value.getValue());
            ((SpinnerNumberModel) Couldlet_value).setMinimum((int)Couldlet_value.getValue());
            ((SpinnerNumberModel) Couldlet_value).setMaximum((int)Couldlet_value.getValue());

        });

        /*String twoLines = "Subject wise marks\nof each student";
        JButton b =
                new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");

        b.setBounds((int)(3*w/16),(int)(5*h/12)-25,200, 50);

        f.add(b);


        twoLines = "Total marks\nof each student";
        JButton b1 =
                new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");

        b1.setBounds((int)(13*w/16)-200,(int)(5*h/12)-25,200, 50);

        f.add(b1);

        b1.addActionListener(e->
        {

            new TotalMarks().start();
            f.dispose();

        });

        b.addActionListener(e->
        {

            new SelectSubject().start();
            f.dispose();

        });*/
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
    }
}