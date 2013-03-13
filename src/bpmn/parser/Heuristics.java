/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpmn.parser;

import BPMNMetaModel.Activity;
import BPMNMetaModel.EndEvent;
import BPMNMetaModel.ExclusiveGateway;
import BPMNMetaModel.FlowNode;
import BPMNMetaModel.Fragment;
import BPMNMetaModel.Gateway;
import BPMNMetaModel.ParallelGateway;
import BPMNMetaModel.SequenceFlow;
import BPMNMetaModel.StartEvent;
import KAOSMetaModel.Goal;
import KAOSMetaModel.GoalModel;
import KAOSMetaModel.RefinementRelation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import parserFunctions.NLP;
import parserFunctions.PosTagger;

/**
 *
 * @author localadmin
 */
public class Heuristics {
   
    private static Goal getMaintainGoal(ExclusiveGateway gateway, SequenceFlow flow, FlowNode activity){
        Goal goal = new Goal();
        goal.setType(Goal.GoalType.MAINTAIN);
        PosTagger posTagger = new PosTagger();
        NLP processor = new NLP();
        String[] startVerb = posTagger.getVerb(activity.getName());
        String startNoun = posTagger.getNoun(activity.getName());
        String startCondition="";
        
        //if there is no verb, just use the label
        if (startVerb[0].contentEquals(""))
            startCondition = activity.getName().toLowerCase();
        //if there is a verb at past tense, use the label as it is
        else if(startVerb[1].contentEquals("PAST"))
            startCondition = activity.getName().toLowerCase();
        //if there is a verb at present tense, switch to past tense
        else            
            startCondition = processor.generateCondition(startVerb,startNoun);
        
        goal.setStartCondition(startCondition);
        String condition = gateway.getName().substring(0,gateway.getName().length()-1);
        if (flow.getName().equalsIgnoreCase("yes"))
            goal.setEndCondition(condition);
        else
            goal.setEndCondition("Not"+condition);
        
       return goal;
    }
    private static Goal getAchieveGoal(FlowNode source, ExclusiveGateway guard, SequenceFlow flow, FlowNode target){
        Goal goal = new Goal();
        goal.setType(Goal.GoalType.ACHIEVE);
        PosTagger posTagger = new PosTagger();
        NLP processor = new NLP();
        String[] startVerb = posTagger.getVerb(source.getName());
        String startNoun = posTagger.getNoun(source.getName());
        String startCondition="";
        
        //if there is no verb, just use the label
        if (startVerb[0].contentEquals(""))
            startCondition = source.getName().toLowerCase();
        //if there is a verb at past tense, use the label as it is
        else if(startVerb[1].contentEquals("PAST"))
            startCondition = source.getName().toLowerCase();
        //if there is a verb at present tense, switch to past tense
        else            
            startCondition = processor.generateCondition(startVerb,startNoun);
         
        
        if (flow.getName().equalsIgnoreCase("yes"))
            startCondition+=" and "+guard.getName().substring(0,guard.getName().length()-1);
        else
            startCondition+=" and NOT "+guard.getName().substring(0,guard.getName().length()-1);
        
        goal.setStartCondition(startCondition);
        
        String[] endVerb = posTagger.getVerb(target.getName());
        String endNoun = posTagger.getNoun(target.getName());
        String endCondition ="";
        
        //if there is no verb, just use the label
        if (endVerb[0].contentEquals(""))
            endCondition=target.getName();
        //if there is a verb at past tense, use the label as it is
        else if(endVerb[1].contentEquals("PAST"))
            endCondition = target.getName().toLowerCase();
        //if there is a verb at present tense, switch to past tense
        else
            endCondition = processor.generateCondition(endVerb, endNoun);
        
        goal.setEndCondition(endCondition);
        
        return goal;
    }
    
