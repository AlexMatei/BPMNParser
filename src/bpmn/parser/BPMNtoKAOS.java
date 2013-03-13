package bpmn.parser;


import BPMNMetaModel.EndEvent;
import BPMNMetaModel.FlowNode;
import BPMNMetaModel.Fragment;
import BPMNMetaModel.Lane;
import BPMNMetaModel.StartEvent;
import KAOSMetaModel.Goal;
import KAOSMetaModel.GoalModel;
import KAOSMetaModel.RefinementRelation;
import com.aliasi.classify.ConditionalClassification;
import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;
import com.aliasi.tag.TagLattice;
import com.aliasi.tokenizer.RegExTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Streams;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.dom4j.Attribute;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import parserFunctions.BPMNParser;
import parserFunctions.PosTagger;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author localadmin
 */
public class BPMNtoKAOS extends javax.swing.JFrame {

    /**
     * Creates new form BpmnParser
     */
    
        
    BPMNParser parser; 
    
    //this can be replaced by file I/O - it is used to pass the end events selected by the user as Desirable to the Heuristic algo
    List desiredEndEvents = new ArrayList();
    List undesiredEndEvents = new ArrayList();
    
    public BPMNtoKAOS() {
        initComponents();
        parser = new BPMNParser();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jList2 = new javax.swing.JList();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton8 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton9 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton10 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jButton11 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });

        jLabel1.setText("Select ONE end event that shows the desired outcome of the process");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jList1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jList1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTextField1.setText("jTextField1");

        jLabel2.setText("Select exceptional end events, if any");

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jList2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jList2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jButton1.setText("BPMN Designer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Process naming guidelines");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Event naming guidelines");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jToolBar1.setRollover(true);

        jButton4.setText("Process Name");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator1);

        jButton5.setText("Succesful execution");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator3);

        jButton6.setText("EndEvents");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton7.setText("H4");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator4);

        jButton8.setText("Responsibilty handoff");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);
        jToolBar1.add(jSeparator5);

        jButton9.setText("H6");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton9);
        jToolBar1.add(jSeparator6);

        jButton10.setText("Happy Path Refinement");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton10);
        jToolBar1.add(jSeparator7);

        jButton11.setText("TopDown Refinement");
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton11);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Quality Variable", "Value", "Current", "Target"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            parser.loadProcessElements(file);
            this.jTextArea1.setText("Process model found.\n\n");
            getApplicableHeuristics();
        } 
    }//GEN-LAST:event_openMenuItemActionPerformed
    
    private int[] getApplicableHeuristics(){
        int[] heuristics = new int[8];
        if (parser.checkProcessName()) {
            this.jTextArea1.append("Process name is: " + parser.getProcessName()+ ".\nH1 is applicable.\n\n");
            heuristics[0]=1;
        }
        if (parser.getEndEvents().size()==1){
            this.jTextArea1.append("The process has a single end event, labeled: "+((FlowNode)parser.getEndEvents().get(0)).getName() +".\nThis event is also the desired end event\nH2 is applicable\n\n");
            heuristics[1]=1;
        }
        if (parser.getEndEvents().size()>1){
            List<String> endEvLabels = parser.getEndEventLabels();
            //this.jList1.setListData(endEvLabels.toArray());
            //JOptionPane.showConfirmDialog(this, this.jPanel1, "Acceptable Process Outcome", JOptionPane.OK_CANCEL_OPTION);
            //System.out.println("Selected event name is "+(String)this.jList1.getSelectedValue());
            //this.parser.setDesiredEndEvent((String)this.jList1.getSelectedValue());
            
            this.jList2.setListData(endEvLabels.toArray());
            JOptionPane.showConfirmDialog(this, this.jPanel2, "Exceptional Process Outcomes", JOptionPane.OK_CANCEL_OPTION);
            this.parser.setExceptionalEndEvents(this.jList2.getSelectedValuesList());
            
            
            
//            for (int i = 0; i<this.jList2.getModel().getSize();i++ ){
//                if (this.jList2.getSelectedValuesList().contains(this.jList1.getModel().getElementAt(i)) == false)
//                    undesiredEndEvents.add(this.jList1.getModel().getElementAt(i));
//            } 
            
//            for (int i=0;i<this.jList1.getSelectedIndices().length;i++){
//                desiredEndEvents.add(parser.getEndEvents().get(this.jList1.getSelectedIndices()[i]));
//            }
//            
//            for (int i = 0; i<this.jList1.getModel().getSize();i++ ){
//                if (this.jList1.getSelectedValuesList().contains(this.jList1.getModel().getElementAt(i)) == false)
//                    undesiredEndEvents.add(this.jList1.getModel().getElementAt(i));
//            } 

//            this.jTextArea1.append("The process has more than one end event, labeled as follows:\n");
//            Iterator itr = parser.getEndEventLabels().iterator();
//            while(itr.hasNext()) {
//                String endEventName = (String)itr.next(); 
//                this.jTextArea1.append("\tEND EVENT: "+endEventName+"\n");
//            }
//            this.jTextArea1.append("The a end event is: "+this.parser.getDesiredEndEventLabel()+"\n");
            
//            itr = desiredEndEvents.iterator();
//            while(itr.hasNext()){
//                FlowNode element = (FlowNode)itr.next();
//                this.jTextArea1.append("\tDESIRED END EVENT: "+element.getName()+"\n");
//            }
//            this.jTextArea1.append("H2 is applicable\n\n");
            
            if (parser.getAcceptableEndEventLabels().size()>1){
                this.jTextArea1.append("There are at least two acceptable (non-exceptional) end events, labelled as follows: \n");
                Iterator itr = parser.getAcceptableEndEventLabels().iterator();
                while(itr.hasNext()){
                    String evName = (String)itr.next();
                    this.jTextArea1.append(evName+"\n");
                }
                this.jTextArea1.append("Heuristic END_EVENTS is applicable\n\n");
                heuristics[2]=1;
            }
            
            
            
            if (parser.getExceptionalEndEvents().size()>0){
                this.jTextArea1.append("The process has one or more exceptional end events, labeled as follows:\n");
                Iterator itr = parser.getExceptionalEndEventLabels().iterator();
                while(itr.hasNext()){
                    String ev = (String)itr.next();
                    this.jTextArea1.append(ev+"\n");
                }
                this.jTextArea1.append("H4 is applicable\n\n");
                heuristics[3]=1;
            }
        }
//        if (parser.getActivityGroups().size()>1){
//            this.jTextArea1.append("The process has responsibility handoffs between the participants:\n");
//            Iterator itr = parser.getLanes().iterator();
//            while (itr.hasNext()){
//                Lane lane = (Lane)itr.next();
//                this.jTextArea1.append(lane.getName()+"\n");
//            }
//            this.jTextArea1.append("Heuristic H5 is applicable\n\n");
//            heuristics[4]=1;
//            
//        }
        
        if (parser.getExclusiveGateways().size()>0){
            this.jTextArea1.append("The process model contains one or more exclusive gateways:\n Heuristic H6 is applicable\n\n");
            heuristics[5]=1;
        }
        return heuristics;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ProcessBuilder pb = new ProcessBuilder("C:\\BPMNParser\\Yaoqiang-BPMN-Editor-2.1.0.exe");
        try {
            Process proc = pb.start();
        } catch (IOException ex) {
            Logger.getLogger(BPMNtoKAOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JOptionPane.showMessageDialog(this, "The process name, at its simplest, must be in the form verb-noun.\n\nThe name must indicate the result the customer of the process wants.\n\nThe result should be discrete, countable and essential.\n\nAction verbs are preferred: count, evaluate, print, return, sort, provide, calculate, retrieve.\n\nMushy verbs should be avoided: manage, maintain, administer, monitor, handle.\n\nA pool is a container for a single process.Its best practice to label pools with the name of the process.");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JOptionPane.showMessageDialog(this, "Start event can be an action event, temporal event or condition event.\n\nWhen a process is triggered by external request, the start event should be labelled Receive[message flow name].\n\nWhen a process is triggered by a timer event, the start event should be labeled with the recurring schedule, such as Monthly or Mondays at 8am.\n\nWhen a process is manually started, the start event may be left unlabeled.\n\n Indicate success and exception end states of a process or subprocess with separate end events, and label them to indicate the end state\n\n");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.jTextArea3.setText("Using the process name, the resulting goal is: "+Heuristics.ProcessName(parser.getProcessName()));
    }//GEN-LAST:event_jButton4ActionPerformed

    //TO DO: Make it work for several start events
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //this.jTextArea3.append("\nUsing the start and the end event, the resulting goal is: "+Heuristics.StartEndEvent((StartEvent)parser.getStartEvents().get(0),(EndEvent)parser.getDesiredEndEvent()));    
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Fragment fragment = parser.getGatewayStructure();
        this.jTextArea3.append("\nApplying H3, the resulting goal is: "+Heuristics.MultipleEndEvents(fragment));   
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.jTextArea3.append("\nApplying H4, the resulting goal is: ");
        String[] goals = Heuristics.AvoidEndEvents((StartEvent)parser.getStartEvents().get(0),parser.getExceptionalEndEvents());
        for(int i=0; i< goals.length;i++) {
            this.jTextArea3.append(goals[i]+"\n");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.jTextArea3.append("\nApplying H5, the resulting goals are: \n");
        //String[] goals = Heuristics
        List<Fragment> fragments = parser.getActivityGroups();
        System.out.println("Fragments size (from main method)"+fragments.size());
        System.out.println("First fragment size is "+ fragments.get(0).size());
        List<String> goals = Heuristics.ResponsibilityHandoff(parser.getActivityGroups());
        for(int i=0; i< goals.size();i++) {
            this.jTextArea3.append(goals.get(i) +"\n");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:;
        this.jTextArea3.append("\nApplying H6, the resulting goals are: \n");
        Fragment fragment = parser.getGatewayStructure();
        List<String> goals = Heuristics.ExclusiveGateways(fragment);
        for(int i=0; i< goals.size();i++) {
            this.jTextArea3.append(goals.get(i) +"\n");
        }
        System.out.println("Method returned. Fragment size is "+fragment.size());
        System.out.println("Number of sequence flow "+fragment.getSequenceFlows().size());
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
//        this.jTextArea3.append("\nApplying Happy Path Heuristic, the resulting goals are: \n");
//        Fragment happyPath = parser.getHappyPath();
//        GoalModel goals = Heuristics.HappyPath(happyPath);
//        System.out.println("number of goals in goal model:"+goals.getGoals().size());
//        Iterator itr = goals.getGoals().iterator();
//        while(itr.hasNext()){
//            Goal goal = (Goal) itr.next();
//            if (goal.hasRefinement()){
//                this.jTextArea3.append(goal.getName()+" refinedInto ");
//                RefinementRelation refinement = goal.getRefinements().get(0);
//                Iterator iter = refinement.getChildGoals().iterator();
//                while(iter.hasNext()){
//                    Goal childGoal = (Goal)iter.next();
//                    this.jTextArea3.append(childGoal.getName()+", ");
//                }
//            
//            }
//            else break;
//        }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        
        this.jTextArea3.append("\nApplying Top Down Refinement, the resulting goals are: \n");
        Fragment happyPath = parser.getHappyPathStructure();
        GoalModel goals = Heuristics.TopDownRefinement(happyPath);
        System.out.println("number of goals in goal model:"+goals.getGoals().size());
        Iterator itr = goals.getGoals().iterator();
        while(itr.hasNext()){
            Goal goal = (Goal) itr.next();
            if (goal.hasRefinement()){
                this.jTextArea3.append(goal.getName()+" refinedInto ");
                RefinementRelation refinement = goal.getRefinements().get(0);
                Iterator iter = refinement.getChildGoals().iterator();
                while(iter.hasNext()){
                    Goal childGoal = (Goal)iter.next();
                    this.jTextArea3.append(childGoal.getName()+", ");
                }
            
            }
            else break;
        }

    }//GEN-LAST:event_jButton11ActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BPMNtoKAOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BPMNtoKAOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BPMNtoKAOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BPMNtoKAOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BPMNtoKAOS().setVisible(true);
            }
        });
    }
          
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    // End of variables declaration//GEN-END:variables
}
