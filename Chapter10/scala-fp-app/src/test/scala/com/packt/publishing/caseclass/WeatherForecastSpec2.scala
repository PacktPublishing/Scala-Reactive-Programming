package com.packt.publishing.caseclass

import org.scalatest.{FreeSpec, MustMatchers}

class WeatherForecastSpec2 extends FreeSpec with MustMatchers {

  private def createWeatherForecast(city: String = "London",
                                    date: String = "01/01/2018",
                                    hour: String = "10 AM",
                                    temperature: String = "6") =
    WeatherForecast(city, date, hour, temperature)

    "default values" in {
      val wf = createWeatherForecast()
      wf.city mustBe ("London")
      wf.date mustBe "01/01/2018"
      wf.hour mustBe "10 AM"
      wf.temperature mustBe "6"
    }

    "check city value" in {
      val wf = createWeatherForecast(city = "Hyderabad")
      wf.city must not be "London"
      wf.city mustBe "Hyderabad"
    }

    "check date value" in {
      val wf = createWeatherForecast(date = "06/04/2018")
      wf.date mustBe "06/04/2018"
    }

    "check hour value" in {
      val wf = createWeatherForecast(hour = "14")
      wf.hour mustBe "14"
    }

    "check city and temperature values" in {
      val wf = createWeatherForecast(city = "Hyderabad", temperature = "30")
      wf.city mustBe "Hyderabad"
      wf.temperature mustBe "30"
    }

}
