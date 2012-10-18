/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parserFunctions;

import bpmn.parser.BPMNtoKAOS;
import com.aliasi.classify.ConditionalClassification;
import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;
import com.aliasi.tag.TagLattice;
import com.aliasi.tokenizer.RegExTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Streams;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author localadmin
 */
public class PosTagger {
static final String MODEL_LOCATION = "C:\\BPMNParser\\lingpipe-4.1.0\\demos\\models\\pos-en-general-brown.HiddenMarkovModel";
static TokenizerFactory TOKENIZER_FACTORY = new RegExTokenizerFactory("(-|'|\\d|\\p{L})+|\\S");  
HmmDecoder decoder;
public PosTagger(){
       try{
            FileInputStream fileIn = new FileInputStream(MODEL_LOCATION);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            HiddenMarkovModel hmm = (HiddenMarkovModel) objIn.readObject();
            Streams.closeQuietly(objIn);       
            decoder = new HmmDecoder(hmm);
        }
        catch (ClassNotFoundException | IOException ex){
            Logger.getLogger(BPMNtoKAOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public String[] getVerb(String text){
    System.out.println(confidence(text));
    char[] cs = text.toCharArray();
    Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(cs,0,cs.length);
    String[] tokens = tokenizer.tokenize();
    List<String> tokenList = Arrays.asList(tokens);
    String[] result=new String[2];
    result[0]="";
    result[1]="";
    double maxSoFar=0.005;
    int position = -1;
    String type="";
    String tense="";
    TagLattice<String> lattice = decoder.tagMarginal(tokenList);
    for (int tokenIndex = 0; tokenIndex < tokenList.size(); ++tokenIndex) {
        ConditionalClassification tagScores = lattice.tokenClassification(tokenIndex);
        for (int i = 0; i < 5; ++i) {
            double conditionalProb = tagScores.score(i);
            if ((tagScores.category(i).contentEquals("vb")||tagScores.category(i).contentEquals("vbd")||tagScores.category(i).contentEquals("vbn")) && conditionalProb>maxSoFar){
                position = tokenIndex;
                maxSoFar = conditionalProb;
                type = tagScores.category(i);
            }                
        }  
    }
    if (position!=-1){
        result[0]=tokenList.get(position);
        if (type.contentEquals("vb"))
            tense="PRESENT";
        if (type.contentEquals("vbd")||type.contentEquals("vbn"))
            tense="PAST";
        result[1]=tense;
    }    
    return result;
}

//gets everything else except the verb
public String getNoun(String text) {
    char[] cs = text.toCharArray();
    Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(cs,0,cs.length);
    String[] tokens = tokenizer.tokenize();
    List<String> tokenList = Arrays.asList(tokens);    
    String s ="";
    double maxSoFar=0;
    int position = -1;
    TagLattice<String> lattice = decoder.tagMarginal(tokenList);
    for (int tokenIndex = 0; tokenIndex < tokenList.size(); ++tokenIndex) {
        ConditionalClassification tagScores = lattice.tokenClassification(tokenIndex);
        for (int i = 0; i < 5; ++i) {
            double conditionalProb = tagScores.score(i);
            if ((tagScores.category(i).contentEquals("vb")||tagScores.category(i).contentEquals("vbd")||tagScores.category(i).contentEquals("vbn")) && conditionalProb>maxSoFar){
                position = tokenIndex;
                maxSoFar = conditionalProb;
            }                
        }  
    }
    if (position!=-1){
        for (int tokenIndex = 0; tokenIndex < position; ++tokenIndex){
            s+=tokenList.get(tokenIndex)+" ";
        }
        for (int j = position+1; j < tokenList.size(); ++j){
            s+=tokenList.get(j)+" ";
        }
    }

    return s;
    }

public String confidence(String text) {
    char[] cs = text.toCharArray();
    Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(cs,0,cs.length);
    String[] tokens = tokenizer.tokenize();
    List<String> tokenList = Arrays.asList(tokens); 
    String s ="";
    s+="\nCONFIDENCE";
    s+="\n#   Token          (Prob:Tag)*\n";
    TagLattice<String> lattice = decoder.tagMarginal(tokenList);
    for (int tokenIndex = 0; tokenIndex < tokenList.size(); ++tokenIndex) {
        ConditionalClassification tagScores = lattice.tokenClassification(tokenIndex);
        s+=pad(Integer.toString(tokenIndex),4);
        s+=pad(tokenList.get(tokenIndex),15);
        for (int i = 0; i < 5; ++i) {
            double conditionalProb = tagScores.score(i);
            String tag = tagScores.category(i);
            s+=" " + format(conditionalProb) 
                             + ":" + pad(tag,4);

        }
        s+="\n";

    }
    return s;
}

static String format(double x) {
	return String.format("%9.3f",x);
    }

static String pad(String in, int length) {
	if (in.length() > length) return in.substring(0,length-3) + "...";
	if (in.length() == length) return in;
	StringBuilder sb = new StringBuilder(length);
	sb.append(in);
	while (sb.length() < length) sb.append(' ');
	return sb.toString();
	
    }
    
}
