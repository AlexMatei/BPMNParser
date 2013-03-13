/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parserFunctions;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

        
        
/**
 *
 * @author localadmin
 */
public class NLP {
    NLGFactory nlgFactory;
    Realiser realiser;
    
    public NLP(){
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        nlgFactory = new NLGFactory(lexicon);
        realiser = new Realiser(lexicon);

    }
    
    //verb[0] is the verb; verb[1] is the tense
    //takes VERB-NOUN and returns NOUN VERBed
    public String generateCondition(String[] verb, String noun){
        String condition = "";
        SPhraseSpec phrase = nlgFactory.createClause();
        phrase.setVerb(verb[0]);
        if (verb[1].contentEquals("PAST"))
            condition = noun + verb[0];
        else if (verb[1].contentEquals("PRESENT")) {
            phrase.setFeature(Feature.TENSE, Tense.PAST);
            String verbed = realiser.realiseSentence(phrase);
            if (verbed.endsWith("."))
                    verbed = verbed.substring(0, verbed.length()-1);
            verbed = verbed.toLowerCase();
            condition = noun + verbed;
        }
        return condition;
    }
    
    public String generateNegativeForm(String verb){
        String negatedVerb = verb;
        SPhraseSpec phrase = nlgFactory.createClause();
        phrase.setVerb(verb);
        phrase.setFeature(Feature.NEGATED,true);
        negatedVerb = realiser.realiseSentence(phrase);
        if (negatedVerb.endsWith("."))
            negatedVerb = negatedVerb.substring(0, negatedVerb.length()-1);
        negatedVerb = negatedVerb.toLowerCase();
        return negatedVerb;
        
        
    }
    
}
