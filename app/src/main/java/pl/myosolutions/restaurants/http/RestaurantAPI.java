package pl.myosolutions.restaurants.http;

import java.util.List;

import io.reactivex.Observable;
import pl.myosolutions.restaurants.entities.Customer;
import retrofit2.http.GET;

public class RestaurantAPI {


    public static final String API_BASE_URL = "https://s3-eu-west-1.amazonaws.com/";
    private static final String PATH = "/quandoo-assessment/";
    private static final String CUSTOMER_FILE = "customer-list.json";
    private static final String TABLE_FILE = "table-map.json";

    public interface RestaurantService {

        @GET(PATH + CUSTOMER_FILE)
        Observable<List<Customer>> getCustomers();


        @GET(PATH + TABLE_FILE)
        Observable<List<Boolean>> getTables();

    }


}
