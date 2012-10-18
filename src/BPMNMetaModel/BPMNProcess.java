/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author localadmin
 */
public class BPMNProcess {
    private String name;
    private EndEvent desiredEndEvent;
    private List<Lane> lanes;
    private List<SequenceFlow> sequenceFlows;
    private List<Activity> activities;
    private List<StartEvent> startEvents;
    private List<EndEvent> endEvents;
    private List<EndEvent> exceptionalEndEvents;
    private List<ExclusiveGateway> exclusiveGateways;
    
    
    

    public BPMNProcess() {
        lanes = new ArrayList();
        sequenceFlows=new ArrayList();
        activities = new ArrayList();
        startEvents = new ArrayList();
        endEvents = new ArrayList();
        exceptionalEndEvents = new ArrayList();
        exclusiveGateways = new ArrayList();
    }
    
    //Process Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
   
    
    //Start Events
    public boolean addStartEvent(StartEvent ev){
        return this.startEvents.add(ev);
    }

    public List<StartEvent> getStartEvents() {
        return startEvents;
    }

    public StartEvent getStartEvent(String ID){
        Iterator itr = this.startEvents.iterator();
        while (itr.hasNext()){
            StartEvent ev = (StartEvent) itr.next();
            if (((Event)ev).getID().contentEquals(ID))
                return (StartEvent)ev;
        }
        return null;
    }
    
    //End Events
    public boolean addEndEvent(EndEvent ev){
        return this.endEvents.add(ev);
                
    }

    public List<EndEvent> getEndEvents() {
        return endEvents;
    }
    public EndEvent getEndEventByID(String ID){
        Iterator itr = this.endEvents.iterator();
        while (itr.hasNext()){
            EndEvent ev = (EndEvent) itr.next();
            if (((Event)ev).getID().contentEquals(ID))
                return (EndEvent)ev;
        }
        return null;
    }
    
    public EndEvent getEndEventByName (String name){
        Iterator itr = this.endEvents.iterator();
        while (itr.hasNext()){
            EndEvent ev = (EndEvent) itr.next();
            if (((Event)ev).getName().contentEquals(name))
                return (EndEvent)ev;
        }
        return null;
    }
    
    //desired End Event

    public EndEvent getDesiredEndEvent() {
        return desiredEndEvent;
    }

    public void setDesiredEndEvent(EndEvent desiredEndEvent) {
        this.desiredEndEvent = desiredEndEvent;
    }
    
    //exceptional End Event

    public List<EndEvent> getExceptionalEndEvents() {
        return exceptionalEndEvents;
    }

    public void setExceptionalEndEvents(List<EndEvent> exceptionalEndEvents) {
        this.exceptionalEndEvents = exceptionalEndEvents;
    }
    
    public boolean addExceptionalEndEvent(EndEvent event){
        return this.exceptionalEndEvents.add(event);
    }
    
    
    //Activity
    public boolean addActivity(Activity act){
        return this.activities.add(act);
    }
    
    //considers only activities that are part of the lanes
    public Activity getActivity(String ID){
        Iterator itr = this.activities.iterator();
        while(itr.hasNext()) {
            Activity act = (Activity) itr.next();
            if (((Activity)act).getID().contentEquals(ID))
                    return (Activity)act;
            
        }
        
        return null;
    }
        
    public Activity getActivity(int index){
        return this.activities.get(index);
    }
    
    public Iterator getActivityIterator(){
        return this.activities.iterator();
    }

    //Lane
    public boolean addLane(Lane lane){
        return lanes.add(lane);
    }
    
    public Lane getLane(int index){
        return lanes.get(index);
    }
    
    public int numberOfLanes(){
        return this.lanes.size();
    }
    public List <Lane> getLanes(){
        return this.lanes;
    }
    
    //Sequence Flow
    public boolean addSequenceFlow(SequenceFlow seq){
        return sequenceFlows.add(seq);
    }
    
    public SequenceFlow getSequenceFlow(String ID){
        Iterator itr = sequenceFlows.iterator(); 
        while(itr.hasNext()) {
            SequenceFlow seq = (SequenceFlow)itr.next(); 
            if (seq.getId().contentEquals(ID)){
                return seq;
            }
        }
        return null;
    }
    
    public Iterator<SequenceFlow> sequenceFlowIterator(){
        return sequenceFlows.iterator();
    }
    
    //Gateways
    public boolean addExclusiveGateway(ExclusiveGateway gateway){
        return this.exclusiveGateways.add(gateway);
    }
    
    public ExclusiveGateway getExclusiveGateway(String ID){
        Iterator itr = this.exclusiveGateways.iterator();
        while (itr.hasNext()){
            ExclusiveGateway gateway = (ExclusiveGateway) itr.next();
            if (((ExclusiveGateway)gateway).getID().contentEquals(ID))
                return (ExclusiveGateway)gateway;
        }
        return null;
    }
    
    public List<ExclusiveGateway> getExclusiveGateways(){
        return this.exclusiveGateways;
    }
    
}
