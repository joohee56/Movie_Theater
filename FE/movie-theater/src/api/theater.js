import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getTheatersByRegion(region) {
  try {
    const response = await jsonApi.get(`/theaters/${region}`);
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getTheatersByRegion };
