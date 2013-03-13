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
public class GoalModel {
    private List<Goal> goals;
    private List<RefinementRelation> refinement;
    
    public GoalModel(){
        goals = new ArrayList();
        refinement = new ArrayList();
    }
    
    public boolean addGoal(Goal goal){
        return this.goals.add(goal);
    }
    
    public boolean addRefinement(RefinementRelation refinement){
        return this.refinement.add(refinement);
    }
    
    public List<Goal> getGoals(){
        return this.goals;
    }
}
