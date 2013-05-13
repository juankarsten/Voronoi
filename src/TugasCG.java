/*
 * Tugas 1 Geometri Komputasional
 * 
 * Implementasikan algoritma untuk mencari Convex Hull 2D:
 * a) algoritma incremental [ tanpa bonus ]
 *          atau
 * b) algoritma divide-and-conquer [ bonus 20% ]
 *
 * Pergunakan kerangka program ini sebagai dasar.
 * Program ini sudah bisa menerima input point dari user secara 
 * interaktif.
 *
 * Tambahkan button/menu-item untuk convex-hull dan 
 * button/menu-item untuk hitung luas convex-hull. 
 *
 * Silakan lakukan modifikasi terhadap program yang diberikan.
 * 
 * Due date: Senin, March 4, 2013 (11:55 PM) waktu SCeLE
 * Kerjakan per grup. Satu grup terdiri dari 2 orang.
 */

import java.awt.*;          
import java.awt.event.*;    
import javax.swing.*;       
import javax.swing.border.*;

public class TugasCG extends JFrame {
   // This application relies on the PointsPanel component
   PointsPanel pane;

   public static void main(String[] args) {
	
      TugasCG app = new TugasCG();

      app.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) { 
            System.exit(0); 
         }
      });

      app.pack();
      app.setVisible(true);
   }   

   /*
    * This constructor creates the GUI for this application.
    */
   public TugasCG() {
      super("Tugas Geometri Komputasional");  
  
      // All content of a JFrame (except for the menubar) 
      // goes in the Frame's internal "content pane", 
      // not in the frame itself.

      Container contentPane = this.getContentPane();

      // Specify a layout manager for the content pane
      contentPane.setLayout(new BorderLayout());

      // Create the main component, give it a border, and
      // a background color, and add it to the content pane
      pane = new PointsPanel();
      pane.setBorder(new BevelBorder(BevelBorder.LOWERED));
      contentPane.add(pane, BorderLayout.CENTER);

      // Create a menubar and add it to this window.  
      JMenuBar menubar = new JMenuBar();  // Create a menubar
      this.setJMenuBar(menubar);  // Display it in the JFrame
 
      // Create menus and add to the menubar
      JMenu filemenu = new JMenu("File");
      menubar.add(filemenu);
		
      /* Create some Action objects for use in the menus 
         and toolbars.
         An Action combines a menu title and/or icon with 
         an ActionListener.
         These Action classes are defined as inner 
         classes below.
      */

      Action clear = new ClearAction();
      Action quit = new QuitAction();
      Action ch = new CHAction();
      Action chi= new IncrementAction();
      Action cp= new ClosestpairAction();
      Action fortune = new FortuneAction();
      
      // Populate the menus using Action objects
      filemenu.add(clear);
      filemenu.add(quit);
      filemenu.add(ch);
      filemenu.add(chi);
      filemenu.add(cp);
      filemenu.add(fortune);
	
      // Now create a toolbar, add actions to it, and add 
      // it to the top of the frame (where it appears 
      // underneath the menubar)
      JToolBar toolbar = new JToolBar();
      toolbar.add(clear);
      toolbar.add(quit);
      toolbar.add(ch);
      toolbar.add(chi);
      toolbar.add(cp);
      toolbar.add(fortune);
      contentPane.add(toolbar, BorderLayout.NORTH);
   }

   /* This inner class defines the "clear" action */
   class ClearAction extends AbstractAction {
    
      public ClearAction() {
         super("Clear");  // Specify the name of the action
      }

      public void actionPerformed(ActionEvent e) { 
         pane.clear();
          /* Lengkapi */  

      }
   }

   class QuitAction extends AbstractAction {
      public QuitAction() { super("Quit"); }
      public void actionPerformed(ActionEvent e) { 
      // Use JOptionPane to confirm that the user 
      // really wants to quit
      int response =
	 JOptionPane.showConfirmDialog(TugasCG.this,   
            "Benar mau quit?");
      if (response == JOptionPane.YES_OPTION) 
         System.exit(0);
      }
   }

   class CHAction extends AbstractAction {
      public CHAction() { super("DC Convex Hull"); }
      public void actionPerformed(ActionEvent e) { 
         pane.calculateConvexHullDC();          
      }
   }

   class IncrementAction extends AbstractAction {
      public IncrementAction() { super("Incremental Convex Hull"); }
      public void actionPerformed(ActionEvent e) { 
         pane.createIncrement();          
      }
   }
   
   class ClosestpairAction extends AbstractAction {
      public ClosestpairAction() { super("Closest Pair"); }
      public void actionPerformed(ActionEvent e) { 
         pane.findClosestPair();
                 
      }
   }
   
   class FortuneAction extends AbstractAction {
      public FortuneAction() { super("Fortune Algorithm"); }
      public void actionPerformed(ActionEvent e) { 
         pane.fortune();
                 
      }
   }
}