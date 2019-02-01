package alternative;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.Spring;
/*
 * Sectors read from 0 to 99
 * Tracks read from 0 to 49
 * Assumption: all request are valid 
 */

public class PRI_ASPCTF_OPT {

	Disk disk;
	int curr_sector;
	int curr_track;
	LinkedList<Request> reqList;
	LinkedList<Request> list1,list2,list3,list4;

	public PRI_ASPCTF_OPT(Disk disk, LinkedList<Request> reqList , int curr_sector, int curr_track){
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
		int i =0;
		for(i=0;i<list4.size();i++){				//added
			if(list4.get(i).priority==0){
				list1.add(list4.get(i));
				list4.remove(i);
				i--;
			}
			if(list4.get(i).priority==1){
				list2.add(list4.get(i));
				list4.remove(i);
				i--;
			}
			if(list4.get(i).priority==2){
				list3.add(list4.get(i));
				list4.remove(i);
				i--;
			}

		}
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
	
	public void updateTotalTime(){
		int i = 0;
		if(!list1.isEmpty()){
			for(i=0;i<list1.size();i++){
				if(disk.cache.isHit(list1.get(i))){
					list1.get(i).totaltTime = 1;
				}
				else{
					list1.get(i).totaltTime = list1.get(i).posTime + 1;
				}
			}
		}
		if(!list2.isEmpty()){
			for(i=0;i<list2.size();i++){
				if(disk.cache.isHit(list2.get(i))){
					list2.get(i).totaltTime = 1;
				}
				else{
					list2.get(i).totaltTime = list2.get(i).posTime + 1;
				}
			}
		}
		if(!list3.isEmpty()){
			for(i=0;i<list3.size();i++){
				if(disk.cache.isHit(list3.get(i))){
					list3.get(i).totaltTime = 1;
				}
				else{
					list3.get(i).totaltTime = list3.get(i).posTime + 1;
				}
			}
		}
		if(!list4.isEmpty()){
			for(i=0;i<list4.size();i++){
				if(disk.cache.isHit(list4.get(i))){
					list4.get(i).totaltTime = 1;
				}
				else{
					list4.get(i).totaltTime = list4.get(i).posTime + 1;
				}
			}
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
			if(!disk.cache.isHit(list1.getFirst())){
				//cache miss
				if(!disk.cache.isCacheFull()){
					//cache is not full
					curr_sector = list1.getFirst().sector;
					curr_track = list1.getFirst().track;
					CacheUnit cu = new CacheUnit(curr_sector, curr_track);
					if(list1.getFirst().isWrite)
						cu.dirtyBit = 1;
					else
						cu.dirtyBit = 0;
					disk.cache.cacheList.push(cu);
					list1.pop();
				}else{
					//cache is full
					//replacement needed
					if(disk.cache.cacheList.getLast().dirtyBit == 1){
						//write back needed
						curr_sector = disk.cache.cacheList.getLast().sector;
						curr_track = disk.cache.cacheList.getLast().track;
						//do not pop list1.first
					}else{
						//write back not needed
						disk.cache.cacheList.removeLast();
						curr_sector = list1.getFirst().sector;
						curr_track = list1.getFirst().track;
						CacheUnit cu = new CacheUnit(curr_sector, curr_track);
						if(list1.getFirst().isWrite)
							cu.dirtyBit = 1;
						else
							cu.dirtyBit = 0;
						disk.cache.cacheList.push(cu);
						list1.pop();
					}
				}
			}
			else{
				//cache hit
				int i;
				if((i=disk.cache.search(list1.getFirst())) != -1){
					if(list1.getFirst().isWrite){
						disk.cache.cacheList.get(i).dirtyBit = 1;
					}
					disk.cache.cacheList.push(disk.cache.cacheList.remove(i));
				}
				list1.pop();
		        
			}
			
			reqExecuted = true;
			
		}else if(!list2.isEmpty()){
			if(!disk.cache.isHit(list2.getFirst())){
				//cache miss
				if(!disk.cache.isCacheFull()){
					//cache is not full
					curr_sector = list2.getFirst().sector;
					curr_track = list2.getFirst().track;
					CacheUnit cu = new CacheUnit(curr_sector, curr_track);
					if(list2.getFirst().isWrite)
						cu.dirtyBit = 1;
					else
						cu.dirtyBit = 0;
					disk.cache.cacheList.push(cu);
					list2.pop();
				}else{
					//cache is full
					//replacement needed
					if(disk.cache.cacheList.getLast().dirtyBit == 1){
						//write back needed
						curr_sector = disk.cache.cacheList.getLast().sector;
						curr_track = disk.cache.cacheList.getLast().track;
						//do not pop list2.first
					}else{
						//write back not needed
						disk.cache.cacheList.removeLast();
						curr_sector = list2.getFirst().sector;
						curr_track = list2.getFirst().track;
						CacheUnit cu = new CacheUnit(curr_sector, curr_track);
						if(list2.getFirst().isWrite)
							cu.dirtyBit = 1;
						else
							cu.dirtyBit = 0;
						disk.cache.cacheList.push(cu);
						list2.pop();
					}
				}
			}
			else{
				//cache hit
				int i;
				if((i=disk.cache.search(list2.getFirst())) != -1){
					if(list2.getFirst().isWrite){
						disk.cache.cacheList.get(i).dirtyBit = 1;
					}
					disk.cache.cacheList.push(disk.cache.cacheList.remove(i));
				}
				list2.pop();
		        
			}
			
			reqExecuted = true;
			
		}else if(!list3.isEmpty()){
			if(!disk.cache.isHit(list3.getFirst())){
				//cache miss
				if(!disk.cache.isCacheFull()){
					//cache is not full
					curr_sector = list3.getFirst().sector;
					curr_track = list3.getFirst().track;
					CacheUnit cu = new CacheUnit(curr_sector, curr_track);
					if(list3.getFirst().isWrite)
						cu.dirtyBit = 1;
					else
						cu.dirtyBit = 0;
					disk.cache.cacheList.push(cu);
					list3.pop();
				}else{
					//cache is full
					//replacement needed
					if(disk.cache.cacheList.getLast().dirtyBit == 1){
						//write back needed
						curr_sector = disk.cache.cacheList.getLast().sector;
						curr_track = disk.cache.cacheList.getLast().track;
						//do not pop list3.first
					}else{
						//write back not needed
						disk.cache.cacheList.removeLast();
						curr_sector = list3.getFirst().sector;
						curr_track = list3.getFirst().track;
						CacheUnit cu = new CacheUnit(curr_sector, curr_track);
						if(list3.getFirst().isWrite)
							cu.dirtyBit = 1;
						else
							cu.dirtyBit = 0;
						disk.cache.cacheList.push(cu);
						list3.pop();
					}
				}
			}
			else{
				//cache hit
				int i;
				if((i=disk.cache.search(list3.getFirst())) != -1){
					if(list3.getFirst().isWrite){
						disk.cache.cacheList.get(i).dirtyBit = 1;
					}
					disk.cache.cacheList.push(disk.cache.cacheList.remove(i));
				}
				list3.pop();
		        
			}
			
			reqExecuted = true;
			
		}else if(!list4.isEmpty()){
			if(!disk.cache.isHit(list4.getFirst())){
				//cache miss
				if(!disk.cache.isCacheFull()){
					//cache is not full
					curr_sector = list4.getFirst().sector;
					curr_track = list4.getFirst().track;
					CacheUnit cu = new CacheUnit(curr_sector, curr_track);
					if(list4.getFirst().isWrite)
						cu.dirtyBit = 1;
					else
						cu.dirtyBit = 0;
					disk.cache.cacheList.push(cu);
					list4.pop();
				}else{
					//cache is full
					//replacement needed
					if(disk.cache.cacheList.getLast().dirtyBit == 1){
						//write back needed
						curr_sector = disk.cache.cacheList.getLast().sector;
						curr_track = disk.cache.cacheList.getLast().track;
						//do not pop list4.first
					}else{
						//write back not needed
						disk.cache.cacheList.removeLast();
						curr_sector = list4.getFirst().sector;
						curr_track = list4.getFirst().track;
						CacheUnit cu = new CacheUnit(curr_sector, curr_track);
						if(list4.getFirst().isWrite)
							cu.dirtyBit = 1;
						else
							cu.dirtyBit = 0;
						disk.cache.cacheList.push(cu);
						list4.pop();
					}
				}
			}
			else{
				//cache hit
				int i;
				if((i=disk.cache.search(list4.getFirst())) != -1){
					if(list4.getFirst().isWrite){
						disk.cache.cacheList.get(i).dirtyBit = 1;
					}
					disk.cache.cacheList.push(disk.cache.cacheList.remove(i));
				}
				list4.pop();
		        
			}
			
			reqExecuted = true;
			
		}
		if(reqExecuted){
			//update pos time
			aging();
			updatePosTime();
			updateTotalTime();
			updateOrder();
		}
	}
	
	public static void main(String[] args){
		LinkedList<Request> list = new LinkedList<Request>();
		int i=0;
		int sector, track,priority;
		Random num = new Random();
		//int[] arr = {12, 25, 69, 12, 78, 42, 45, 97, 21, 37, 54, 34, 14, 86, 5};
		for(i=0;i<25;i++){
			sector = num.nextInt(100);
			track = num.nextInt(50);
			priority = num.nextInt(4);
			list.add(new Request(i+2,sector, track,priority));
		}
		
		Disk disk = new Disk(2, 3, 50, 100);
		int curr_sector = 21;
		int curr_track = 25;
		PRI_ASPCTF_OPT algorithm = new PRI_ASPCTF_OPT(disk, list, curr_sector, curr_track);
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
	double totaltTime;
	int priority;
	boolean isWrite;
	
	public String toString(){
		return this.arrivalTime + " " + this.age + " " + this.posTime + " " + this.sector + " " + this.track;
	}
	public Request(int arrivalTime, int sector, int track,int priority){
		this.arrivalTime = arrivalTime;
		this.age = 30 - arrivalTime;
		this.sector = sector;
		this.track = track;
		this.priority=priority;
	
	}
	@Override
	public int compareTo(Request o) {
		double comparedSize = o.totaltTime;
		if (this.totaltTime > comparedSize) {
			return 1;
		} else if (this.totaltTime == comparedSize) {
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
	Cache cache;
	public Disk(int stFactor, int rotLatency, int tracksPerSector, int noOfSectors){
		this.stFactor = stFactor;
		this.rotLat = rotLatency;
		this.tracksPerSector = tracksPerSector;
		this.noOfSectors = noOfSectors;
		cache = new Cache();
	}

}

class Cache{
	LinkedList<CacheUnit> cacheList;
	public Cache(){
		this.cacheList = new LinkedList<CacheUnit>();
		// cacheList has 5 units/blocks i.e it can store data for 5 requests 
		//at any time
	}
	public boolean isCacheFull(){
		if(this.cacheList.size() >= 5)
			return true;
		return false;
	}
	public boolean isHit(Request req){
		int i = 0;
		for(i=0; i<this.cacheList.size(); i++){
			if(req.sector == this.cacheList.get(i).sector 
					&& req.track == this.cacheList.get(i).track){
				return true;
			}
		}
		return false;
	}
	
	public int search(Request req){
		int i = 0;
		for(i=0; i<5; i++){
			if(req.sector == this.cacheList.get(i).sector 
					&& req.track == this.cacheList.get(i).track){
				return i;
			}
		}
		return -1;
	}
}

class CacheUnit{
	int sector;
	int track;
	int dirtyBit;
	
	public CacheUnit(int sector, int track){
		this.sector = sector;
		this.track = track;
	}
}