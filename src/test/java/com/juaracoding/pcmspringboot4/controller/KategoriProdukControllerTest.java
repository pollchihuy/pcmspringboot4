package com.juaracoding.pcmspringboot4.controller;

import com.juaracoding.pcmspringboot4.config.OtherConfig;
import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.repo.KategoriProdukRepo;
import com.juaracoding.pcmspringboot4.utils.DataGenerator;
import com.juaracoding.pcmspringboot4.utils.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class KategoriProdukControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private KategoriProdukRepo kategoriProdukRepo;

    private JSONObject req;
    private KategoriProduk kategoriProduk;
//    private Random rand ;
    private String token;
    private DataGenerator dataGenerator;

    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
//        rand  = new Random();
        req = new JSONObject();
        kategoriProduk = new KategoriProduk();
        dataGenerator = new DataGenerator();
        Optional<KategoriProduk> op = kategoriProdukRepo.findTop1ByOrderByIdDesc();
        kategoriProduk = op.get();
    }

    @BeforeTest
    private void setup(){
        /** sifatnya optional */
    }

    @Test(priority = 0)
    void save(){
        Response response ;
        String strNama =  dataGenerator.genDataAlfabet(15,50);
        String strDeskripsi =  dataGenerator.genDataAlfabet(25,255);
        String strNotes =  dataGenerator.genDataAlfabet(30,255);
        try{

            req.put("nama",strNama);
            req.put("deskripsi", strDeskripsi);
            req.put("notes", strNotes);

            System.out.println("Nama : "+strNama);
            System.out.println("Deskripsi : "+strDeskripsi);
            System.out.println("Notes : "+strNotes);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.POST,"kategoriproduk");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DISIMPAN");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 10)
    void update(){
        Response response ;
        req.clear();
        try{
            String reqNama = dataGenerator.genDataAlfabet(15,50);
            String reqDeskripsi = dataGenerator.genDataAlfabet(20,255);
            String reqNotes = dataGenerator.genDataAlfabet(25,255);

            System.out.println("ReqNama : "+reqNama);
            System.out.println("ReqDeskripsi : "+reqDeskripsi);
            System.out.println("ReqNotes : "+reqNotes);

            kategoriProduk.setNama(reqNama);
            kategoriProduk.setDeskripsi(reqDeskripsi);
            kategoriProduk.setNotes(reqNotes);

            req.put("nama", reqNama);
            req.put("deskripsi",reqDeskripsi);
            req.put("notes", reqNotes);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.PUT,"/kategoriproduk/"+ kategoriProduk.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DIUBAH");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 20)
    void findById(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/kategoriproduk/"+ kategoriProduk.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DITEMUKAN");
            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), kategoriProduk.getId());
            Assert.assertEquals(jsonPath.getString("data.nama"), kategoriProduk.getNama());
//            Assert.assertEquals(jsonPath.getString("data.deskripsi"), kategoriProduk.getDeskripsi());
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 30)
    void findAll(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/kategoriproduk");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DITEMUKAN");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort_by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.currentpage")),0);
            Assert.assertEquals(jsonPath.getString("data.column_name"),"id");
            Assert.assertNotNull(jsonPath.getString("data.total_pages"));
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size_per_page")), OtherConfig.getDefaultPaginationSize());
            Assert.assertEquals(jsonPath.getString("data.value"),"");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total_data")),intData);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 40)
    void findByParam(){
        Response response ;
        String pathVariable = "/kategoriproduk/asc/id/0";
        String strValue = kategoriProduk.getNama();

        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
                    params("value",strValue).
                    params("size",10).
                    request(Method.GET,pathVariable);

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List<Map<String,Object>> ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
            Map<String,Object> map = ltData.get(0);

            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DITEMUKAN");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort_by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current_page")),0);
            Assert.assertEquals(jsonPath.getString("data.column_name"),"nama");
            Assert.assertNotNull(jsonPath.getString("data.total_pages"));
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total_data")),intData);
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size_per_page")), 10);
            Assert.assertEquals(jsonPath.getString("data.value"),strValue);
// ======================================================================================================================================================

            Assert.assertEquals(map.get("nama"),strValue);
//            Assert.assertEquals(map.get("deskripsi"), kategoriProduk.getDeskripsi());
            Assert.assertEquals(Long.parseLong(map.get("id").toString()), kategoriProduk.getId());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 50)
    void uploadExcel(){
        Response response ;
        int intResponse = 0;
        try{
            response = given().
                    header("Content-Type","multipart/form-data").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    multiPart("file",new File(System.getProperty("user.dir")+"/src/test/resources/data-test/kategori_produk.xlsx"),
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    request(Method.POST,"kategoriproduk/upload-excel");

            intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DISIMPAN");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertEquals(intResponse,201);
        }
    }
    @Test(priority = 50)
    void downloadExcel(){
        Response response ;
        int intResponse =0;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
                    params("value", kategoriProduk.getNama()).
                    request(Method.GET,"kategoriproduk/download-excel");

            intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".xlsx"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertEquals(intResponse,200);
        }
    }

    @Test(priority = 60)
    void downloadPdf(){
        Response response ;
        int intResponse =0;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/pdf").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
                    params("value", kategoriProduk.getNama()).
                    request(Method.GET,"kategoriproduk/download-pdf");

            intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".pdf"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/pdf");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertEquals(intResponse,200);
        }
    }

    @Test(priority = 999)
    void delete(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.DELETE,"/kategoriproduk/"+ kategoriProduk.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA BERHASIL DIHAPUS");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}