    private static Goal getAchieveGoal(FlowNode source, FlowNode target){
        Goal goal = new Goal();
        goal.setType(Goal.GoalType.ACHIEVE);

        PosTagger posTagger = new PosTagger();
        NLP processor = new NLP();
        String[] startVerb = posTagger.getVerb(source.getName());
        String startNoun = posTagger.getNoun(source.getName());
        String startCondition="";
        
        //if there is no verb, just use the label
        if (startVerb[0].contentEquals(""))
            startCondition = source.getName().toLowerCase();
        //if there is a verb at past tense, use the label as it is
        else if(startVerb[1].contentEquals("PAST"))
            startCondition = source.getName().toLowerCase();
        //if there is a verb at present tense, switch to past tense
        else            
            startCondition = processor.generateCondition(startVerb,startNoun);
        
        goal.setStartCondition(startCondition);
        
        String[] endVerb = posTagger.getVerb(target.getName());
        String endNoun = posTagger.getNoun(target.getName());
        String endCondition ="";
        
        //if there is no verb, just use the label
        if (endVerb[0].contentEquals(""))
            endCondition=target.getName();
        //if there is a verb at past tense, use the label as it is
        else if(endVerb[1].contentEquals("PAST"))
            endCondition = target.getName().toLowerCase();
        //if there is a verb at present tense, switch to past tense
        else
            endCondition = processor.generateCondition(endVerb, endNoun);
        
        goal.setEndCondition(endCondition);
     
        return goal;
    }
    //takes the form EndCondition when StartCondition
   public static String StartEndEvent(StartEvent startEvent,EndEvent endEvent){
        String goal="";
        PosTagger posTagger = new PosTagger();
        NLP processor = new NLP();
        String[] startVerb = posTagger.getVerb(startEvent.getName());
        String startNoun = posTagger.getNoun(startEvent.getName());
        String startCondition="";
        
        
        
        //if there is no verb, just use the label
        if (startVerb[0].contentEquals(""))
            startCondition = startEvent.getName().toLowerCase();
        
        else if(startVerb[1].contentEquals("PAST"))
            startCondition = startEvent.getName().toLowerCase();
        
        else            
            startCondition = processor.generateCondition(startVerb,startNoun);
        
        
        
        String[] endVerb = posTagger.getVerb(endEvent.getName());
        String endNoun = posTagger.getNoun(endEvent.getName());
        String endCondition ="";
        
        //if there is no verb, just use the label
        if (endVerb[0].contentEquals(""))
            endCondition=endEvent.getName();
        
         else if(endVerb[1].contentEquals("PAST"))
            endCondition = endEvent.getName().toLowerCase();
        
        //if there is a verb, use the past tense
        else
            endCondition = processor.generateCondition(endVerb, endNoun);
        
        goal = "Achieve["+endCondition + " when "+ startCondition+"]";
        return goal;
    }
   
   //takes the form EndCondition1 OR EndCondition2 when StartCondition
//   public static String MultipleEndEvents(StartEvent startEvent, List<EndEvent> desiredEndEvents){
//        String goal="";
//        PosTagger posTagger = new PosTagger();
//        NLP processor = new NLP();
//        String[] startVerb = posTagger.getVerb(startEvent.getName());
//        String startNoun = posTagger.getNoun(startEvent.getName());
//        String startCondition="";
//        //if there is no verb, just use the label
//        if (startVerb[0].contentEquals(""))
//            startCondition = startEvent.getName().toLowerCase();
//        //if there is a verb, use the past tense
//        else            
//            startCondition = processor.generateCondition(startVerb,startNoun);
//        
//        String endCondition = "";
//        for (int i = 0; i<desiredEndEvents.size();i++){
//            String[] endVerb = posTagger.getVerb(desiredEndEvents.get(i).getName());
//            String endNoun = posTagger.getNoun(desiredEndEvents.get(i).getName());
//            
//            if (i>0)
//                endCondition+=" OR ";
//        
//            //if there is no verb, just use the label
//            if (endVerb[0].contentEquals(""))
//                endCondition+=desiredEndEvents.get(i).getName();
//            //if there is a verb, use the past tense
//            else endCondition += processor.generateCondition(endVerb, endNoun);
//        }
//       goal = "Achieve["+endCondition + " when "+ startCondition+"]"; 
//       return goal;
//   }
   
