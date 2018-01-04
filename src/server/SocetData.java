/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.Serializable ;
/**
 *
 * @author iva417
 */
public class SocetData implements Serializable{
    private String value;
 public SocetData() {
        this.value = "";
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
     
}
