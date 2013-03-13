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
public class RefinementRelation {
    private Goal parentGoal;
    private List<Goal> childGoals;
    
    public RefinementRelation(Goal parentGoal){
        childGoals = new ArrayList();
    }
    
    public boolean addChildGoal(Goal childGoal){
        return this.childGoals.add(childGoal);
    }
    
    public List<Goal> getChildGoals(){
        return this.childGoals;
    }
}
