package bigjava.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Local {

    private double latitude;
    private double longitude;

    

    public Local() {
    }

    public Local(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Local: latitude=" + latitude + ", longitude=" + longitude + ".";
    }
}