   //takes the form Avoid [Undesired End Condition when Start condition]
   public static String[] AvoidEndEvents(StartEvent startEvent, List<EndEvent> undesiredEndEvents){
        String[] avoidGoals= new String[undesiredEndEvents.size()];
        String goal="";
        PosTagger posTagger = new PosTagger();
        NLP processor = new NLP();
        String[] startVerb = posTagger.getVerb(startEvent.getName());
        String startNoun = posTagger.getNoun(startEvent.getName());
        String startCondition="";
        //if there is no verb, just use the label
        if (startVerb[0].contentEquals(""))
            startCondition = startEvent.getName().toLowerCase();
        //if there is a verb, use the past tense
        else            
            startCondition = processor.generateCondition(startVerb,startNoun);
        
        String endCondition = "";
        for (int i = 0; i<undesiredEndEvents.size();i++){
            String[] endVerb = posTagger.getVerb(undesiredEndEvents.get(i).getName());
            String endNoun = posTagger.getNoun(undesiredEndEvents.get(i).getName());
            
            //if there is no verb, just use the label
            if (endVerb[0].contentEquals("")){
                endCondition=undesiredEndEvents.get(i).getName();
            }
            //if there is a verb, use the past tense
            else endCondition = processor.generateCondition(endVerb, endNoun);
            
            goal = "Avoid["+endCondition + " when "+ startCondition+"]"; 
            avoidGoals[i]=goal;
            goal = "";
        }
       
       return avoidGoals;
   }
   
   
   public static String ProcessName(String processName){
       String goal="";
       PosTagger posTagger = new PosTagger();
       NLP processor = new NLP();
       String[] verb = posTagger.getVerb(processName);
       String noun = posTagger.getNoun(processName);
       goal = "Achieve["+processor.generateCondition(verb,noun)+"]";
       return goal;
   
   }
   
   public static List<String> ResponsibilityHandoff(List<Fragment> fragments){
       String goal = "";
       List<String> goals = new ArrayList();
       PosTagger posTagger = new PosTagger();
       NLP processor = new NLP();
       Fragment fragment;
       Iterator itr = fragments.iterator();    
       while(itr.hasNext()){
       fragment = (Fragment)itr.next();
           System.out.println("Fragment size "+fragment.size());
           
           if(fragment.size()==1){
                String[] startVerb = posTagger.getVerb(fragment.getFirst().getName());
                String startNoun = posTagger.getNoun(fragment.getFirst().getName());
                //if there is no verb, just use the label
                if (startVerb[0].contentEquals(""))
                    goal = "Achieve ["+fragment.getFirst().getName().toLowerCase()+"]";
                //if there is a verb, use the past tense
                else            
                    goal = "Achieve ["+processor.generateCondition(startVerb,startNoun)+"]";
                goals.add(goal);
           }
           else if(fragment.size()>1){
                String[] startVerb = posTagger.getVerb(fragment.getFirst().getName());
                String startNoun = posTagger.getNoun(fragment.getFirst().getName());
                String startCondition="";
                //if there is no verb, just use the label
                if (startVerb[0].contentEquals(""))
                    startCondition = fragment.getFirst().getName().toLowerCase();
                //if there is a verb, use the past tense
                else            
                    startCondition = processor.generateCondition(startVerb,startNoun);
                
                String endCondition = "";
                String[] endVerb = posTagger.getVerb(fragment.getLast().getName());
                String endNoun = posTagger.getNoun(fragment.getLast().getName());
            
                //if there is no verb, just use the label
                if (endVerb[0].contentEquals(""))
                    endCondition=fragment.getLast().getName();
                //if there is a verb, use the past tense
                else endCondition = processor.generateCondition(endVerb, endNoun);
                goal = "Achieve["+endCondition+" when "+startCondition+"]";
                goals.add(goal);
                                
            }
       
       }
       return goals;
   }
   
