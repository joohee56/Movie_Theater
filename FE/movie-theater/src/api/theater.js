import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getAllRegionsAndTheaterCount() {
  try {
    const response = await jsonApi.get("/theaters/count");
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getAllRegionsAndTheaterCount };
