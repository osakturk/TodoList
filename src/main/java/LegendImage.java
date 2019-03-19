/**
*
* Need to produce some chart in session prior to this action call in a Java bean
* Need a request attribute named "image" (Holds the name of session var of the chart);
*
*/


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LegendImage extends HttpServlet {

  /**
     *
     */
  private static final long serialVersionUID = -8823077159712737789L;

  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

   // get the chart from session
    int w = 37;
    int h = 9;
    BufferedImage image = new BufferedImage((int)w,(int)h,BufferedImage.TYPE_INT_RGB);

    Graphics2D g = (Graphics2D)image.getGraphics();

    try {
        String color = (String)request.getParameter("color");
        g.setColor(new Color(Integer.parseInt(color,16)));
        }
    catch(Exception e){}

    g.fillRect(0,0,37,9);
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
    doGet(request, response);
  }

  //Process the HTTP Put request
  public void doPut(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
  }

  //Clean up resources
  public void destroy() {
  }

}
