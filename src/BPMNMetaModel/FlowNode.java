/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

import java.util.List;

/**
 *
 * @author localadmin
 */
public interface FlowNode {    
    public String getID();
    public Lane getLane();
    public void setLane(Lane lane);
    public List<SequenceFlow> getIncoming();
    public void setIncoming(List<SequenceFlow> flow);
    public List<SequenceFlow> getOutgoing();
    public void setOutgoing(List<SequenceFlow> flow);
    public String getName();
    public void setName(String name);
}
