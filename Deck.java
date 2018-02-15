// Julie Xiao
// Deck   
// ICS 4U1
// December 12, 2016 

import java.awt.*;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener

class Deck
{
    private Card deck[];

    public Deck () // constructor for deck class 
    {
        deck = new Card [52];
        for (int x = 0 ; x < 52 ; x++)  // for each card in standard deck
        {
            deck [x] = new Card (x); // create card
        }
    }

    public int length () // method that finds the length of deck object 
    {
        return deck.length; 
    }

    public void show (Graphics g)  // draws card face up or face down
    {
        for (int c = 0 ; c < deck.length ; c++)
        {
            deck [c].show (g, c % 13 * 20 + 150, c / 13 * 50 + 20);
        }
    }

    public void shuffle () // method that randomly shuffles everything in deck[]
    {
        int num1, num2; 
        Card storage = new Card (1); 
        for (int x = 0; x < deck.length; x ++) { // for all values in array
            num1 = (int)(Math.random() * (deck.length -1)+ 0); // generate 2 random numbers
            num2 = (int)(Math.random() * (deck.length-1) + 0); 
            storage = deck [num1]; // temporarily store one value
            deck[num1]= deck [num2]; // replace value with another value
            deck [num2] = storage; // put stored value into the other value's spot
        }

    }

    public Card deal () // method that removes the first card in deck 
    {
        Card storage = deck[0]; 

        for (int x= 1; x<deck.length; x++) { // temp filled with list values with no extra spaces
            deck [x-1] = deck [x];
        }

        deck = newresize(deck, deck.length-1); 

        return storage;
    }

    public Card deal (int pos) // method that removes a card in the pos-1 spot in deck[]
    {
        try // try catch to prevent possible indexoutofboundsexception 
        {
            Card storage = deck[pos-1]; // take the card object in the position 
            Card temp [] = new Card [deck.length-1]; 
            if (deck.length > 0) // making sure deck is bigger than 0 
            {
                for (int i = 0; i < deck.length - 1; i++) // goes through entire array
                {
                    if (i < pos - 1) // for all values before pos-1. copy values from deck
                        temp[i] = deck[i]; 
                    else  // for all values after pos-1, copy values one spot over from deck[]
                        temp[i] = deck[i+1]; 
                }
            }

            deck = newresize(temp, deck.length-1); // update deck 
            return storage; 
        }
        catch (IndexOutOfBoundsException e) 
        {
            return null; //nothing happens 
        }

    }

    public void add (Card newCard) // method that adds a specific card to deck[]
    {
        Card [] newDeck = new Card [deck.length + 1]; // make new card[] array 
        for (int i =0; i < deck.length; i ++) // copy all values from deck[] to newDeck[]
            newDeck[i] = deck[i]; 
        newDeck [deck.length] = newCard; // add newCard to the end of the newDeck 

        deck = newresize(newDeck,deck.length + 1); // update deck 
    }

    public int[] search (Card want) // method that finds all of the positions of the card requested 
    {
        int position = 0,counter = 0;
        int temp [] = new int [100];

        for (int i = 0; i < deck.length; i ++){ // for all values in array 
            if (deck[i].equals(want) == 0){ // if match found
                temp [counter] = i + 1; 
                counter++; 
            }
        }

        temp = resize(temp, counter); 

        return temp;
    }

    public void flip (int pos) // method that flips cards 
    {
        if (deck[pos-1].getFaceup()) // if card is faceup, make it facedown 
            deck[pos-1].flip();
    }

    public void quickSort(int low, int high) // method that sorts cards based on rank from 2-A using quickSort algorithm
    {
        Card pivot = deck[low + (high-low)/2];  // assing random pivot value 
        int i = low, j = high; 

        while (i < j) // while l is less than h 
        {
            while (pivot.getRank()>deck[i].getRank()) // if pivot is greater than deck[l], add one to l
                i ++; 
                                                                                                                //move closer and closer to pivot value 
            while (deck[j].getRank()>pivot.getRank()) // if pivot is less than deck[l], subtract one from h
                j--; 

            if (i<=j) // if l is less than or equal to h 
            {
                Card temp = deck[i]; // swap deck[l] and deck[h] 
                deck[i] = deck[j];
                deck[j] = temp;
                i++; // add one to l
                j--; // subtract one from h 
            }

            // must get all of the values higher than pivot is above and everything less than pivot is below 
            if (low<j) // if low is less than h 
                quickSort(low,j); // apply quickSort recursively using low and new high value (h)  
                
            if (i<high) // if l is less than high 
                quickSort(i,high); // apply quickSort recursively using high and new low value (l) 
        }

    }

    public void selectionSort () // sort list using selectionSort algorithm 
    {  
        Card temp;
        for ( int  x = 0 ; x < deck.length - 1 ; x++) // sort first length-1 values
        {
            int  lowPos = x; // assume first value is lowest
            for  ( int  y = x + 1 ; y < deck.length ; y++) // check rest of list 
            {
                if  (deck [y].getRank() > deck[lowPos].getRank()) // if you find a lower value lowPos = y; // keep track of lowest’s position
                {
                    lowPos = y; 
                }
            }
            temp = deck [lowPos]; // swap low value with value in its proper position
            deck [lowPos] = deck [x];
            deck [x] = temp;
        }
    }

    public void selectionSort1 () // sort list by suit (S,H,C,D) 
    {
        Card temp;
        for ( int  x = 0 ; x < deck.length - 1 ; x++) // sort first length-1 values
        {
            int  lowPos = x; // assume first value is lowest
            for  ( int  y = x + 1 ; y < deck.length ; y++) // check rest of list 
            {
                if  (deck [y].getSuit() < deck [lowPos].getSuit()) // if you find a lower value lowPos = y; // keep track of lowest’s position
                {
                    lowPos = y; 
                }
            }

            temp = deck [x]; // swap low value with value in its proper position
            deck [x] = deck [lowPos];
            deck [lowPos] = temp;
        }

    }

    public Card[] newresize (Card[] array, int size) { // method that gets rid of extra spaces in Card [] array 
        Card temp [] = new Card [size];
        for (int x= 0; x<size; x++) { // temp filled with list values with no extra spaces
            temp [x] = array [x];
        }
        return temp;
    }

    public int[] resize( int list [], int size) { // method that gets rid of extra space in int [] array
        int temp [] = new int [size];
        for (int x= 0; x<size; x++) { // temp filled with list values with no extra spaces
            temp [x] = list [x];
        }
        return temp;
    }
}
