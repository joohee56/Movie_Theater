import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getSeats(screeningId, hallId) {
  try {
    const response = await jsonApi.get("/seats", {
      params: {
        screeningId: screeningId,
        hallId: hallId,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getSeats };
