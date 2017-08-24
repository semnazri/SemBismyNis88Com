package POJO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 8/21/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public interface VersionControl {

    @GET("BVersion")
    Call<model.VersionControl> getVersionControl();
}
