/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BPMNMetaModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author localadmin
 */
public class Lane {
    private String ID;
    private String name;
  
    public Lane(String ID) {
        this.ID = ID;
    }
    
    public boolean equals(Lane lane2){
        return this.ID.contentEquals(lane2.ID);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
