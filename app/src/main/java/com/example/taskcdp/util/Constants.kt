import com.example.taskcdp.BuildConfig

object Constants {
    const val WEATHER_URL = "forecast.json?key=${BuildConfig.API_KEY}"
    const val SEARCH_CITY_URL = "search.json?key=${BuildConfig.API_KEY}"


    const val SOMETHING_WRONG = "Something went wrong, Please check your network..."
}