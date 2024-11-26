import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function createTheater(theater) {
  try {
    const response = await jsonApi.post("/theaters/new", theater);
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

async function getRegionsWithTheaterCount() {
  try {
    const response = await jsonApi.get("/theaters/regions");
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

async function getTheatersByRegion(region) {
  try {
    const response = await jsonApi.get(`/theaters/regions/${region}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

export { createTheater, getRegionsWithTheaterCount, getTheatersByRegion };
