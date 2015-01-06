/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_parser;

/**
 *
 * @author Lbd
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Category {
    public String           name;
    public int              count;
    public static int       total = 0;
    
    Category(String n)
    {
        this.name = n;
        this.count = 1;
        total++;
    }
    
    public String getName() { return this.name; }
    public int getCount() { return this.count; }
    public void addCount() { this.count++; }
}

class SortByCount implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Category c1 = (Category) o1;
        Category c2 = (Category) o2;
        if (c1.getCount() > c2.getCount()) {
            return 1;
        }
        if (c1.getCount() < c2.getCount()) {
            return -1;
        }
        return 0;
    }
}