import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function createBooking(bookingRequest) {
  try {
    const response = await jsonApi.post("/bookings/new", bookingRequest);
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getBooking(bookingId) {
  try {
    const response = await jsonApi.get(`/bookings/booking/${bookingId}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getBookingList(userId) {
  try {
    const response = await jsonApi.get(`/bookings/user/${userId}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { createBooking, getBooking, getBookingList };
