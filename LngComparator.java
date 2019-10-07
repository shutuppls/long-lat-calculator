
import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Colin Ruan
 * SBU ID # 111705515
 * Recitation 05
 */
public class LngComparator implements Comparator<City> {

    @Override
    public int compare(City o1, City o2) {
        if (o1.getLocation().getLng() < o2.getLocation().getLng()) {
            return -1;
        } else if (o1.getLocation().getLng() == o2.getLocation().getLng()) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
