import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getRegionsAndTheaterCount() {
  try {
    const response = await jsonApi.get("/theaters/count");
    return response;
  } catch (error) {
    console.log(error);
  }
}

async function getTheatersByRegion(region) {
  try {
    const response = await jsonApi.get(`/theaters/${region}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getRegionsAndTheaterCount, getTheatersByRegion };
