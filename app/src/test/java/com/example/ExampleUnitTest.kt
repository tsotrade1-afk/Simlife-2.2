package com.example

import com.example.data.generators.LifeEventGenerator
import com.example.data.model.Asset
import com.example.data.model.AssetType
import com.example.data.model.Gender
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testCharacterCreation_andAgeUpProgression() {
    val char = LifeEventGenerator.createRandomCharacter(
      customName = "Jordan Hayes",
      customGender = Gender.NON_BINARY,
      customCountry = "United States",
      customBirthYear = 2000
    )
    assertEquals(0, char.age)
    assertTrue(char.isAlive)

    // Simulate Age Up logic
    val agedYear1 = char.copy(age = char.age + 1)
    assertEquals(1, agedYear1.age)

    val agedYear2 = agedYear1.copy(age = agedYear1.age + 1)
    assertEquals(2, agedYear2.age)
  }

  @Test
  fun testTechStoreCatalog_andPurchases() {
    val techItems = LifeEventGenerator.TECH_FOR_SALE
    assertTrue(techItems.isNotEmpty())

    val simPhone = techItems.find { it.name == "SimPhone" }
    assertNotNull(simPhone)
    assertEquals(AssetType.PHONE, simPhone?.type)

    val char = LifeEventGenerator.createRandomCharacter().apply {
      bankBalance = 2000L
    }
    assertFalse(char.hasPhone)

    // Purchase SimPhone
    char.bankBalance -= simPhone!!.value
    char.assets.add(simPhone)

    assertTrue(char.hasPhone)
    assertEquals(1501L, char.bankBalance)
  }

  @Test
  fun testUpdateLogs_containsCurrentAndPreviousUpdates() {
    val logs = LifeEventGenerator.GAME_UPDATE_LOGS
    assertTrue(logs.size >= 2)

    val currentUpdate = logs.find { it.version.contains("v2.2") }
    assertNotNull("v2.2 update must be documented in update logs", currentUpdate)
    assertTrue(currentUpdate!!.highlight.contains("Age 0"))

    val prevUpdate = logs.find { it.version.contains("v2.0") }
    assertNotNull("v2.0 update must be documented in update logs", prevUpdate)
  }
}
