from fastapi import FastAPI
from datetime import date
from pydantic import BaseModel
from typing import List
from application.get_driver_elo import GetDriverElo
from application.get_driver_elo import GetDriverEloResponse
from application.get_driver_elo import GetDriverEloRecord
from application.get_driver_elo import GetDriverEloRequest
from dataclasses import dataclass

app = FastAPI(
    title="Formula 1 ELO System API",
    summary="This API will be responsible of calculating the ELO for the Formula 1. This is a non official API",
    root_path = "/api/elo/v1"
)

get_driver_elo_use_case = GetDriverElo()

@dataclass
class HttpGetDriverEloRecord(BaseModel):
    elo: int
    date: date   

@dataclass
class HttpGetDriverEloResponse(BaseModel):
    id: str
    name: str
    current_elo: int
    highest_elo: int
    record: List[HttpGetDriverEloRecord]

@app.get("/drivers/{driver_id}", response_model=HttpGetDriverEloResponse, tags=["Drivers"])
def get_driver_elo(driver_id: str) -> HttpGetDriverEloResponse:
    request = GetDriverEloRequest(driver_id=driver_id)
    response = get_driver_elo_use_case.execute(request)
    return HttpGetDriverEloResponse(
        id=response.id,
        name=response.name,
        current_elo=response.current_elo,
        highest_elo=response.highest_elo,
        record=[HttpGetDriverEloRecord(elo=record.elo, date=record.date) for record in response.record]
    )
    
if __name__ == "__main__":
    import uvicorn
    
    uvicorn.run(app, host="0.0.0.0", port=8000)