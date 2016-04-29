package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.vaadin.hezamu.canvas.Canvas;


@Theme("mytheme")
@Widgetset("org.test.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        final Canvas canvas = new Canvas();

        canvas.setWidth(600,Unit.PIXELS);
        canvas.setHeight(400,Unit.PIXELS);
        final BarChar bars = new BarChar(canvas,300,260);


        HorizontalLayout buttons = new HorizontalLayout();
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.addClickListener( click -> bars.voteYes() );
        no.addClickListener( click -> bars.voteNo() );


        buttons.setSpacing(true);
        buttons.setMargin(true);
        buttons.addComponents(yes,no);

        layout.addComponents(canvas,buttons);
        layout.setComponentAlignment(canvas, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
        bars.init();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


   public static class BarChar{
       private final Canvas canvas;
       private int x,y;
       private int yes = 5, no = 1;

       public void init(){
           draw();
       }

       public BarChar(Canvas canvas, int x, int y){
           this.canvas = canvas;
           this.x = x;
           this.y = y;
       }

       private void draw(){
           clear();
           canvas.setFillStyle("#FFFFFF");
           canvas.setFont("50px Arial");
           canvas.fillText("¿Cree usted que Ruben es pato?",x-200,y-100,400);

           canvas.fillText("Yes",x-200,y-40,50);
           canvas.fillText("No",x+200,y-40,50);

           canvas.setFillStyle("#F47CBF");
           canvas.fillRect(x-200,y,widthYes(),40);
           canvas.setFillStyle("#6CDCDF");
           canvas.fillRect((x-200)+widthYes(),y,widthNo(),40);

           circle(yes,x-200,y);
           circle(no,x+200,y);
       }
       private double widthYes(){
           if(yes+no == 0) return 0;
           double percentYes = (yes *100)/(yes + no);
           return percentYes * 400 /100;
       }

       private double widthNo(){
           if(yes+no == 0) return 0;
           double percentNo = (no *100)/(yes + no);
           return percentNo * 400 /100;
       }

       public void voteYes(){
            yes++;
           draw();
       }
       public void voteNo(){
            no++;
           draw();
       }

       private void clear(){
           canvas.clear();
           canvas.setFillStyle("#F4DF62");
           canvas.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
       }

       private void circle(int cant,double x ,double y){
           canvas.setFillStyle("#F76379");
           canvas.beginPath();
           canvas.arc(x,y,40,0,2*Math.PI,false);
           canvas.fill();
           canvas.setFillStyle("#FFFFFF");
           canvas.setFont("35px Arial");
           canvas.fillText(cant+"",x-10,10+y,40);
       }
   }


}
