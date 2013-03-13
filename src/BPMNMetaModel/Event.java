/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

/**
 *
 * @author localadmin
 */
public abstract class Event implements FlowNode{
    private String id;
    private String name;
    private Lane lane;
    
    
    //constructors
    public Event(String id) {
        this.id = id;
    }

    public Event(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    //interface methods implementation

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    
    @Override
    public Lane getLane() {
        return lane;
    }

    @Override
    public void setLane(Lane lane) {
        this.lane = lane;
    }
    
    
}
