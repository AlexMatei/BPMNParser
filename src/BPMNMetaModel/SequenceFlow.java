/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

/**
 *
 * @author localadmin
 */
public class SequenceFlow {
    private String id;
    private String name;
    private FlowNode source;
    private FlowNode target;

    public SequenceFlow(String id) {
        this.id = id;
        this.source = null;
        this.target = null;
        this.name ="";
    }
    
    public SequenceFlow(String ID,String name, FlowNode source, FlowNode target){
        this.id = ID;
        this.name = name;
        this.source = source;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FlowNode getSource() {
        return source;
    }

    public void setSource(FlowNode source) {
        this.source = source;
    }

    public FlowNode getTarget() {
        return target;
    }

    public void setTarget(FlowNode target) {
        this.target = target;
    }
    
    
}
