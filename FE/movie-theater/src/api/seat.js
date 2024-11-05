import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getSeats(hallId) {
  try {
    const response = await jsonApi.get("/seats", {
      params: {
        hallId: hallId,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getSeats };
