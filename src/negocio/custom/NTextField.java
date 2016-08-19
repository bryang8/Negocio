/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.custom;

import javafx.scene.control.TextField;

/**
 *
 * @author BRYAN
 */
public class NTextField extends TextField{
    
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("\\d||")&&end<21) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {  
            super.replaceSelection(replacement);
    }

    public NTextField() {
    
    }
    
}
