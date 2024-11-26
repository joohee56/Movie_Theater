import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function holdBooking(bookingHoldRequest) {
  try {
    const response = await jsonApi.post(
      "/bookings/booking/hold",
      bookingHoldRequest
    );
    return response.data;
  } catch (error) {
    return error.response.data;
  }
}

async function confirmBooking(confirmBooking) {
  try {
    const response = await jsonApi.post(
      "/bookings/booking/confirm",
      confirmBooking
    );
    return response.data;
  } catch (error) {
    return error.response.data;
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

async function getBookingHistory() {
  try {
    const response = await jsonApi.get("/bookings/user");
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function cancelBookingAndGetBookingHistory(bookingId) {
  try {
    const response = await jsonApi.get(`/bookings/cancel/${bookingId}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export {
  holdBooking,
  confirmBooking,
  getBooking,
  getBookingHistory,
  cancelBookingAndGetBookingHistory,
};
