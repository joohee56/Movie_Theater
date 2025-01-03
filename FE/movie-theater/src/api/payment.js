import { jsonApiInstance } from "@/api/index";
const jsonApi = jsonApiInstance();

async function preparePayment(paymentId, amount) {
  try {
    const response = await jsonApi.post("/payments/prepare", {
      paymentId: paymentId,
      amount: amount,
    });
    return response.data;
  } catch (error) {
    console.log(error);
    return error.response.data;
  }
}

export { preparePayment };
