package org.example.apis;

import pojo.BookingPojo;

public class Booking {

    private static final String endPointBooking = "/booking";

    private static String endPointBookingID;

    private static int validBookingID;

    private int BookingIDCreated;

    public static String getEndpointBooking()
    {
        return endPointBooking;
    }

    public static String getEndpointBookingID()
    {
        endPointBookingID = endPointBooking + "/" + validBookingID;
        return endPointBookingID;
    }

    public static void setValidBookingID(int bookingID)
    {
        validBookingID = bookingID;
    }

    public static int getValidBookingID()
    {
        return validBookingID;
    }


    public BookingPojo getBookingBody(
            String firstname,
            String lastname,
            int totalprice,
            boolean depositpaid,
            String checkin,
            String checkout,
            String additionalneeds) {

        // Create booking dates object
        BookingPojo.BookingDates bookingDates = new BookingPojo.BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);

        // Create main booking object
        BookingPojo booking = new BookingPojo();
        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(totalprice);
        booking.setDepositpaid(depositpaid);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds(additionalneeds);

        return booking;
    }

    public void setBookingIDCreated(int bookingIDCreated)
    {
        this.BookingIDCreated = bookingIDCreated;
    }

    public int getBookingIDCreated()
    {
        return this.BookingIDCreated;
    }

    public String getBookingIDCreatedEndpoint()
    {
        return (endPointBooking + "/" + validBookingID);
    }

}
