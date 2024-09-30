from datetime import date
from typing import List
from dataclasses import dataclass

@dataclass
class GetDriverEloRecord():
    elo: int
    date: date   

@dataclass
class GetDriverEloResponse():
    id: str
    name: str
    current_elo: int
    highest_elo: int
    record: List[GetDriverEloRecord]

@dataclass
class GetDriverEloRequest:
    driver_id: str
    

class GetDriverElo:
    def execute(self, request: GetDriverEloRequest) -> GetDriverEloResponse:
        raise {
        "id": request.driver_id,
        "name": "Fernando Alonso",
        "elo": 2571,
        "record": [
            {"elo": 2301, "date": "2023-01-21"},
            {"elo": 2571, "date": "2024-09-25"}
        ]
    }