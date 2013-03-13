/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parserFunctions;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import BPMNMetaModel.*;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author localadmin
 */
public class BPMNParser {
    SAXReader reader = new SAXReader();
    private Document document;
    private BPMNProcess process = new BPMNProcess();
    
    
    //add error messages in case file not find; file not in BPMN format
    //process lane has to come last, because it doesnt create FlowNodes, it checks if they exist and then assigns them lanes
    private void parseModel() throws InterruptedException{
        setProcessName();

        setProcessStartEvents();

        setProcessEndEvents();
        
//        setProcessDefaultDesiredEndEvent();

        setProcessActivities();

        setProcessExclusiveGateways();
        
        setProcessParallelGateways();
        
        
        setProcessLanes();
        
        setProcessSequenceFlows();
    }
    private void setProcessName(){
        Element root = null;
        String processName="ProcessNameNotFound";
        root = document.getRootElement();
        for (Iterator i = root.elementIterator("collaboration"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("participant"); j.hasNext();) {
                Element event = (Element) j.next();
                for (Iterator k = event.attributeIterator(); k.hasNext();) {
                    Attribute attribute = (Attribute) k.next();
                    if (attribute.getName().equalsIgnoreCase("name")){ 
                            process.setName( attribute.getValue());
                    }
                }       
            }
        }
    }
    private void setProcessStartEvents(){
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("startEvent"); j.hasNext();) {
                Element event = (Element) j.next();
                StartEvent ev = null;
                for (Iterator k = event.attributeIterator(); k.hasNext();) {
                    Attribute attribute = (Attribute) k.next();
                    if (attribute.getName().equalsIgnoreCase("id")){ 
                        ev = new StartEvent(attribute.getValue());
                    }
                    else if (attribute.getName().equalsIgnoreCase("name")){ 
                        ev.setName(attribute.getValue());
                    }
                }
                 for (Iterator l = event.elementIterator("outgoing"); l.hasNext();) {
                    Element seq = (Element) l.next();
                    SequenceFlow flow = new SequenceFlow((String) seq.getData());
                    flow.setSource(ev);
                    this.process.addSequenceFlow(flow);
                    List<SequenceFlow> outgoingSeqFlows = new ArrayList();
                    outgoingSeqFlows.add(flow);
                    System.out.println("trying to assign seq flow to start events"+outgoingSeqFlows.get(0).getId());
                    ev.setOutgoing(outgoingSeqFlows);
                    
                 }
                 
                 process.addStartEvent(ev);
                 System.out.println("Adding start event to the process. Seq flow id"+ev.getOutgoing().get(0).getId());
            }
        }
        
    }
    private void setProcessEndEvents(){
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("endEvent"); j.hasNext();) {
                Element event = (Element) j.next();
                EndEvent ev = null;
                for (Iterator k = event.attributeIterator(); k.hasNext();) {
                    Attribute attribute = (Attribute) k.next();
                    if (attribute.getName().equalsIgnoreCase("id")){ 
                        ev = new EndEvent(attribute.getValue());
                    }
                    else if (attribute.getName().equalsIgnoreCase("name")){ 
                        ev.setName(attribute.getValue());
                    }
                }
                 for (Iterator l = event.elementIterator("incoming"); l.hasNext();) {
                    Element seq = (Element) l.next();
                    SequenceFlow flow = new SequenceFlow((String) seq.getData());
                    flow.setTarget(ev);
                    this.process.addSequenceFlow(flow);
                    List<SequenceFlow> incomingSeqFlows = new ArrayList();
                    incomingSeqFlows.add(flow);
                    ev.setIncoming(incomingSeqFlows);
                    
                 }
                 process.addEndEvent(ev);
                 System.out.println("Adding end event to the process. Seq flow id "+ ev.getIncoming().get(0).getId());
            }
        }
        
    }
    
    //call method only after SetProcessEndEvents
