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
public class Activity implements FlowNode {
    private String id;
    private String name;
    private List<SequenceFlow> incoming;
    private List<SequenceFlow> outgoing;
    private Lane lane;
    
    //constructors

    public Activity(String id) {
        this.id = id;
        incoming = new ArrayList();
        outgoing = new ArrayList();
    }

    public Activity(String id, Lane lane) {
        this.id = id;
        this.lane = lane;
        incoming = new ArrayList();
        outgoing = new ArrayList();
    }
    
    public Activity(String id, String name, Lane lane) {
        this.id = id;
        this.name = name;
        this.lane = lane;
        incoming = new ArrayList();
        outgoing = new ArrayList();
    }
    
    
    //interface methods implementation
    @Override
    public String getID() {
        return id;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setName(String name){
        this.name=name;
    } 

    @Override
    public Lane getLane() {
        return lane;
    }

    @Override
    public void setLane(Lane lane) {
        this.lane = lane;
    }
    
    
    @Override
    public List<SequenceFlow> getIncoming() {
        return this.incoming;
    }

    @Override
    public void setIncoming(List<SequenceFlow> incoming) {
        this.incoming = incoming;
    }
    
    @Override
    public List<SequenceFlow> getOutgoing() {
        return this.outgoing;
    }

    @Override
    public void setOutgoing(List<SequenceFlow> outgoing) {
        this.outgoing = outgoing;
    }

    

    
}
