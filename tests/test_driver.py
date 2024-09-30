import unittest
from datetime import date
from src.domain.driver import Elo, DriverId, DriverName, Driver

class TestDriver(unittest.TestCase):
    def should_create_a_driver_id(self):
        valid_id = DriverId("max_verstappen")
        self.assertEqual(valid_id.value, "max_verstappen")
        
        with self.assertRaises(ValueError):
            DriverId("")

    def should_driver_name_creation(self):
        valid_name = DriverName("Michael Schumacher")
        self.assertEqual(valid_name.value, "Michael Schumacher")
        
        with self.assertRaises(ValueError):
            DriverName("")

    def should_driver_creation(self):
        valid_id = DriverId("fernando_alonso")
        valid_name = DriverName("Fernando Alonso")
        valid_elo_record = [Elo(1500, date(2023, 9, 28))]
        
        driver = Driver(id=valid_id, name=valid_name, elo_record=valid_elo_record)
        self.assertEqual(driver.id.value, "fernando_alonso")
        self.assertEqual(driver.name.value, "Fernando Alonso")
        self.assertEqual(len(driver.elo_record), 1)

        with self.assertRaises(ValueError):
            Driver(id=valid_id, name=valid_name, elo_record=[])
    
    def test_highest_elo(self):
        valid_id = DriverId("lewis_hamilton")
        valid_name = DriverName("Lewis Hamilton")
        elo_record = [
            Elo(1400, date(2022, 6, 20)),
            Elo(1500, date(2023, 4, 18)),
            Elo(1350, date(2021, 10, 15))
        ]
        
        driver = Driver(id=valid_id, name=valid_name, elo_record=elo_record)
        highest_elo = driver.highest_elo()

        self.assertEqual(highest_elo.elo, 1500)

    def test_lowest_elo(self):
        valid_id = DriverId("sebastian_vettel")
        valid_name = DriverName("Sebastian Vettel")
        elo_record = [
            Elo(1300, date(2021, 9, 22)),
            Elo(1200, date(2023, 5, 10)),
            Elo(1250, date(2022, 11, 1))
        ]
        
        driver = Driver(id=valid_id, name=valid_name, elo_record=elo_record)
        lowest_elo = driver.lowest_elo()

        self.assertEqual(lowest_elo.elo, 1200)

    def test_current_elo(self):
        valid_id = DriverId("charles_leclerc")
        valid_name = DriverName("Charles Leclerc")
        elo_record = [
            Elo(1300, date(2022, 7, 10)),
            Elo(1350, date(2023, 9, 30)),
            Elo(1400, date(2023, 1, 10))
        ]
        
        driver = Driver(id=valid_id, name=valid_name, elo_record=elo_record)
        current_elo = driver.current_elo()

        self.assertEqual(current_elo.elo, 1350)

if __name__ == "__main__":
    unittest.main()