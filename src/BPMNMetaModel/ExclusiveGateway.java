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
public class ExclusiveGateway extends Gateway{
    
    private List<SequenceFlow> incoming;
    private List<SequenceFlow> outgoing;
    
    public ExclusiveGateway(String id) {
        super(id);
        incoming = new ArrayList();
        outgoing = new ArrayList();
    }

    public ExclusiveGateway(String id, String name) {
        super(id, name);
        incoming = new ArrayList();
        outgoing = new ArrayList();
    }

    @Override
    public SequenceFlow getIncoming() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIncoming(SequenceFlow flow) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SequenceFlow getOutgoing() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOutgoing(SequenceFlow flow) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
