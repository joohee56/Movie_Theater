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

export { createBooking };
