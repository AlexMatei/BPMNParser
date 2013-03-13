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
public class ParallelGateway extends Gateway{
    
    private List<SequenceFlow> incoming;
    private List<SequenceFlow> outgoing;
    
    public ParallelGateway(String id) {
        super(id);
        this.incoming = new ArrayList();
        this.outgoing = new ArrayList();
    }

    public ParallelGateway(String id, String name) {
        super(id, name);
        this.incoming = new ArrayList();
        this.outgoing = new ArrayList();
    }

    @Override
    public List<SequenceFlow> getIncoming() {
        return this.incoming;
    }

    @Override
    public void setIncoming(List<SequenceFlow> incomingFlows) {
        this.incoming = incomingFlows;
    }

    @Override
    public List<SequenceFlow> getOutgoing() {
        return this.outgoing;
    }

    @Override
    public void setOutgoing(List<SequenceFlow> outgoingFlows) {
        this.outgoing = outgoingFlows;
    }
    
    public boolean addOutgoing(SequenceFlow seq){
        return this.outgoing.add(seq);
    }
    public boolean addIncoming(SequenceFlow seq){
        return this.incoming.add(seq);
    }
    
}
