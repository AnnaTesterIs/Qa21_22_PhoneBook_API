package okhttp;

import com.google.gson.Gson;
import dto.DeleteByIDResponseDTO;
import dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIdOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdC5hbm5hLmJvb2tAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MDg4NzczMjcsImlhdCI6MTcwODI3NzMyN30.j4oTonpidIldPp1-uLZFZmONnGCxfsfrbzb1fsH3pVc";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    @BeforeMethod
    public void preCondition(){
        //create contact
        //get id from "message" : "Contact was added!"
        //id
    }

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
    @Test
    public void deleteContactByIdWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/de6564a2-b53a")
                .delete()
                .addHeader("Authorization", "de6564a2")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");
    }

    @Test
    public void deleteContactByIdNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+123)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Bad Request");
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage(), "Contact with id: 123 not found in your contacts!");
    }
}
