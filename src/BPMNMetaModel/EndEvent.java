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
public class EndEvent extends Event{
    private List<SequenceFlow> incoming;

    //constructors
    public EndEvent(String id) {
        super(id);
        incoming=new ArrayList();
    }

    public EndEvent(String id, String name) {
        super(id, name);
        incoming = new ArrayList();
    }
    
    //interface methods implementation
    @Override
    public void setIncoming(List<SequenceFlow> flow){
        this.incoming=flow;
    }
    
    @Override
    public List<SequenceFlow> getIncoming(){
        return this.incoming;
    }
    
    @Override
    public List<SequenceFlow> getOutgoing(){
        System.out.println("Tried to get outgoing flow from end event");
        return null;
  
    }
    


    @Override
    public void setOutgoing(List<SequenceFlow> flow) {
        throw new UnsupportedOperationException("End events do not have outgoing sequence flows");
    }
}
