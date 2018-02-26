package com.packt.publishing.caseclass

import org.scalatest.{WordSpec, Matchers}

class WeatherForecastSpec extends WordSpec with Matchers {

  private def createWeatherForecast(city: String = "London",
                                    date: String = "01/01/2018",
                                    hour: String = "10 AM",
                                    temperature: String = "6") =
    WeatherForecast(city, date, hour, temperature)

  "WeatherForecast" should {

    "default values" in {
      createWeatherForecast().city shouldBe "London"
      createWeatherForecast().date shouldBe "01/01/2018"
      createWeatherForecast().hour shouldBe "10 AM"
      createWeatherForecast().temperature shouldBe "6"
    }

    "check city value" in {
      createWeatherForecast(city = "Hyderabad" ).city should not be "London"
      createWeatherForecast(city = "Hyderabad" ).city shouldBe "Hyderabad"
    }

    "check date value" in {
      createWeatherForecast(date = "06/04/2018" ).date shouldBe "06/04/2018"
    }

    "check hour value" in {
      createWeatherForecast(hour = "14" ).hour shouldBe "14"
    }

    "check city and temperature values" in {
      val wf = createWeatherForecast(city = "Hyderabad", temperature = "30" )
      wf.city shouldBe "Hyderabad"
      wf.temperature shouldBe "30"
    }

  }

}
