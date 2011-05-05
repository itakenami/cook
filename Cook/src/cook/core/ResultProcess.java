/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cook.core;

/**
 *
 * @author itakenami
 */
public class ResultProcess {

    private int INFO;
    private String MESSAGE;
    public final static int ERROR = 0;
    public final static int SUCESS = 1;
    public final static int WARNING = 2;

    public ResultProcess(){

    }

    public ResultProcess(int ERRO, String MESSAGE){
        this.INFO = INFO;
        this.MESSAGE = MESSAGE;
    }

     public void setResultProcess(int INFO, String MESSAGE){
        this.INFO = INFO;
        this.MESSAGE = MESSAGE;
    }

    public boolean isERROR(){
        return getINFO() == 0;
    }
    

    /**
     * @return the INFO
     */
    public int getINFO() {
        return INFO;
    }

    /**
     * @param INFO the INFO to set
     */
    public void setINFO(int INFO) {
        this.INFO = INFO;
    }

    /**
     * @return the MESSAGE
     */
    public String getMESSAGE() {
        return MESSAGE;
    }

    /**
     * @param MESSAGE the MESSAGE to set
     */
    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

}
