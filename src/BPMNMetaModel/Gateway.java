/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

/**
 *
 * @author localadmin
 */
public abstract class Gateway implements FlowNode {
    private String id;
    private String name;
    private Lane lane;
    private String direction;
    
    //constructor

    public Gateway(String id) {
        this.id = id;
    }

    public Gateway(String id, String name) {
        this.id = id;
        this.name = name;
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
