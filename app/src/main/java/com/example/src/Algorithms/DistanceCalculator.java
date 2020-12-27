package com.example.src.Algorithms;
import java.lang.*;

public class DistanceCalculator
{
    /**
     * This routine calculates the distance between two points (given the
     * latitude/longitude of those points). It is being used to calculate
     * the distance between two locations using GeoDataSource (TM) products
     *
     * Definitions:
     *      Southern latitudes are negative, eastern longitudes are positive
     * Function parameters:
     *      lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)
     *      lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)
     *      unit = the unit you desire for results
     *           where: 'M' is statute miles (default)
     *                  'K' is kilometers
     *                  'N' is nautical miles
     * @return the distance between the two given coordinators
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}