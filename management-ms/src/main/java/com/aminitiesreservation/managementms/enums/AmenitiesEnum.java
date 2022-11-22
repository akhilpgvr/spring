package com.aminitiesreservation.managementms.enums;

public enum AmenitiesEnum {
    GYM("GYM"),
    SWIMMING_POOL("SWIMMING POOL"),
    STEAM_BATH("STEAM BATH"),
    YOGA("YOGA");
    private String userAmenity;
    AmenitiesEnum(final String amenity){
        userAmenity = amenity;
    }
    public String getAmenityType() {

        return userAmenity;
    }
}
