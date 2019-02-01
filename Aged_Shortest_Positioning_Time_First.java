import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/*
 * Sectors read from 0 to 99
 * Tracks read from 0 to 49
 * Assumption: all request are valid 
 */

public class ASPTF_OPT {

	Disk disk;
	int curr_sector;
	int curr_track;
	LinkedList<Request> reqList;
	LinkedList<Request> list1,list2,list3,list4;

	public ASPTF_OPT(Disk disk, LinkedList<Request> reqList , int curr_sector, int curr_track){
		this.disk = disk;
		this.reqList = new LinkedList<Request>();
		this.reqList = reqList;
		this.curr_sector = curr_sector;
		this.curr_track = curr_track;
		list1 = new LinkedList<Request>();
		list2 = new LinkedList<Request>();
		list3 = new LinkedList<Request>();
		list4 = new LinkedList<Request>();
		list4 = reqList;
		setUp();
	}
	
	public void setUp(){
		updatePosTime();
		updateOrder();
	}
	
	public void updatePosTime(){
		int i =0;
		int seekTime, rotTime;
		for(i=0;i<list1.size();i++){
			if((list1.get(i).sector - curr_sector) > 0){
				rotTime = list1.get(i).sector - curr_sector;
			}
			else{
				rotTime = 100 + list1.get(i).sector - curr_sector;
			}
			if((list1.get(i).track - curr_track) > 0){
				seekTime = (list1.get(i).track - curr_track);
			}
			else{
				seekTime = 50 + (list1.get(i).track - curr_track);
			}
			list1.get(i).posTime = seekTime*disk.stFactor 
					+ rotTime*disk.rotLat;
		}
		for(i=0;i<list2.size();i++){
			if((list2.get(i).sector - curr_sector) > 0){
				rotTime = list2.get(i).sector - curr_sector;
			}
			else{
			    rotTime = 100 + list2.get(i).sector - curr_sector;
			}
			if((list2.get(i).track - curr_track) > 0){
				seekTime = (list2.get(i).track - curr_track);
			}
			else{
				seekTime = 50 + (list2.get(i).track - curr_track);
			}
			list2.get(i).posTime = seekTime*disk.stFactor 
					+ rotTime*disk.rotLat;
		}
		for(i=0;i<list3.size();i++){
			if((list3.get(i).sector - curr_sector) > 0){
				rotTime = list3.get(i).sector - curr_sector;
			}
			else{
				rotTime = 100 + list3.get(i).sector - curr_sector;
			}
			if((list3.get(i).track - curr_track) > 0){
				seekTime = (list3.get(i).track - curr_track);
			}
			else{
				seekTime = 50 + (list3.get(i).track - curr_track);
			}
			list3.get(i).posTime = seekTime*disk.stFactor 
					+ rotTime*disk.rotLat;
		}
		for(i=0;i<list4.size();i++){
			if((list4.get(i).sector - curr_sector) > 0){
				rotTime = list4.get(i).sector - curr_sector;
			}
			else{
				rotTime = 100 + list4.get(i).sector - curr_sector;
			}
			if((list4.get(i).track - curr_track) > 0){
				seekTime = (list4.get(i).track - curr_track);
			}
			else{
				seekTime = 50 + (list4.get(i).track - curr_track);
			}
			list4.get(i).posTime = seekTime*disk.stFactor 
					+ rotTime*disk.rotLat;
		}
	}
	public LinkedList<Request> updateOrder(){
		
		int i =0;
		for(i=0;i<list4.size();i++){
			if(list4.get(i).age >= 20){
				list3.add(list4.get(i));
				list4.remove(i);
				i--;
			}
		}
		for(i=0;i<list3.size();i++){
			if(list3.get(i).age >= 25){
				list2.add(list3.get(i));
				list3.remove(i);
				i--;
			}
		}
		for(i=0;i<list2.size();i++){
			if(list2.get(i).age >= 30){
				list1.add(list2.get(i));
				list2.remove(i);
				i--;
			}
		}
		
		sortList(list4);
		sortList(list1);
		sortList(list2);
		sortList(list3);
		
		if(!list1.isEmpty())
			for(i=0; i< list1.size(); i++){
				System.out.println( "List1: " + list1.get(i).arrivalTime + " " + list1.get(i).posTime + " " + list1.get(i).age);
			}
		if(!list2.isEmpty())
			for(i=0; i< list2.size(); i++){
				System.out.println( "List2: " + list2.get(i).arrivalTime + " " + list2.get(i).posTime + " " + list2.get(i).age);
			}
		if(!list3.isEmpty())
			for(i=0; i< list3.size(); i++){
				System.out.println( "List3: " + list3.get(i).arrivalTime + " " + list3.get(i).posTime + " " + list3.get(i).age);
			}
		if(!list4.isEmpty()){
			for(i=0; i< list4.size(); i++){
				System.out.println( "List4: " + list4.get(i).arrivalTime + " " + list4.get(i).posTime + " " + list4.get(i).age);
			}
		}
		System.out.println();
		return reqList;
	}
	
	
	public void sortList(LinkedList<Request> reqList){
		Collections.sort(reqList);
		
	}
	public void aging(){
		int i=0;
		if(!list1.isEmpty()){
			for(i=0;i<list1.size();i++){
				list1.get(i).age++;
			}
		}
		if(!list2.isEmpty()){
			for(i=0;i<list2.size();i++){
				list2.get(i).age++;
			}
		}
		if(!list3.isEmpty()){
			for(i=0;i<list3.size();i++){
				list3.get(i).age++;
			}
		}
		if(!list4.isEmpty()){
			for(i=0;i<list4.size();i++){
				list4.get(i).age++;
			}
		}
	}
	
