/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author localadmin
 */
public class StartEvent extends Event {
    private List<SequenceFlow> outgoing;
    
    
    //constructors
    public StartEvent(String id) {
        super(id);
        outgoing = new ArrayList();
    }

    public StartEvent(String id, String name) {
        super(id, name);
        outgoing = new ArrayList();
    }
    
    //interface methods implementation   
    @Override
    public void setOutgoing(List<SequenceFlow> flow){
        this.outgoing=flow;
    }
    
    @Override
    public List<SequenceFlow> getOutgoing(){
        return this.outgoing;
    }
    
    @Override
    public List<SequenceFlow> getIncoming(){
        return null;
    }

    @Override
    public void setIncoming(List<SequenceFlow> flow) {
        throw new UnsupportedOperationException("Start events do not have incoming sequence flows");
    }

    
    
    
}
