/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;


/**
 *
 * @author Ravjot
 */
public interface IScreenController {

    public void setScreenParent(SceneNavigator screenPage);

    public Object populateDTO();

    public boolean validateRequest() throws Exception;

    public void message(String message,String title);

}
