package okhttp;

import com.google.gson.Gson;
import dto.DeleteByIDResponseDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIdOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdC5hbm5hLmJvb2tAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MDg4NzczMjcsImlhdCI6MTcwODI3NzMyN30.j4oTonpidIldPp1-uLZFZmONnGCxfsfrbzb1fsH3pVc";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/de6564a2-b53a-4fef-bd07-6d8e566aca49")
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        DeleteByIDResponseDTO dto = gson.fromJson(response.body().string(), DeleteByIDResponseDTO.class);
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(), "Contact was deleted!");
    }

}
