package net.dreameater.chatroomproject.classes;

import java.io.Serializable;

public class Room implements Serializable {

    private String roomName;
    private String imgName;
    private boolean favorite;
    public Chat chatTool;
    private double longitude;
    private double latitude;

    public Room(String name, double latitude, double longitude) {
        super();
        this.roomName = name;
        this.imgName = "red";
        this.favorite = false;
        this.chatTool = new Chat();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return this.roomName;
    }

    public String getImg() { return this.imgName; }

    public void setImg(String img) {
        imgName = img;
    }

    public boolean isWithinRange() {
        return imgName.equals("green");
    }

    public boolean isFavorited() { return favorite; }

    public void sendMessage(Message msg) {
        chatTool.addToHistory(msg);
    }

    public Chat currentChat() {
        return chatTool;
    }

    public boolean gpsCheck(double latitude, double longitude){
        return (distance(latitude, longitude,   this.latitude, this.longitude) < 0.1);
    }

    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometers

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadius * c;
    }
}