	public void executeRequest(){
		//int sector, track;
		boolean reqExecuted = false;
		if(!list1.isEmpty()){
			curr_sector = list1.getFirst().sector;
			curr_track = list1.getFirst().track;
			list1.pop();
			reqExecuted = true;
		}else if(!list2.isEmpty()){
			curr_sector = list2.getFirst().sector;
			curr_track = list2.getFirst().track;
			list2.pop();
			reqExecuted = true;
		}else if(!list3.isEmpty()){
			curr_sector = list3.getFirst().sector;
			curr_track = list3.getFirst().track;
			list3.pop();
			reqExecuted = true;
		}else if(!list4.isEmpty()){
			curr_sector = list4.getFirst().sector;
			curr_track = list4.getFirst().track;
			list4.pop();
			reqExecuted = true;
		}
		if(reqExecuted){
			//update pos time
			aging();
			updatePosTime();
			updateOrder();
		
		}
	}
	
	public static void main(String[] args){
		LinkedList<Request> list = new LinkedList<Request>();
		int i=0;
		int sector, track;
		Random num = new Random();
		//int[] arr = {12, 25, 69, 12, 78, 42, 45, 97, 21, 37, 54, 34, 14, 86, 5};
		for(i=0;i<25;i++){
			sector = num.nextInt(100);
			track = num.nextInt(50);
			list.add(new Request(i+2,sector, track));
		}
		
		Disk disk = new Disk(2, 3, 50, 100);
		int curr_sector = 21;
		int curr_track = 25;
		ASPTF_OPT algorithm = new ASPTF_OPT(disk, list, curr_sector, curr_track);
		i=0;
		while(i<25){
			algorithm.executeRequest();
		}
	}
}

class Request implements Comparable<Request>{
	int arrivalTime;
	int age;
	int sector;
	int track;
	double posTime;
	//int priority;
	
	public String toString(){
		return this.arrivalTime + " " + this.age + " " + this.posTime + " " + this.sector + " " + this.track;
	}
	public Request(int arrivalTime, int sector, int track){
		this.arrivalTime = arrivalTime;
		this.age = 30 - arrivalTime;
		this.sector = sector;
		this.track = track;
		
	}
	@Override
	public int compareTo(Request o) {
		double comparedSize = o.posTime;
		if (this.posTime > comparedSize) {
			return 1;
		} else if (this.posTime == comparedSize) {
			return 0;
		} else {
			return -1;
		}
	}
	
}

class Disk{
	int noOfSectors;
	int tracksPerSector;
	double stFactor;
	double rotLat;
	
	public Disk(int stFactor, int rotLatency, int tracksPerSector, int noOfSectors){
		this.stFactor = stFactor;
		this.rotLat = rotLatency;
		this.tracksPerSector = tracksPerSector;
		this.noOfSectors = noOfSectors;
	}

}