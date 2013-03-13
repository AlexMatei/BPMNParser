/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author localadmin
 */
public class SESEFragment extends Fragment {
    private FlowNode firstElement;
    private FlowNode lastElement;
    
    public SESEFragment(){
        super();
    }
    

    public FlowNode getFirstElement() {
        return firstElement;
        
    }

    public void setFirstElement(FlowNode firstElement) {
        this.firstElement = firstElement;
    }

    public FlowNode getLastElement() {
        return lastElement;
    }

    public void setLastElement(FlowNode lastElement) {
        this.lastElement = lastElement;
    }
    
    //asume it doesnt start or end with a gateway
//    public ExclusiveGateway getLastExclusiveDivergingGateway(){
//        FlowNode current = super.getFlowByTarget(this.lastElement).get(0).getSource();
//        while(super.get){
//            FlowNode node = (FlowNode)itr.next();
//            if(super.getFlowByTarget(this.lastElement).get(0).getSource() instanceof ExclusiveGateway)
//            return super.getFlowByTarget(this.lastElement).get(0).getSource();#
//        }
//        
  //  }
    
    
    
    
}