//    private void setProcessDefaultDesiredEndEvent(){
//        
//        try {
//            this.process.setDesiredEndEvent(this.process.getEndEvents().get(0));
//        } catch (Exception e) {
//            System.out.println("No End event found");
//        }
//    }
    private void setProcessActivities(){
        Element root = null;
        //holds all the lists of activities for each lane
        root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("task"); j.hasNext();) {
                Element task = (Element) j.next();
                Activity act = null;
                
                for (Iterator k = task.attributeIterator(); k.hasNext();) {
                        Attribute attribute = (Attribute) k.next();
                        if (attribute.getName().equalsIgnoreCase("id")){ 
                            act = new Activity(attribute.getValue());
                            process.addActivity(act);
                           
                        }
                        else if (attribute.getName().equalsIgnoreCase("name")){ 
                            act.setName(attribute.getValue());
                            
                        }
                }
                for (Iterator m = task.elementIterator("incoming");m.hasNext();){
                    Element seqID = (Element) m.next();
                    //System.out.println("Processing seq flow with id "+ (String)seqID.getText());
                    List<SequenceFlow> incomingSeqFlow = new ArrayList();
                    if (process.getSequenceFlow((String)seqID.getText())!=null){
                        //System.out.println("seq flow with id "+ (String)seqID.getText()+" already exists in the process");
                        //System.out.println("Get existing seq from process "+ process.getSequenceFlow((String)seqID.getText()).getId());
                        incomingSeqFlow.add(process.getSequenceFlow((String)seqID.getText()));
                        act.setIncoming(incomingSeqFlow );
                        process.getSequenceFlow((String)seqID.getText()).setTarget(act);
                        
                    }
                    
                    else {
                        //System.out.println("seq flow with id "+ (String)seqID.getText()+" does NOT exist in the process");
                        SequenceFlow seq = new SequenceFlow((String)seqID.getText());
                        seq.setTarget(act);
                        this.process.addSequenceFlow(seq);
                        incomingSeqFlow.add(seq);
                        act.setIncoming(incomingSeqFlow);
                        
                    }
                    //System.out.println("Finished incoming for this task");
               }   
               for (Iterator m = task.elementIterator("outgoing");m.hasNext();){
                    Element seqID = (Element) m.next();
                    List<SequenceFlow> outgoingSeqFlow = new ArrayList();
                    //System.out.println("Processing seq flow with id "+ (String)seqID.getText());
                    if (process.getSequenceFlow((String)seqID.getText())!=null){
                        outgoingSeqFlow.add(process.getSequenceFlow((String)seqID.getText()));
                        act.setOutgoing( outgoingSeqFlow);
                        process.getSequenceFlow((String)seqID.getText()).setSource(act);
                    }
                    else {
                        //System.out.println("seq flow with id "+ (String)seqID.getText()+" does NOT exist in the process");
                        SequenceFlow seq = new SequenceFlow((String)seqID.getText());
                        seq.setSource(act);
                        this.process.addSequenceFlow(seq);
                        outgoingSeqFlow.add(seq);
                        act.setOutgoing(outgoingSeqFlow);
                        
                    }
               }
               //System.out.println("Finished outgoing for this task");
               
            }
        }
    }
    private void setProcessExclusiveGateways(){
        Element root = null;
        //holds all the lists of activities for each lane
        root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("exclusiveGateway"); j.hasNext();) {
                Element exclusiveGateway = (Element) j.next();
                ExclusiveGateway gateway = null;
                
                for (Iterator k = exclusiveGateway.attributeIterator(); k.hasNext();) {
                        Attribute attribute = (Attribute) k.next();
                        if (attribute.getName().equalsIgnoreCase("id")){ 
                            gateway = new ExclusiveGateway(attribute.getValue());
                            process.addExclusiveGateway(gateway);
                           
                        }
                        else if (attribute.getName().equalsIgnoreCase("name")){ 
                            gateway.setName(attribute.getValue());
                            
                        }
                }
                for (Iterator m = exclusiveGateway.elementIterator("incoming");m.hasNext();){
                Element seqID = (Element) m.next();
                //System.out.println("Processing seq flow with id "+ (String)seqID.getText());
                List<SequenceFlow> incomingSeqFlow = new ArrayList();
                if (process.getSequenceFlow((String)seqID.getText())!=null){
                    //System.out.println("seq flow with id "+ (String)seqID.getText()+" already exists in the process");
                    //System.out.println("Get existing seq from process "+ process.getSequenceFlow((String)seqID.getText()).getId());
                    gateway.addIncoming(process.getSequenceFlow((String)seqID.getText()) );
                    process.getSequenceFlow((String)seqID.getText()).setTarget(gateway);

                }

                else {
                    //System.out.println("seq flow with id "+ (String)seqID.getText()+" does NOT exist in the process");
                    SequenceFlow seq = new SequenceFlow((String)seqID.getText());
                    seq.setTarget(gateway);
                    this.process.addSequenceFlow(seq);
                    gateway.addIncoming(seq);

                }
                    //System.out.println("Finished incoming for this task");
               }
                for (Iterator m = exclusiveGateway.elementIterator("outgoing");m.hasNext();){
                Element seqID = (Element) m.next();
                List<SequenceFlow> outgoingSeqFlow = new ArrayList();
                //System.out.println("Processing seq flow with id "+ (String)seqID.getText());
                if (process.getSequenceFlow((String)seqID.getText())!=null){
                    gateway.addOutgoing( process.getSequenceFlow((String)seqID.getText()));
                    process.getSequenceFlow((String)seqID.getText()).setSource(gateway);
                    System.out.println("Adding seq "+ process.getSequenceFlow((String)seqID.getText()).getId()+ " to gateway "+ gateway.getName());
                }
                else {
                    //System.out.println("seq flow with id "+ (String)seqID.getText()+" does NOT exist in the process");
                    SequenceFlow seq = new SequenceFlow((String)seqID.getText());
                    seq.setSource(gateway);
                    this.process.addSequenceFlow(seq);
                    gateway.addOutgoing(seq);

                    }
                }
            }
        }
    }
    
        private void setProcessParallelGateways(){
        Element root = null;
        //holds all the lists of activities for each lane
        root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("parallelGateway"); j.hasNext();) {
                Element parallelGateway = (Element) j.next();
                ParallelGateway gateway = null;
                
                for (Iterator k = parallelGateway.attributeIterator(); k.hasNext();) {
                        Attribute attribute = (Attribute) k.next();
                        if (attribute.getName().equalsIgnoreCase("id")){ 
                            gateway = new ParallelGateway(attribute.getValue());
                            process.addParallelGateway(gateway);
                           
                        }
                        else if (attribute.getName().equalsIgnoreCase("name")){ 
                            gateway.setName(attribute.getValue());
                            
                        }
                }
                for (Iterator m = parallelGateway.elementIterator("incoming");m.hasNext();){
                Element seqID = (Element) m.next();
                //System.out.println("Processing seq flow with id "+ (String)seqID.getText());
                List<SequenceFlow> incomingSeqFlow = new ArrayList();
                if (process.getSequenceFlow((String)seqID.getText())!=null){
                    //System.out.println("seq flow with id "+ (String)seqID.getText()+" already exists in the process");
                    //System.out.println("Get existing seq from process "+ process.getSequenceFlow((String)seqID.getText()).getId());
                    gateway.addIncoming(process.getSequenceFlow((String)seqID.getText()) );
                    process.getSequenceFlow((String)seqID.getText()).setTarget(gateway);

                }

                else {
                    //System.out.println("seq flow with id "+ (String)seqID.getText()+" does NOT exist in the process");
                    SequenceFlow seq = new SequenceFlow((String)seqID.getText());
                    seq.setTarget(gateway);
                    this.process.addSequenceFlow(seq);
                    gateway.addIncoming(seq);

                }
                    //System.out.println("Finished incoming for this task");
               }
                for (Iterator m = parallelGateway.elementIterator("outgoing");m.hasNext();){
                Element seqID = (Element) m.next();
                List<SequenceFlow> outgoingSeqFlow = new ArrayList();
                //System.out.println("Processing seq flow with id "+ (String)seqID.getText());
                if (process.getSequenceFlow((String)seqID.getText())!=null){
                    gateway.addOutgoing( process.getSequenceFlow((String)seqID.getText()));
                    process.getSequenceFlow((String)seqID.getText()).setSource(gateway);
                    System.out.println("Adding seq "+ process.getSequenceFlow((String)seqID.getText()).getId()+ " to gateway "+ gateway.getName());
                }
                else {
                    //System.out.println("seq flow with id "+ (String)seqID.getText()+" does NOT exist in the process");
                    SequenceFlow seq = new SequenceFlow((String)seqID.getText());
                    seq.setSource(gateway);
                    this.process.addSequenceFlow(seq);
                    gateway.addOutgoing(seq);

                    }
                }
            }
        }
    }
        
    private void setProcessLanes(){
        Element root = null;
        //holds all the lists of activities for each lane
        root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("laneSet"); j.hasNext();) {
                Element laneset = (Element) j.next();
                for(Iterator k = laneset.elementIterator("lane"); k.hasNext();){
                    Element laneElement = (Element) k.next();
                    Lane lane = null;
                    for (Iterator l = laneElement.attributeIterator();l.hasNext();){
                    Attribute attribute = (Attribute) l.next();
                    
                        if (attribute.getName().equalsIgnoreCase("id")){ 
                            lane = new Lane(attribute.getText());
                            process.addLane(lane);
                        }
                        else if (attribute.getName().equalsIgnoreCase("name")){
                            lane.setName(attribute.getValue());
                        }
                    }
                    for(Iterator m = laneElement.elementIterator("flowNodeRef"); m.hasNext();){
                        Element flowNode = (Element) m.next();
                        if (process.getActivity(flowNode.getText())!=null)
                            process.getActivity(flowNode.getText()).setLane(lane);                    
                        else if (process.getStartEvent(flowNode.getText())!=null)
                            process.getStartEvent(flowNode.getText()).setLane(lane);
                        else if (process.getEndEventByID(flowNode.getText())!=null)
                            process.getEndEventByID(flowNode.getText()).setLane(lane);  
                        else if (process.getExclusiveGateway(flowNode.getText())!=null)
                            process.getExclusiveGateway(flowNode.getText()).setLane(lane);
                        
                    }
                }
                
            }
        }
    }
    
    
    private void setProcessSequenceFlows(){
        String currentID="";
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator("process"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("sequenceFlow"); j.hasNext();) {
                SequenceFlow sequenceFlow = null;
                Element seqFlow = (Element) j.next();
                for (Iterator k = seqFlow.attributeIterator(); k.hasNext();) {
                    Attribute attribute = (Attribute) k.next();
                    if (attribute.getName().equalsIgnoreCase("id")){ 
                        if(process.getSequenceFlow(attribute.getValue())==null){
                                sequenceFlow = new SequenceFlow(attribute.getValue());
                                currentID=attribute.getValue();
                                System.out.println("Sequence flow did not exist ---Smth wrong");
                        }
                        else if (process.getSequenceFlow(attribute.getValue())!=null){
                            currentID=attribute.getValue();
                            System.out.println("Flow exists - all good");
                        }
                    }
                    else if (attribute.getName().equalsIgnoreCase("name")){ 
                        process.getSequenceFlow(currentID).setName(attribute.getValue());
                    }                
                }
                
            }
        }
    }
    public void loadProcessElements(File file){
        try {
            document = reader.read(file);
        } catch (DocumentException ex) {
            Logger.getLogger(BPMNParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            parseModel();
        } catch (InterruptedException ex) {
            Logger.getLogger(BPMNParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkProcessName(){
        Element root = null;
        boolean processName=false;
        root = document.getRootElement();
        for (Iterator i = root.elementIterator("collaboration"); i.hasNext();) {
            Element element = (Element) i.next();
            for (Iterator j = element.elementIterator("participant"); j.hasNext();) {
                Element event = (Element) j.next();
                for (Iterator k = event.attributeIterator(); k.hasNext();) {
                    Attribute attribute = (Attribute) k.next();
                    if (attribute.getName().equalsIgnoreCase("name")&& !attribute.getValue().isEmpty()){ 
                            processName = true;
                    }
                }       
            }
        }
        return processName;
    } 
    
    //get methods
    //only considers the first start event
    public List<Fragment> getActivityGroups(){
        List<Fragment> fragments = new ArrayList();
        Fragment fragment = new Fragment();
        FlowNode last = process.getStartEvents().get(0).getOutgoing().get(0).getTarget();
        System.out.println("Reading start event.\n Getting target of start event "+ last.getName());
        while (!(last instanceof EndEvent)){
            if (last.getOutgoing().get(0).getTarget() instanceof Activity && ((FlowNode)last).getLane().getID().contentEquals(((Activity)last.getOutgoing().get(0).getTarget()).getLane().getID())){
                fragment.addNode(last);
                last = (Activity) last.getOutgoing().get(0).getTarget();
       
             }
            else if  (last.getOutgoing().get(0).getTarget() instanceof Activity && (!((Activity)last).getLane().equals(((Activity)last.getOutgoing().get(0).getTarget()).getLane()))){
                fragment.addNode(last);
                
                System.out.println("Fragment added. Size: "+fragment.size());
                for (int i = 0; i<fragment.size();i++)
                    System.out.println("Element: "+((FlowNode)fragment.getNode(i)).getName());
                fragments.add(fragment);
                fragment = new Fragment();
                last = (Activity) last.getOutgoing().get(0).getTarget();
                
            }
            else if (last.getOutgoing().get(0).getTarget() instanceof EndEvent ){
                fragment.addNode(last);
                System.out.println("Size: "+fragment.size());
                for (int i = 0; i<fragment.size();i++)
                    System.out.println("Element: "+((FlowNode)fragment.getNode(i)).getName());
                fragments.add(fragment);
                fragment=new Fragment();
                last = last.getOutgoing().get(0).getTarget();
            }
            else if (last.getOutgoing().get(0).getTarget() instanceof ExclusiveGateway){
                System.out.println("Gateway in next iteration "+last.getOutgoing().get(0).getTarget().getName());
                last = last.getOutgoing().get(0).getTarget();
            }
            else last = last.getOutgoing().get(0).getTarget();
        }
    
    return fragments;
    }
    public List<StartEvent> getStartEvents(){
        return process.getStartEvents();
    }
    
    public String getProcessName(){
        return this.process.getName();
    }
    public List<Lane> getLanes(){
        return this.process.getLanes();
    }
    public List<ExclusiveGateway> getExclusiveGateways(){
        return this.process.getExclusiveGateways();
    }
    //we consider the process name to be the name of the first participant in the XML
    
    
    
    private Fragment traverse(FlowNode node, Fragment fragment, Stack seqFlows) {
        Random generator = new Random();
        
        if (node instanceof EndEvent){
                fragment.addNode(node);
                System.out.println("Visited an end event");
                SequenceFlow sequenceFlow = (SequenceFlow)seqFlows.pop();
                sequenceFlow.setTarget(node);
                fragment.addSequenceFlow(sequenceFlow);
                return fragment;
            }
        else if(node instanceof StartEvent){
            fragment.addNode(node);
            System.out.println("Visited a Start Event. Stack size is "+seqFlows.size());
            Integer id = generator.nextInt();
            SequenceFlow seq = new SequenceFlow(id.toString(),node.getOutgoing().get(0).getName(),node,null);
            seqFlows.push(seq);
            return traverse(node.getOutgoing().get(0).getTarget(),fragment,seqFlows);
        }
        else if (node instanceof Gateway){
            fragment.addNode(node);
            SequenceFlow sequenceFlow = (SequenceFlow)seqFlows.pop();
            sequenceFlow.setTarget(node);
            fragment.addSequenceFlow(sequenceFlow);
            if(node.getOutgoing().size()>1){
                System.out.println("Visited a diverging gateway");
                Iterator itr = node.getOutgoing().iterator();
                while (itr.hasNext()){
                    Integer id = generator.nextInt();
                    SequenceFlow seq = (SequenceFlow)itr.next();
                    SequenceFlow newSequenceFlow = new SequenceFlow(id.toString(),seq.getName(),node,null);
                    seqFlows.push(newSequenceFlow);
                    fragment = traverse(seq.getTarget(), fragment,seqFlows);
                }
            }
            else {
                System.out.println("Visited a converging gateway");
                Integer id = generator.nextInt();
                SequenceFlow seq = node.getOutgoing().get(0);
                SequenceFlow newSequenceFlow = new SequenceFlow(id.toString(),seq.getName(),node,null);
                seqFlows.push(newSequenceFlow);
                fragment = traverse(seq.getTarget(), fragment, seqFlows);
                
            }
            
            return fragment;
        }
        
        else return traverse(node.getOutgoing().get(0).getTarget(),fragment, seqFlows);

    }


    //returns a fragment composed of All Start Events, All End Events - Exceptional ones and All  Gateways
    public Fragment getGatewayStructure(){
        Fragment fragment = new Fragment();
        Stack seqFlows = new Stack();
        return traverseGatewaysAndActivities(this.process.getStartEvents().get(0),fragment,seqFlows);
    }
    
    //gets structure: acceptable end events, exclusive and parallel gateways -- Does not include activities!!!
    private Fragment traverseGatewaysAndActivities(FlowNode node, Fragment fragment, Stack seqFlows){
        Random generator = new Random();
        
        if (node instanceof EndEvent){
            if(process.isAcceptableEndEvent(node)){
                fragment.addNode(node);
                System.out.println("Visited an acceptable end event");
                SequenceFlow sequenceFlow = (SequenceFlow)seqFlows.pop();
                sequenceFlow.setTarget(node);
                fragment.addSequenceFlow(sequenceFlow);
                return fragment;
            }
            return fragment; 
        }
        else if(node instanceof StartEvent){
            fragment.addNode(node);
            System.out.println("Visited a Start Event. Stack size is "+seqFlows.size());
            Integer id = generator.nextInt();
            SequenceFlow seq = new SequenceFlow(id.toString(),node.getOutgoing().get(0).getName(),node,null);
            seqFlows.push(seq);
            return traverseGatewaysAndActivities(node.getOutgoing().get(0).getTarget(),fragment,seqFlows);
        }
//        else if ((node instanceof Activity)&&(node.getOutgoing().get(0).getTarget() instanceof ExclusiveGateway)){
//            if (node.getOutgoing().get(0).getTarget().getOutgoing().size()>1){
//                fragment.addNode(node);
//                SequenceFlow sequenceFlow = (SequenceFlow)seqFlows.pop();
//                sequenceFlow.setTarget(node);
//                fragment.addSequenceFlow(sequenceFlow);
//                Integer id = generator.nextInt();
//                SequenceFlow seq = new SequenceFlow(id.toString(),node.getOutgoing().get(0).getName(),node,null);
//                seqFlows.push(seq);
//                return traverseGatewaysAndActivities(node.getOutgoing().get(0).getTarget(),fragment,seqFlows);
//            }
//            return traverseGatewaysAndActivities(node.getOutgoing().get(0).getTarget(),fragment,seqFlows);
//        }
            
        else if (node instanceof ExclusiveGateway){
            fragment.addNode(node);
            SequenceFlow sequenceFlow = (SequenceFlow)seqFlows.pop();
            sequenceFlow.setTarget(node);
            fragment.addSequenceFlow(sequenceFlow);
            if(node.getOutgoing().size()>1){
                System.out.println("Visited a diverging exclusive gateway");
                Iterator itr = node.getOutgoing().iterator();
                while (itr.hasNext()){
                    Integer id = generator.nextInt();
                    SequenceFlow seq = (SequenceFlow)itr.next();
                    SequenceFlow newSequenceFlow = new SequenceFlow(id.toString(),seq.getName(),node,null);
                    seqFlows.push(newSequenceFlow);
                    fragment = traverseGatewaysAndActivities(seq.getTarget(), fragment,seqFlows);
                }
            }
            else  {
                System.out.println("Visited a converging exclusive gateway");
                Integer id = generator.nextInt();
                SequenceFlow seq = node.getOutgoing().get(0);
                SequenceFlow newSequenceFlow = new SequenceFlow(id.toString(),seq.getName(),node,null);
                seqFlows.push(newSequenceFlow);
                fragment = traverseGatewaysAndActivities(seq.getTarget(), fragment, seqFlows);
                
            }
            
            return fragment;
        }
        
        else if (node instanceof ParallelGateway){
            fragment.addNode(node);
            SequenceFlow sequenceFlow = (SequenceFlow)seqFlows.pop();
            sequenceFlow.setTarget(node);
            fragment.addSequenceFlow(sequenceFlow);
            if(node.getOutgoing().size()>1){
                System.out.println("Visited a diverging parallel gateway");
                Iterator itr = node.getOutgoing().iterator();
                while (itr.hasNext()){
                    Integer id = generator.nextInt();
                    SequenceFlow seq = (SequenceFlow)itr.next();
                    SequenceFlow newSequenceFlow = new SequenceFlow(id.toString(),seq.getName(),node,null);
                    seqFlows.push(newSequenceFlow);
                    fragment = traverseGatewaysAndActivities(seq.getTarget(), fragment,seqFlows);
                }
            }
            else  {
                System.out.println("Visited a converging parallel gateway");
                Integer id = generator.nextInt();
                SequenceFlow seq = node.getOutgoing().get(0);
                SequenceFlow newSequenceFlow = new SequenceFlow(id.toString(),seq.getName(),node,null);
                seqFlows.push(newSequenceFlow);
                fragment = traverseGatewaysAndActivities(seq.getTarget(), fragment, seqFlows);
                
            }
            
            return fragment;
        }
        
        else return traverseGatewaysAndActivities(node.getOutgoing().get(0).getTarget(),fragment, seqFlows);
    }
    
    public Fragment getHappyPathStructure(){
        Fragment fragment = new Fragment();
        Stack seqFlows = new Stack();
        return traverseGatewaysAndActivities(this.process.getStartEvents().get(0),fragment,seqFlows);
    }
    
    
//    public Fragment getHappyPath(){
//        Random generator = new Random();
//        
//        Fragment happyPath = new Fragment();
//        Fragment gatewayStructure = this.getGatewayStructure();
//        System.out.println(gatewayStructure.getFlowByTarget( this.process.getDesiredEndEvent()).get(0).getSource().getName());
//        
//        FlowNode current = this.process.getDesiredEndEvent();
//        happyPath.addNode(current);
//        Integer id = generator.nextInt();
//        SequenceFlow flow = new SequenceFlow(id.toString(),"", null,current);
//        while (!(current instanceof StartEvent)){    
//            flow.setName(gatewayStructure.getSequenceFlow(gatewayStructure.getFlowByTarget(current).get(0).getSource(), current).getName());
//            current = gatewayStructure.getFlowByTarget(current).get(0).getSource();
//            if (current instanceof ExclusiveGateway){
//                if (current.getOutgoing().size()>1){
//                    flow.setSource(current);
//                    
//                    happyPath.addNode(current);
//                    happyPath.addSequenceFlow(flow);
//                    id = generator.nextInt();
//                    flow = new SequenceFlow(id.toString(),"",null,current);
//
//                    happyPath.addNode(current.getIncoming().get(0).getSource());
//                    flow.setSource(current.getIncoming().get(0).getSource());
//                    happyPath.addSequenceFlow(flow);
//                    id = generator.nextInt();
//                    flow = new SequenceFlow(id.toString(),"",null,current.getIncoming().get(0).getSource());
//                  
//                    System.out.println("Adding nodes to happy path");
//                }    
//            }
//        }
//        flow.setSource(current);
//        happyPath.addSequenceFlow(flow);
//        happyPath.addNode(current);
//        
//        System.out.println("Fragment size "+ happyPath.size());
//        System.out.println("Seq flow number "+happyPath.getSequenceFlows().size());
//        return happyPath;
//    }
    
    //event methods
    public List<EndEvent> getEndEvents(){
        return process.getEndEvents();
    }
    public List<String> getEndEventLabels(){
        List<String> endEventNames = new ArrayList();
        Iterator itr = this.process.getEndEvents().iterator();
        while (itr.hasNext()){
            EndEvent ev = (EndEvent)itr.next();
            endEventNames.add(ev.getName());
        }
        return endEventNames;
    }
    //takes the label of an end event and sets that end event to be the desired end event of the process.
//    public void setDesiredEndEvent(String label){
//        System.out.println("trying to get eventByName "+this.process.getEndEventByName(label));
//        this.process.setDesiredEndEvent(this.process.getEndEventByName(label));
//    }
//    //returns the label of the desired end event
//    public String getDesiredEndEventLabel(){
//        return this.process.getDesiredEndEvent().getName();
//    }
//    public EndEvent getDesiredEndEvent(){
//        return this.process.getDesiredEndEvent();
//    }
    //method gets the name of the end event and sets the event as an Exceptional End Event of teh process.
    public void setExceptionalEndEvents(List<String> names ){
        Iterator itr = names.iterator();
        while(itr.hasNext()){
            String evName = (String) itr.next();
            this.process.addExceptionalEndEvent(this.process.getEndEventByName(evName));
        }
    }
    //get acceptable end event labels (defined as end events which are not in the set of exceptional end events)
    public List<String> getAcceptableEndEventLabels(){
        List<String> evNames = new ArrayList();
        Iterator itr = this.process.getEndEvents().iterator();
        while (itr.hasNext()){
            EndEvent ev = (EndEvent) itr.next();
            if (!this.process.getExceptionalEndEvents().contains((EndEvent)ev))
                evNames.add(ev.getName());
        }
        return evNames;
    }   
    public List<EndEvent> getAcceptableEndEvents(){
        return this.process.getAcceptableEndEvents();
    }
    public List<EndEvent> getExceptionalEndEvents(){
        return this.process.getExceptionalEndEvents();
    }
    public List<String> getExceptionalEndEventLabels(){
        List<String> evNames = new ArrayList();
        Iterator itr = this.process.getExceptionalEndEvents().iterator();
        while (itr.hasNext()){
            EndEvent ev = (EndEvent) itr.next();
            evNames.add(ev.getName());
        }
        return evNames;
    }
    
    

}
