// Julie Xiao
// Deck   
// ICS 4U1
// December 12, 2016 

import java.awt.*;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener

class Card
{
    private int rank, suit;
    private Image image;
    private boolean faceup;
    private static Image cardback; // shared image for back of card

    public Card (int cardNum)  // Creates card from 0-51
    {
        rank = cardNum % 13;
        suit = cardNum / 13;
        faceup = true;

        image = null;
        try
        {
            image = ImageIO.read (new File ("cards/" + (cardNum + 1) + ".gif")); // load file into Image object
            cardback = ImageIO.read (new File ("cards/b.gif")); // load file into Image object
        }
        catch (IOException e)
        {
            System.out.println ("File not found");
        }
    }

    public void show (Graphics g, int x, int y)  // draws card face up or face down
    {
        if (faceup)
            g.drawImage (image, x, y, null);
        else
            g.drawImage (cardback, x, y, null);

    }
    
    public int getRank () // method that returns the rank of the card 
    {
        return rank; 
    }
    
    public int getSuit() // method that returns the suit of the card 
    {
        return suit; 
    }
    
    public boolean getFaceup () // method that returns whether the card is faceup (true) or facedown (false) 
    {
        return faceup; 
    }
    
    public void flip () // method that flips the card 
    {
        if (faceup) // if faceup, make facedown 
            faceup = false; 
        else // if facedown, make faceup 
            faceup = true;  
    }
    
    public int equals (Card two) // method that compares invoking object with an accepted card object 
    {
        if ((two.getSuit()*13 + two.getRank() )< (suit*13 + rank)) // if invoking object is greater, return 1
            return 1; 
        else if ((two.getSuit()*13 + two.getRank()) >(suit*13 + rank))  // if invoking object is less, return -1
            return -1; 
        else // if equal, return 0 
            return 0; 
          
    }
}