   //build the end condition
   private static String traverseGatewaysStructure(FlowNode node, Fragment fragment, String condition){
        if(node instanceof StartEvent){
            return traverseGatewaysStructure(((SequenceFlow)fragment.getFlowBySource(node).get(0)).getTarget(),fragment,condition);
        }

        else if (node instanceof ExclusiveGateway){
            String result="";
            if(node.getOutgoing().size()>1){
                result+="(";
                System.out.println("Visited a diverging exclusive gateway");
                Iterator itr = fragment.getFlowBySource(node).iterator();
                while (itr.hasNext()){
                    SequenceFlow seq = (SequenceFlow)itr.next();
                    System.out.println("Preparing to visit "+seq.getTarget().getName());
                    result += traverseGatewaysStructure(seq.getTarget(),fragment,result);
                    result +=" OR ";
                }
                result = result.substring(0, result.length()-4);
                result+=" )";
            }           
            return result;
        }
        
        else if (node instanceof ParallelGateway){
            String result="";
            if(node.getOutgoing().size()>1){
                result+="(";
                System.out.println("Visited a diverging parallel gateway");
                Iterator itr = fragment.getFlowBySource(node).iterator();
                while (itr.hasNext()){
                    SequenceFlow seq = (SequenceFlow)itr.next();
                    result += traverseGatewaysStructure(seq.getTarget(),fragment,result);
                    result+=" AND ";
                }
                result = result.substring(0, result.length()-5);
                result+=" )";
            }           
            return result;
        }
        else if (node instanceof EndEvent){
            return node.getName();
        }
        else 
            return condition;
        
   }
   //fragment includes acceptable end events, parallel and exclusive gateways
   //it assumes fragment.getLast returns an acceptable end event
   public static String MultipleEndEvents(Fragment fragment){
       String goal = "";
       String endCondition="";
       String startCondition=fragment.getFirst().getName();
       
       
       FlowNode current = fragment.getFirst();
       endCondition+=traverseGatewaysStructure(current, fragment, endCondition);
       
       goal = endCondition+" when "+startCondition;
       return goal;
   }
       
