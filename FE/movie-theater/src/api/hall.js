import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function createHallWithSeats(hallSeatsRequest) {
  try {
    const response = await jsonApi.post("/halls/new/seats", hallSeatsRequest);
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getHalls(theaterId) {
  try {
    const response = await jsonApi.get(`/halls/theater/${theaterId}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { createHallWithSeats, getHalls };
