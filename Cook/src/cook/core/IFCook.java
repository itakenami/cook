/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cook.core;

/**
 *
 * @author itakenami
 */
public interface IFCook {

    public String getVersion();
    public void printHeader();
    public void printHelp();
    public boolean start(String[] param);
    public boolean validDirectory();
    public ResultProcess cook();
    public void end();
    

}
