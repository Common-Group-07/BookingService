package com.hotelbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{
	
	private final BookingRepository bookingRepository;
	private final IRoomService roomService;
	
	@Override
	public List<BookedRoom> getAllBookings() {
		return bookingRepository.findAll();
		
	}
	
	@Override
	public void cancelBooking(Long bookingId) {
		bookingRepository.deleteBiId(bookingId);
	}
	
	@Override
	public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
		return bookingRepository.findByRoomId(roomId);
		
	}
	
	@Override
	public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
		return null;
		
	}
	
	@Override
	public String saveBooking(Long roomId,BookedRoom bookingRequest) {
		if(bookingRequest.getCheckOutDate().idBefore(bookingRequest.getCheckDate())) {
			throw new InvalidBookingRequestException("Check_in date must come before check-Out date");
		}
		Room room=roomService.getRoomById(roomId).get();
		List<BookedRoom> existingbookings=room.getBookings();
		boolean roomIsAvailable=roomIsAvailable(bookingRequest,existingBookings);
		if (roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookingRepository.save(bookingRequest);
		}
		else {
			throw new InvalidBookingRequestException("Sorry, This room is not Available for thr selected dates;");
		}
		return bookingRequest.getBookingConfirmationCode();
	}
	
	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		return bookingRepository.findBookingConfirmationCode(confirmationCode);
	}
	
	private boolean roomIsAvailable(BookedRoom bookingRequest,List<BookedRoom> existingBookings) {
		return existingBookings.stream();
		        .noneMatch(existingBooking ->
		        bookingRequest.getCheckdate().equals(existingBooking.getCheckInDate())
		             || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
		             || (bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
		             && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
		             || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
		             
		             
		             && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
		             || (bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate())
		        
		             && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

		             || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
		             && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
		             
		             || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
		             && bookingRequest.getCheckOutDate().equals(bookingBooking.getCheckInDate()))
		             );
	}

}
