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
public class Fragment {
    protected List<FlowNode> flowNodes;
    protected List<SequenceFlow>sequenceFlows;

    
    public Fragment(){
        flowNodes=new ArrayList();
        sequenceFlows = new ArrayList();
    }
    
    public Iterator<FlowNode> iterator(){
        return this.flowNodes.iterator();
    }


    
    public Fragment concatenate(Fragment fragment){
        Fragment newFragment =  new Fragment();
        Iterator itr = this.flowNodes.iterator();
        while (itr.hasNext()){
            FlowNode node = (FlowNode) itr.next();
            newFragment.addNode(node);
        }
        itr = fragment.iterator();
        while (itr.hasNext()){
            FlowNode node = (FlowNode) itr.next();
            newFragment.addNode(node);
        }
        return newFragment;
        
    }
    
    public boolean addNode(FlowNode node){
        return flowNodes.add(node);
    }
    
    public boolean removeNode(FlowNode node){
        return flowNodes.remove(node);
    }
    
    public FlowNode getNode (int index){
        return flowNodes.get(index);
    }
    
    public FlowNode getFirst(){
        return flowNodes.get(0);
    }
    
    public FlowNode getLast(){
        return flowNodes.get(flowNodes.size()-1);
    }
    
    public int size(){
        return flowNodes.size();
    }
    
    public List<SequenceFlow> getSequenceFlows(){
        return this.sequenceFlows;
    }
    
    public void removeAll(){
        this.flowNodes.removeAll(flowNodes);
    }
    
    public boolean removeSequenceFlow(SequenceFlow flow){
        return this.sequenceFlows.remove(flow);
    }
    
    public boolean addSequenceFlow(SequenceFlow flow){
        return this.sequenceFlows.add(flow);
    }
    
    public List<SequenceFlow> getFlowByTarget(FlowNode target){
        List<SequenceFlow> list = new ArrayList();
        Iterator itr = this.sequenceFlows.iterator();
        while(itr.hasNext()){
            SequenceFlow flow = (SequenceFlow)itr.next();
            if (flow.getTarget().getID().contentEquals(target.getID()))
                list.add(flow);
        }
        if (list.size()==0) System.out.println("Something wrong: tried to get flow by target, but no flow was found");
        return list;
    }
    
        public List<SequenceFlow> getFlowBySource(FlowNode source){
        List<SequenceFlow> list = new ArrayList();
        Iterator itr = this.sequenceFlows.iterator();
        while(itr.hasNext()){
            SequenceFlow flow = (SequenceFlow)itr.next();
            if (flow.getSource().getID().contentEquals(source.getID()))
                list.add(flow);
        }
        if (list.size()==0) System.out.println("Something wrong: tried to get flow by source, but no flow was found");
        return list;
    }
        
        public SequenceFlow getSequenceFlow(FlowNode source, FlowNode target){

            Iterator itr = this.sequenceFlows.iterator();
            while(itr.hasNext()){
                SequenceFlow flow = (SequenceFlow)itr.next();
                if (flow.getSource().getID().contentEquals(source.getID()) && flow.getTarget().getID().contentEquals(target.getID()))
                    return flow;
            }
            System.out.println("Something went wrong: tried to get flow by source and target, but seq was not found");
            return null;
        }
        
        public boolean containsExclusiveDivergingGateway(){
            Iterator itr = this.flowNodes.iterator();
            while (itr.hasNext()){
                FlowNode node = (FlowNode)itr.next();
                if ((node instanceof ExclusiveGateway)&& node.getOutgoing().size()>1)
                    return true;
                
            }
            return false;
        }
        
        
        
    
    
    
}
