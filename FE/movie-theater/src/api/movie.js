import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function getMovies(date, theaterId) {
  try {
    const response = await jsonApi.get("/movies", {
      params: {
        date: date,
        theaterId: theaterId,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
}

export { getMovies };