   public static List<String> ExclusiveGateways(Fragment fragment){
       List<String> goals = new ArrayList();
       
       PosTagger posTagger = new PosTagger();
       NLP processor = new NLP();
       String goal = "";
       Iterator itr = fragment.getSequenceFlows().iterator();
       while (itr.hasNext()){
            SequenceFlow seq = (SequenceFlow)itr.next();
            String[] startVerb = posTagger.getVerb(seq.getSource().getName());
            String startNoun = posTagger.getNoun(seq.getSource().getName());
            String startCondition="";
            //if there is no verb, just use the label
            if (startVerb[0].contentEquals(""))
                startCondition = seq.getSource().getName().toLowerCase();
            //if there is a verb, use the past tense
            else            
                startCondition = processor.generateCondition(startVerb,startNoun);

            String endCondition = "";
            String[] endVerb = posTagger.getVerb(seq.getTarget().getName());
            String endNoun = posTagger.getNoun(seq.getTarget().getName());

            //if there is no verb, just use the label
            if (endVerb[0].contentEquals(""))
                endCondition=seq.getTarget().getName();
            //if there is a verb, use the past tense
            else endCondition = processor.generateCondition(endVerb, endNoun);
            if(seq.getName().equalsIgnoreCase("no"))
                goal = "Achieve["+endCondition+" when NOT "+startCondition+"]";
            else 
                goal = "Achieve["+endCondition+" when "+startCondition+"]";
            goals.add(goal);
           
       }
       
       return goals;
   }
   
   
   //HAPPY PATH Starts from end event and finished to Start event !!!!
   //works only with diverging gateways
   //three elements: get the last gateway: maintain gateway condition, achive previous activity when starte event, achieve end event when maintain and achieve prev act
   
   
   //the fragment contains all end events, all gateways, any activity that comes right before a gateway and the start event
   public static GoalModel TopDownRefinement(Fragment happyPathStructure){
       GoalModel goals = new GoalModel();
       Iterator itr = happyPathStructure.iterator();
       FlowNode end = null;
       while(itr.hasNext()){
           FlowNode node = (FlowNode)itr.next();
           if (node instanceof EndEvent){
               end = node;
               break;
           }
           
       }
       itr = happyPathStructure.iterator();
       FlowNode start = null;
       while(itr.hasNext()){
       FlowNode node = (FlowNode)itr.next();
       if (node instanceof StartEvent){
           start = node;
           break;
       }
           
       }
       ExclusiveGateway gateway = null;
       SequenceFlow flow = null;
       Activity act = null;
       if (happyPathStructure.containsExclusiveDivergingGateway()){
           gateway = happyPathStructure.getExclusiveDivergingGateway(end);
           flow = happyPathStructure.getSequenceFlow(gateway, end);
           act = (Activity) happyPathStructure.getFlowByTarget(gateway).get(0).getSource();
       }
       
       Goal parentGoal = new Goal();
       parentGoal = getAchieveGoal( start, end);
       if (happyPathStructure.containsExclusiveDivergingGateway()){
           RefinementRelation refinement = new RefinementRelation (parentGoal);
           Goal maintGoal = getMaintainGoal(gateway, flow, act);
           goals.addGoal(parentGoal);
           refinement.addChildGoal(maintGoal);
           goals.addGoal(maintGoal);
           Goal firstAchieveGoal = getAchieveGoal(start, act);
           refinement.addChildGoal(firstAchieveGoal);
           goals.addGoal(firstAchieveGoal);
           Goal finalAchieveGoal = getAchieveGoal(act,gateway,flow,end);
           refinement.addChildGoal(finalAchieveGoal);
           goals.addGoal(finalAchieveGoal);
           goals.addRefinement(refinement);
           parentGoal.addRefinement(refinement);
       }
   
       return goals;
   }
   
   
   public static GoalModel HappyPath(Fragment happyPath){
       GoalModel goals = new GoalModel();
       
       Goal parentGoal = new Goal();
       parentGoal = getAchieveGoal( happyPath.getLast(),happyPath.getFirst());    
       goals.addGoal(parentGoal);
       FlowNode target = happyPath.getFirst();
       FlowNode source = happyPath.getFlowByTarget(target).get(0).getSource();
       
       //generate a maintain goal from the activity closest to the end event, if any
       if (source instanceof ExclusiveGateway){
           RefinementRelation refinement = new RefinementRelation (parentGoal);
           
           //generate an achieve goal, if there is an activity between the start event and the last gateway
           if (happyPath.getFlowByTarget(source).get(0).getSource() instanceof Activity){
               Goal maintGoal = getMaintainGoal((ExclusiveGateway)source,happyPath.getFlowByTarget(target).get(0),happyPath.getFlowByTarget(source).get(0).getSource());
               
               refinement.addChildGoal(maintGoal);
               goals.addGoal(maintGoal);
    
                //generate an achieve goal, if there is an activity between start event and gateway
               
                Goal firstAchieveGoal = getAchieveGoal(happyPath.getLast(), happyPath.getFlowByTarget(source).get(0).getSource());
                refinement.addChildGoal(firstAchieveGoal);
                goals.addGoal(firstAchieveGoal);
                
                //generate an achieve goal for the end event, with the gateway as a guard and the act completed as a trigger
                Goal finalAchieveGoal = getAchieveGoal(happyPath.getFlowByTarget(source).get(0).getSource(),(ExclusiveGateway)source,happyPath.getFlowByTarget(target).get(0),happyPath.getFirst());
                refinement.addChildGoal(finalAchieveGoal);
                goals.addGoal(finalAchieveGoal);
                
                
            }
            goals.addRefinement(refinement);
            parentGoal.addRefinement(refinement);
       }
       return goals;
       
       
//       while (!(source instanceof StartEvent)){
//           if (source instanceof ExclusiveGateway){
//               Goal childGoal = getMaintainGoal((ExclusiveGateway)source,happyPath.getFlowByTarget(target).get(0));
//               goals.add(childGoal);
//               
//           }
//           target=source;
//           source = happyPath.getFlowByTarget(source).get(0).getSource();
//           
//           
//       }
       
   }
    
}
