import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function createHallWithSeats(hallSeatsRequest) {
  try {
    const response = await jsonApi.post("/halls/new/seats", hallSeatsRequest);
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

async function getHalls(theaterId) {
  try {
    const response = await jsonApi.get(`/halls/theater/${theaterId}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

export { createHallWithSeats, getHalls };
