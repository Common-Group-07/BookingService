package com.hotelbooking.service;

public interface IBookingService {
	void cancelBooking(Long bookingId);
	
	String saveBooking(Long roomId,BookedRoom bookingRequest);
	
	BookedRoom findByBookConfirmationCode(String confirmationCode);
	
	List<BookedRoom> getAllBookings();

}
