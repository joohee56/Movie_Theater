import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function createTheater(theater) {
  try {
    const response = await jsonApi.post("/theaters/new", theater);
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getRegionsWithTheaterCount() {
  try {
    const response = await jsonApi.get("/theaters/regions");
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getTheatersByRegion(region) {
  try {
    const response = await jsonApi.get(`/theaters/regions/${region}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { createTheater, getRegionsWithTheaterCount, getTheatersByRegion };
