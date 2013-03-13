/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KAOSMetaModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author localadmin
 */
public class Goal {
    public enum GoalType {
        ACHIEVE, MAINTAIN
    }
    private GoalType type;
    private String name;
    private String definition;
    private String startCondition;
    private String endCondition;
    private List <RefinementRelation> refinement;

    public Goal(){
        refinement = new ArrayList();
    }
    public boolean hasRefinement(){
        if (this.refinement.size()>0)
            return true;
        else return false;
    }
    public boolean addRefinement(RefinementRelation refinement){
        return this.refinement.add(refinement);
    }
    
    public List<RefinementRelation> getRefinements(){
        return this.refinement;
    }
    
    public String getStartCondition() {
        return startCondition;
    }

    public void setStartCondition(String startCondition) {
        this.startCondition = startCondition;
    }

    public String getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(String endCondition) {
        this.endCondition = endCondition;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) {
        this.type = type;
    }


    public String getName() {
        if (this.type.equals(this.type.ACHIEVE))       
            return "Achieve [ "+this.endCondition+" when "+this.startCondition+" ]";
        else 
            return "Maintain [ "+this.endCondition+" when " + this.startCondition + " ] ";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


    
    
}
