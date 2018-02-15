// Julie Xiao
// Deck   
// ICS 4U1
// December 12, 2016 

import java.awt.*;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener

class DisplayDeck extends JFrame
{
    static Deck deck = new Deck(); 

    private JComboBox suit; 
    private JComboBox rank; 
    private JComboBox card; 
    //======================================================== constructor
    public DisplayDeck ()
    {
        // 1... Create/initialize components
        String [] suits = new String[] {"S","H","C","D"}; // Suit JComboBox 
        suit = new JComboBox (suits); 
        Integer[] numbers1 = new Integer [52]; // position JComboBox 
        for (Integer i = 1; i < 53; i ++)
            numbers1 [i-1] = i; 
        card = new JComboBox (numbers1); 
        Integer [] numbers = new Integer [] {2,3,4,5,6,7,8,9,10,11,12,13,14}; //rank JComboBox
        rank = new JComboBox(numbers); 

        BtnListener btnListener = new BtnListener (); // listener for all buttons

        JButton shuffleBtn = new JButton ("Shuffle");
        shuffleBtn.addActionListener (btnListener);
        JButton qsortBtn = new JButton ("Sort (2-A)");
        qsortBtn.addActionListener (btnListener);
        JButton ssortBtn = new JButton ("Sort (A-2)"); 
        ssortBtn.addActionListener (btnListener); 
        JButton ssortBtn1 = new JButton ("Sort suit"); 
        ssortBtn1.addActionListener (btnListener); 
        JButton dealBtn = new JButton ("Deal"); 
        dealBtn.addActionListener (btnListener); 
        JButton removeBtn = new JButton ("Remove"); 
        removeBtn.addActionListener (btnListener); 
        JButton addBtn = new JButton ("Add"); 
        addBtn.addActionListener(btnListener); 
        JButton searchBtn = new JButton ("Search"); 
        searchBtn.addActionListener(btnListener); 

        // 2... Create content pane, set layout
        JPanel content = new JPanel ();        // Create a content pane
        content.setLayout (new BorderLayout ()); // Use BorderLayout for panel
        JPanel north = new JPanel (); // where the buttons, etc. will be
        north.setLayout (new FlowLayout ()); // Use FlowLayout for input area

        DrawArea board = new DrawArea (600, 400); // Area for cards to be displayed

        // 3... Add the components to the input area.
        north.add (shuffleBtn);
        north.add (qsortBtn);
        north.add (ssortBtn);
        north.add (ssortBtn1); 
        north.add (dealBtn);
        north.add (removeBtn); 
        north.add (card); 
        north.add (addBtn); 
        north.add (searchBtn); 
        north.add (rank); 
        north.add (suit); 

        content.add (north, "North"); // Input area
        content.add (board, "South"); // Deck display area

        // 4... Set this window's attributes.
        setContentPane (content);
        pack ();
        setTitle ("Deck Array");
        setSize (1000, 500);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window.
    } 

    public Card get() // method that creates card objects using input from combo boxes
    {
        int s; 
        int r = (int)rank.getSelectedItem(); // retrieving input from combo boxes 
        String suits = (String)suit.getSelectedItem(); 

        if (suits.compareTo("S") == 0) // if the suit is S (spades) 
            s = 0; 
        else if (suits.compareTo("H") == 0) // if the suit is H (hearts) 
            s = 1; 
        else if (suits.compareTo("C") == 0 )// if the suit is C (clubs)  
            s = 2; 
        else // else the suit is D (diamonds) 
            s = 3; 

        return new Card ((s*13) + r - 2); // multiply s by 3 and add rank-2 
    }

    // put ActionListener class for your buttons here
    class BtnListener implements ActionListener // Button menu
    {
        public void actionPerformed (ActionEvent e)
        {
            int [] temp; 
            if (e.getActionCommand ().equals ("Shuffle"))
                deck.shuffle (); // shuffle deck
            else if (e.getActionCommand ().equals ("Sort (2-A)"))
                deck.quickSort (0,deck.length()-1); // quickSort deck 
            else if (e.getActionCommand ().equals ("Sort (A-2)"))
                deck.selectionSort(); // selectionSort deck by rank 
            else if (e.getActionCommand ().equals ("Sort suit"))
                deck.selectionSort1(); //selectionSort deck by suit 
            else if (e.getActionCommand ().equals("Add")) //add card to deck 
            {
                deck.add (get()); 
                card.addItem(card.getItemCount()+1); // add a number to position JComboBox 
            }
            else if (e.getActionCommand ().equals("Search")) 
            {
                temp = deck.search(get()); // int array with all positions of card object from get()
                for (int i = 0; i < temp.length; i ++) // flip the cards in positions from temp[]
                    deck.flip(temp[i]); 
            }
            else if (e.getActionCommand ().equals("Deal"))
            {
                deck.deal(); // remove first card from deck
                card.removeItemAt(card.getItemCount()-1); // remove last number in position JComboBox
            }
            else if (e.getActionCommand().equals("Remove")) 
            {
                deck.deal((int)card.getSelectedItem()); // remove card from specified position
                card.removeItemAt(card.getItemCount()-1); // remove last number in position JComboBox
            }

            repaint (); // do after each action taken to update deck
        }
    }

    class DrawArea extends JPanel // class responsible for drawing card images 
    {
        public DrawArea (int width, int height)
        {
            this.setPreferredSize (new Dimension (width, height)); // size
        }

        public void paintComponent (Graphics g)
        {
            deck.show (g);
        }
    }

    //======================================================== method main
    public static void main (String[] args)
    {
        DisplayDeck window = new DisplayDeck ();
        window.setVisible (true);
    }
}