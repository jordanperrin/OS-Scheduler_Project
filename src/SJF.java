import java.util.LinkedList;

public class SJF extends PCB {

    //constructor just calls the parent class constructor
    public SJF(){
        super();
    }

    public void start() {
        while(!ReadyList.isEmpty()){
            System.out.println("Current State of ReadyList:"+ RQtoString());
            System.out.println("Current State of IO Queue:"+ IOtoString());
            this.currentP = this.findShortest(); //we find the shortest job in the ReadyList
            this.currentP.markResponse(totalTime); //eberything excutes as FCFS - if anything unclear check comments from FCFS
            System.out.println("[ Excution Time: " + totalTime + "] Process " + this.currentP.getID() + " has ran");
            System.out.print("[Time: " + totalTime + "] Total execution finished: ");
            this.currentP.calculateWaitTime();
            Integer currCPUBurst = this.currentP.runCPU();
            this.addTimeTotoal(currCPUBurst);
            this.goingtoIO();
            this.checkIOqueue();
        }
        totalTime--;
        this.calaculateAverage();
        this.calculateCPUUtil();
        System.out.println("-------------------------------SJF Data-------------------------------");
    }

    //simple function just finds the shortest CPU Burst
    public Process findShortest(){
        Process p_min = this.ReadyList.peek();
        Integer min = p_min.getCPUBurst();
        for(Process temp_p : ReadyList){
            Integer temp_int = temp_p.getCPUBurst();
            if(temp_int < min){
                min = temp_int;
                p_min = temp_p;
            }else if(temp_int == min){
                if(temp_p.CPUtimestart < p_min.CPUtimestart)
                    p_min = temp_p;
            }
        }
        this.ReadyList.remove(p_min);
        return p_min;
    }

    //here we needed Ovveride this function from parent class PCB
    //the idea
    @Override
    public void checkIOqueue(){
       super.checkIOqueue();
        if(!IOqueue.isEmpty()){
            LinkedList<Process> cleanout = new LinkedList<>();
            for(Process p: IOqueue){
                if(p.IOwait <= this.totalTime){
                    this.ReadyList.add(p);
                    cleanout.add(p);
                }
            }

            //the idea w this function is to clear out all IOs that <= to the total time
            for(Process p : cleanout){
                this.IOqueue.remove(p);
            }
        }

    }

}
